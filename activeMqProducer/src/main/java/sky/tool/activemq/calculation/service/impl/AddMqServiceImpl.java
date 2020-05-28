package sky.tool.activemq.calculation.service.impl;

import javax.annotation.Resource;
import javax.jms.Queue;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import sky.tool.activemq.calculation.entity.HtywRedisEntity;
import sky.tool.activemq.calculation.mq.ActiveEntity;
import sky.tool.activemq.calculation.service.AbstractAddMqService;

@Service
public class AddMqServiceImpl extends AbstractAddMqService
{
	private Logger logger = Logger.getLogger(AddMqServiceImpl.class);
	
	@Resource(name="calculation")
	Queue calculationQueue;
	
	@Autowired
	private JmsMessagingTemplate jt;
	
	@Override
	public void afterAccept(HtywRedisEntity entity, JSONObject jo)
	{
		
	}

	@Override
	public void accepting(HtywRedisEntity entity, JSONObject jo)
	{
		jt.convertAndSend(calculationQueue, new ActiveEntity(jo , entity));
	}

	@Override
	public void beforeAccept(HtywRedisEntity entity, JSONObject jo)
	{
		
	}
}
