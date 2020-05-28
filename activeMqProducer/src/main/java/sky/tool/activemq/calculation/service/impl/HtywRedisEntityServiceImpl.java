package sky.tool.activemq.calculation.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import sky.tool.activemq.calculation.constant.HtywType;
import sky.tool.activemq.calculation.dao.HtywRedisEntityDao;
import sky.tool.activemq.calculation.entity.HtywRedisEntity;
import sky.tool.activemq.calculation.service.HtywRedisEntityService;

@Service
public class HtywRedisEntityServiceImpl implements HtywRedisEntityService
{
	private Logger logger = Logger.getLogger(HtywRedisEntityServiceImpl.class);
	
	@Autowired
	HtywRedisEntityDao dao;
	
	@Value("${default.gap}")
	Integer gap;
	
	@Transactional
	@Override
	public void setInitData(List<String> keys)
	{
		dao.deactive();
		Calendar currTime = Calendar.getInstance();
		int insert = 0 , update = 0;
		for(String key : keys)
		{
			String[] keyArray = key.split("-");
			HtywRedisEntity entity = dao.findByCollId(Long.valueOf(keyArray[1]));
			if(entity != null)
			{
				entity.setLastModify(currTime);
				entity.setActive(Boolean.TRUE);
				dao.save(entity);
				update++;
			}
			else
			{
				HtywRedisEntity hre = new HtywRedisEntity
						(
								Long.valueOf(keyArray[0]), 
								Long.valueOf(keyArray[1]), 
								HtywType.toHtywType(keyArray[2]), 
								null, 
								currTime, 
								gap, 
								Boolean.TRUE
						); 
				dao.save(hre);
				insert++;
			}
		}
		logger.info("update size:" + update + " insert size:" + insert);
	}

	@Override
	public List<HtywRedisEntity> findPullEntity()
	{
		Calendar currTime = Calendar.getInstance();
		List<HtywRedisEntity> queryResult = dao.findByTypeAndActive(HtywType.ORIGINAL, Boolean.TRUE);
		List<HtywRedisEntity> result = new ArrayList<>();
		for(HtywRedisEntity e : queryResult)
		{
			Calendar lastModify = e.getLastModify();
			lastModify.add(Calendar.SECOND, e.getUpdateGap());
			if(lastModify.compareTo(currTime) >= 0)
			{
				result.add(e);
			}
		}
		return result;
	}

	@Override
	public HtywRedisEntity save(HtywRedisEntity htywRedisEntity)
	{
		return dao.save(htywRedisEntity);
	}
	
}
