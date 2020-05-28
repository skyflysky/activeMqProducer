package sky.tool.activemq.calculation.task;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import sky.tool.activemq.calculation.service.HtywRedisEntityService;
import sky.tool.activemq.calculation.util.RedisUtils;

@Component
public class InitTask
{
	private Logger logger = Logger.getLogger(InitTask.class);
	
	@Value("${default.redis.scan.pattern}")
	private String pattern;
	
	@Value("${default.redis.scan.limit}")
	private Long limit;
	
	@Autowired
	RedisUtils redisUtil;
	
	@Autowired
	HtywRedisEntityService service;
	
	@PostConstruct
	public void init()
	{
		logger.info("init start");
		List<String> keys = redisUtil.assembleScanKeys(pattern, limit);
		logger.info("redis size :" + keys.size());
		service.setInitData(keys);
		logger.info("init end");
	}
}
