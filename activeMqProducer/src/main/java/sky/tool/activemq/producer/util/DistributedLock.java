
package sky.tool.activemq.producer.util;

import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.exceptions.JedisException;

/**
 * redis分布式锁
 */
public class DistributedLock
{
	private Logger logger = Logger.getLogger(DistributedLock.class);
	
	private RedisTemplate<String, Object> redisTemplate;

	public DistributedLock(RedisTemplate<String, Object> redisTemplate)
	{
		this.redisTemplate = redisTemplate;
	}

	/**
	 * 加锁
	 * 
	 * @param lockName
	 *            锁的key
	 * @param acquireTimeout
	 *            获取超时时间 单位:毫秒
	 * @param timeout
	 *            锁的超时时间 单位:毫秒
	 * @return 锁标识--若请求锁失败,则返回null
	 */
	public String lockWithTimeout(String lockName, long acquireTimeout, long timeout)
	{
		Jedis conn = null;
		String lockId = null;
		try
		{
			// 获取连接
			conn = ((JedisConnection) redisTemplate.getConnectionFactory().getConnection()).getJedis();

			// 随机生成一个value
			String identifier = UUID.randomUUID().toString();
			// 锁名，即key值
			String lockKey = "lock:" + lockName;
			// 超时时间，上锁后超过此时间则自动释放锁
			int lockExpire = (int) (timeout / 1000);

			// 获取锁的超时时间，超过这个时间则放弃获取锁
			long end = System.currentTimeMillis() + acquireTimeout;
			while (System.currentTimeMillis() < end)
			{
				if (conn.setnx(lockKey, identifier) == 1)
				{
					conn.expire(lockKey, lockExpire);
					// 返回value值，用于释放锁时间确认
					lockId = identifier;
					return lockId;
				}
				// 返回-1代表key没有设置超时时间，为key设置一个超时时间
				if (conn.ttl(lockKey) == -1)
				{
					conn.expire(lockKey, lockExpire);
				}

				try
				{
					// 停止10毫秒，等待下轮请求锁
					Thread.sleep(10);
				}
				catch (InterruptedException e)
				{
					Thread.currentThread().interrupt();
				}
			}
		}
		catch (JedisException e)
		{
			logger.error("获取分布式锁失败", e);
		}
		finally
		{
			if (conn != null)
			{
				conn.close();
			}
		}
		return lockId;
	}

	/**
	 * 释放锁
	 * 
	 * @param lockName
	 *            锁的key
	 * @param lockId
	 *            释放锁的标识
	 * @return
	 */
	public boolean releaseLock(String lockName, String lockId)
	{
		Jedis conn = null;
		String lockKey = "lock:" + lockName;
		boolean success = false;
		try
		{
			conn = ((JedisConnection) redisTemplate.getConnectionFactory().getConnection()).getJedis();
			while (true)
			{
				// 监视lock，准备开始事务
				conn.watch(lockKey);
				// 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
				if (lockId.equals(conn.get(lockKey)))
				{
					Transaction transaction = conn.multi();
					transaction.del(lockKey);
					List<Object> results = transaction.exec();
					if (results == null)
					{
						continue;
					}
					success = true;
				}
				conn.unwatch();
				break;
			}
		}
		catch (JedisException e)
		{
			logger.error("获取分布式锁失败", e);
		}
		finally
		{
			if (conn != null)
			{
				conn.close();
			}
		}
		return success;
	}
}
