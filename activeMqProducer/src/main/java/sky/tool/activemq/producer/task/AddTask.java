package sky.tool.activemq.producer.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Component;

import sky.tool.activemq.producer.entity.ActiveEntity;

@Component
public class AddTask implements ApplicationRunner
{
	private Logger logger = Logger.getLogger(AddTask.class);
	
	@Value("${send.conut}")
	private Integer count;
	
	@Autowired
	private JmsMessagingTemplate jt;
	
	@Resource(name="q1")
	private Queue q1;
	@Resource(name="q2")
	private Queue q2;
	
	@Resource(name="t1")
	private Topic t1;
	@Resource(name="t2")
	private Topic t2;
	@Resource(name="t3")
	private Topic t3;
	
	private List<Destination> DestinationList = new ArrayList<Destination>(4);
	
	private Random random = new Random();
	
	@Override
	public void run(ApplicationArguments args) throws Exception
	{
		DestinationList.add(q1);
		DestinationList.add(q2);
		DestinationList.add(t1);
		DestinationList.add(t2);
		DestinationList.add(t3);
		Random r = new Random();
		for(int i = 0 ; i < count ; i ++)
		{
			ActiveEntity ae = new ActiveEntity(r.nextLong(), getDescription(r));
			jt.convertAndSend(DestinationList.get(random.nextInt(DestinationList.size())),ae);
			logger.info("seq:" + i + " send succees id:" + ae.getId());
			//Thread.sleep(r.nextInt(1500) + 300);
		}
	}
	
	private String getDescription(Random r)
	{
		byte[] bArray = new byte[64];
		r.nextBytes(bArray);
		return new String(bArray);
	}

}
