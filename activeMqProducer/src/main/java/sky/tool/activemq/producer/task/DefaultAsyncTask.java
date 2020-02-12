package sky.tool.activemq.producer.task;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class DefaultAsyncTask
{
	private Logger logger = Logger.getLogger(getClass());
	
	@Async
	public void defaultTask(String caller)
	{
		try
		{
			Thread.sleep(6000l);
			logger.debug(caller);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
