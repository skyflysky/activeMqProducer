package sky.tool.activemq.calculation.service;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;

import sky.tool.activemq.calculation.entity.HtywRedisEntity;

public abstract class AbstractAddMqService implements AddMqService
{
	@Autowired
	private HtywRedisEntityService htywService;
	
	@Override
	public void accept(HtywRedisEntity entity, JSONObject jo)
	{
		entity.setLastModify(Calendar.getInstance());
		htywService.save(entity);
		beforeAccept(entity , jo);
		accepting(entity,jo);
		afterAccept(entity , jo);
	}

	public abstract void afterAccept(HtywRedisEntity entity, JSONObject jo);

	public abstract void accepting(HtywRedisEntity entity, JSONObject jo);

	public abstract void beforeAccept(HtywRedisEntity entity, JSONObject jo);
	
	
}
