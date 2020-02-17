
package sky.tool.activemq.producer.config;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

import sky.tool.activemq.producer.task.Excutor;

@Configuration
@EnableJms
public class ActiveMQConfig
{
	private Logger logger = Logger.getLogger(ActiveMQConfig.class);
	
	@Bean(name="q1")
	public Queue queue1()
	{
		return new ActiveMQQueue("Queue1");
	}
	
	@Bean(name="q2")
	public Queue queue2()
	{
		return new ActiveMQQueue("Queue2");
	}

	@SuppressWarnings("rawtypes")
	@Bean
	public JmsListenerContainerFactory jmsTopicListenerContainerFactory(ConnectionFactory connectionFactory , ExampleErrorHandler errorHandler)
	{
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		// 这里必须设置为true，false则表示是queue类型
		factory.setPubSubDomain(true);
		factory.setErrorHandler(errorHandler);
		return factory;
	}

	@Service
	public class ExampleErrorHandler implements ErrorHandler
	{
		@Override
		public void handleError(Throwable t)
		{
			if(t instanceof java.util.concurrent.RejectedExecutionException)
			{
				//executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
				logger.warn("");
			}
		}
	}
	
	@Bean(name="t1")
	Topic topic1()
	{
		return new ActiveMQTopic("Topic1");
	}

	@Bean(name="t2")
	Topic topic2()
	{
		return new ActiveMQTopic("Topic2");
	}
	
	@Bean(name="t3")
	Topic topic3()
	{
		return new ActiveMQTopic("Topic3");
	}
	
	@Bean(name="acen")
	Excutor excutor()
	{
		return new Excutor(10, 500l, 200);
	}
	
}
