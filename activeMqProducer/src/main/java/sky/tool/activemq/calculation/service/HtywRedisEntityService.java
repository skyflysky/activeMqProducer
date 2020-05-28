package sky.tool.activemq.calculation.service;

import java.util.List;

import sky.tool.activemq.calculation.entity.HtywRedisEntity;

public interface HtywRedisEntityService
{
	/**
	 * 根据航天云网redis数据设置本地mysql
	 * @param keys
	 */
	void setInitData(List<String> keys);
	
	/**
	 * 查出应该去redis取数据的实体类
	 * @return
	 */
	List<HtywRedisEntity> findPullEntity();
	
	HtywRedisEntity save(HtywRedisEntity htywRedisEntity);
}
