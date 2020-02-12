package sky.tool.activemq.producer.task;

import org.apache.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import sky.tool.activemq.producer.entity.ActiveEntity;

@Component
public class ActivemqConsumer
{
	private Logger logger = Logger.getLogger(ActivemqConsumer.class);
	
	private void goEntity(ActiveEntity entity)
	{
		logger.info("id:" + entity.getId() + "\thash:" + entity.getHashcode());
	}
	
	@JmsListener(destination="QueueA")
	public void qaListnenr(ActiveEntity entity)
	{
		try
		{
			Thread.sleep(5000l);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		logger.info("队列A处理一条信息");
		goEntity(entity);
	}
	
	@JmsListener(destination="QueueB")
	public void qbListnenr(ActiveEntity entity)
	{
		try
		{
			Thread.sleep(5000l);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		logger.info("队列B处理一条信息");
		goEntity(entity);
	}
	
	@JmsListener(destination="TopicA", containerFactory = "jmsTopicListenerContainerFactory")
	public void taListnenr(ActiveEntity entity)
	{
		try
		{
			Thread.sleep(5000l);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		logger.info("主题A处理一条信息");
		goEntity(entity);
	}
	
	@JmsListener(destination="TopicB", containerFactory = "jmsTopicListenerContainerFactory")
	public void tbListnenr(ActiveEntity entity)
	{
		try
		{
			Thread.sleep(5000l);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		logger.info("主题B处理一条信息");
		goEntity(entity);
	}
	
	@JmsListener(destination="TopicC", containerFactory = "jmsTopicListenerContainerFactory")
	public void tcListnenr(ActiveEntity entity)
	{
		try
		{
			Thread.sleep(5000l);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		logger.info("主题C处理一条信息");
		goEntity(entity);
	}
}
