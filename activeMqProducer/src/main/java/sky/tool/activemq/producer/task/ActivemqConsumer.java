package sky.tool.activemq.producer.task;

import org.apache.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import sky.tool.activemq.producer.entity.ActiveEntity;

@Component
public class ActivemqConsumer
{
	private Logger logger = Logger.getLogger(ActivemqConsumer.class);
	
	private void goEntity(ActiveEntity entity)
	{
		//logger.info("id:" + entity.getId() + "\thash:" + entity.getHashcode());
	}
	
	@Async
	@JmsListener(destination="QueueA")
	public void qaListnenr(ActiveEntity entity) throws InterruptedException
	{
		logger.info("队列A处理一条信息\tid:" + entity.getId() + "\thash:" + entity.getHashcode());
		Thread.sleep(5000l);
		goEntity(entity);
	}
	
	@Async
	@JmsListener(destination="QueueB")
	public void qbListnenr(ActiveEntity entity) throws InterruptedException
	{
		logger.info("队列B处理一条信息\tid:" + entity.getId() + "\thash:" + entity.getHashcode());
		Thread.sleep(5000l);
		goEntity(entity);
	}
	
	@Async
	@JmsListener(destination="TopicA", containerFactory = "jmsTopicListenerContainerFactory")
	public void taListnenr(ActiveEntity entity) throws InterruptedException
	{
		logger.info("主题A处理一条信息\tid:" + entity.getId() + "\thash:" + entity.getHashcode());
		Thread.sleep(5000l);
		goEntity(entity);
	}
	
	@Async
	@JmsListener(destination="TopicB", containerFactory = "jmsTopicListenerContainerFactory")
	public void tbListnenr(ActiveEntity entity) throws InterruptedException
	{
		logger.info("主题B处理一条信息\tid:" + entity.getId() + "\thash:" + entity.getHashcode());
		Thread.sleep(5000l);
		goEntity(entity);
	}
	
	@Async
	@JmsListener(destination="TopicC", containerFactory = "jmsTopicListenerContainerFactory")
	public void tcListnenr(ActiveEntity entity) throws InterruptedException
	{
		logger.info("主题C处理一条信息\tid:" + entity.getId() + "\thash:" + entity.getHashcode());
		Thread.sleep(5000l);
		goEntity(entity);
	}
}
