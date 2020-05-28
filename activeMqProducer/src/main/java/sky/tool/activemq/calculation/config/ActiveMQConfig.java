
package sky.tool.activemq.calculation.config;

import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import javax.jms.Topic;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

@Configuration
@EnableJms
public class ActiveMQConfig
{
	@Bean(name="calculation")
	public Queue calculationQueue()
	{
		return new ActiveMQQueue("calculationQuque");
	}

	@SuppressWarnings("rawtypes")
	@Bean
	public JmsListenerContainerFactory jmsTopicListenerContainerFactory(ConnectionFactory connectionFactory)
	{
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		// 这里必须设置为true，false则表示是queue类型
		factory.setPubSubDomain(true);
		return factory;
	}
}
