package sky.tool.activemq.producer.intercepter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 拦截器的配置层
 * @author sky
 */
@Configuration
public class IntercepterConfigration extends WebMvcConfigurationSupport 
{
	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		registry.addInterceptor(new LogIntercepter()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
}
