package sky.tool.activemq.calculation.service;

import com.alibaba.fastjson.JSONObject;

import sky.tool.activemq.calculation.entity.HtywRedisEntity;

public interface AddMqService
{
	void accept(HtywRedisEntity entity , JSONObject jo);
}
