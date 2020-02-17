package sky.tool.activemq.producer.task;

import java.util.Optional;

import org.springframework.stereotype.Service;

import sky.tool.activemq.producer.entity.ActiveEntity;

@Service
public class Excutor extends QueueExecutor<ActiveEntity>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4648435419856651146L;
	
	public Excutor()
	{
		super();
	}

	public Excutor(int thredCount , long timeOut , int queueSizeTimes)
	{
		super(thredCount, timeOut, queueSizeTimes);
	}

	@Override
	public void runMission(Optional<ActiveEntity> opt) throws Exception
	{
		Thread.sleep(500l);
		logger.info("多线程处理");
	}
}
