package sky.tool.activemq.calculation.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import sky.tool.activemq.calculation.constant.HtywType;
import sky.tool.activemq.calculation.entity.HtywRedisEntity;
import sky.tool.activemq.calculation.service.AddMqService;
import sky.tool.activemq.calculation.service.HtywRedisEntityService;
import sky.tool.activemq.calculation.util.RedisUtils;

//@Component
public class LoopTask
{
	@Autowired
	RedisUtils redisUtil;
	
	@Autowired
	HtywRedisEntityService htywService;
	
	@Autowired
	AddMqService addService;
	
	private Logger logger = Logger.getLogger(LoopTask.class);
	
	@Scheduled(initialDelay=120000l , fixedRate=60000l)
	public void loopTask()
	{
		logger.info("pull begin");
		List<HtywRedisEntity> result = htywService.findPullEntity();
		for(HtywRedisEntity hre : result)
		{
			addService.accept(hre,JSONObject.parseObject(redisUtil.get(toKey(hre)).toString()));
		}
		logger.info("pull end");
	}

	private String toKey(HtywRedisEntity hre)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(hre.getDevId());
		sb.append("-");
		sb.append(hre.getCollId());
		sb.append("-");
		sb.append(HtywType.ORIGINAL.getEnglishName());
		return sb.toString();
	}
}
