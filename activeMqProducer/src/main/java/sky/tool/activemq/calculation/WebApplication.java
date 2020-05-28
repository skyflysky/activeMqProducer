
package sky.tool.activemq.calculation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableAsync
@EnableJms
@SpringBootApplication
public class WebApplication
{
	public static void main(String[] args)
	{
		SpringApplication.run(WebApplication.class, args);
	}
}
