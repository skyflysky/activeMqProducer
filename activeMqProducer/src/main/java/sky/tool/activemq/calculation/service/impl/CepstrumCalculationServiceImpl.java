package sky.tool.activemq.calculation.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import sky.tool.activemq.calculation.constant.HtywType;
import sky.tool.activemq.calculation.entity.HtywRedisEntity;
import sky.tool.activemq.calculation.service.AbstractCalculationService;
import sky.tool.activemq.calculation.util.RedisUtils;

@Service(value="cepstrum")
public class CepstrumCalculationServiceImpl extends AbstractCalculationService
{
	@Autowired
	RedisUtils redisUtil;

	@Override
	public void afterculation(HtywRedisEntity hre, JSONObject jo)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void calculating(HtywRedisEntity hre, JSONObject jo)
	{
		
	}

	@Override
	public void beforeCalculation(HtywRedisEntity hre, JSONObject jo)
	{
		redisUtil.set(getKeyPreFix(hre, jo) + HtywType.CEPSTRUM.getEnglishName(), jo);
		logger.info(HtywType.CEPSTRUM.getEnglishName() + " send");
	}
	
}
