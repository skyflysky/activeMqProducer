package sky.tool.activemq.calculation.service;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

import sky.tool.activemq.calculation.constant.HtywType;
import sky.tool.activemq.calculation.entity.HtywRedisEntity;

public abstract class AbstractCalculationService implements CalculationService
{
	protected Logger logger = Logger.getLogger(getClass());

	@Override
	public void calculation(HtywRedisEntity hre, JSONObject jo)
	{
		beforeCalculation(hre , jo);
		calculating(hre , jo);
		afterculation(hre , jo);
	}

	public abstract void afterculation(HtywRedisEntity hre, JSONObject jo);

	public abstract void calculating(HtywRedisEntity hre, JSONObject jo);

	public abstract void beforeCalculation(HtywRedisEntity hre, JSONObject jo);
	
	public String getKeyPreFix(HtywRedisEntity hre, JSONObject jo)
	{
		StringBuilder sb = new StringBuilder(hre.getDevId().toString());
		sb.append("-");
		sb.append(hre.getCollId());
		sb.append("-");
		return sb.toString();
	}
	
}
