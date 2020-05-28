package sky.tool.activemq.calculation.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import sky.tool.activemq.calculation.constant.HtywType;
import sky.tool.activemq.calculation.util.DateUtil;
import sky.tool.activemq.calculation.util.RedisUtils;

//@Component
public class AddTestTask implements ApplicationRunner
{
	@Autowired
	RedisUtils redisUtil;
	
	private Logger logger = Logger.getLogger(AddTestTask.class);

	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		logger.info("filling start");
		JSONArray ja = new JSONArray();
		ja.add(5);
		ja.add(6);
		ja.add(7);
		ja.add(8);
		JSONObject jo = new JSONObject();
		jo.put("x", ja);
		jo.put("y", ja);
		jo.put("startTime", DateUtil.dateTimeStr());
		jo.put("samplerate", 26000);
		for(int i = 100001 ; i < 101001 ;  i++)
		{
			StringBuilder sb = new StringBuilder("11554466");
			sb.append("-");
			sb.append(i);
			sb.append("-");
			sb.append(HtywType.ORIGINAL.getEnglishName());
			redisUtil.set(sb.toString(), jo.toJSONString());
		}
		logger.info("filling end");
	}
}
