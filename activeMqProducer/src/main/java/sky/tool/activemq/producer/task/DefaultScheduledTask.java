package sky.tool.activemq.producer.task;

import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DefaultScheduledTask
{
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private DefaultAsyncTask asyncTask;
	
	@Scheduled(fixedRate=8000)
	public void defaultTask()
	{
		logger.debug("default");
	}
	
	@Scheduled(cron = "0/5 * * * * ?")
	public void defaultAsyncTask()
	{
		Integer k = new Random().nextInt(Integer.MAX_VALUE);
		logger.debug(k);
		asyncTask.defaultTask(k.toString());
	}
}
