package sky.tool.activemq.producer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.jms.Queue;
import javax.jms.Topic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sky.tool.activemq.producer.common.JsonResult;

@RestController
public class MqController
{
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
	
	private List<Queue> queueList = new ArrayList<Queue>(2);
	
	private List<Topic> topicList = new ArrayList<Topic>(3);
	
	private Random random = new Random();
	
	@PostConstruct
	public void add()
	{
		queueList.add(q1);
		queueList.add(q2);
		topicList.add(t1);
		topicList.add(t2);
		topicList.add(t3);
	}
	
	@RequestMapping("/queue")
	public JsonResult sendQueueMsg(String msg)
	{
		jt.convertAndSend(queueList.get(random.nextInt(queueList.size())),msg);
		return new JsonResult(0, msg, "queue");
	}
	
	@RequestMapping("/topic")
	public JsonResult sendTopicMsg(String msg)
	{
		jt.convertAndSend(topicList.get(random.nextInt(topicList.size())) , msg);
		return new JsonResult(0, msg, "topic");
	}
	
}
