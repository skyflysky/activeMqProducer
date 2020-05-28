package sky.tool.activemq.calculation.mq;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import sky.tool.activemq.calculation.service.CalculationService;

@Component
public class ActivemqConsumer
{
	private Logger logger = Logger.getLogger(ActivemqConsumer.class);
	
	@Resource(name = "cepstrum")
	CalculationService cepstrumCalService;
	
	@Resource(name = "frequency")
	CalculationService frequencyCalService;
	
	@JmsListener(destination="calculationQuque")
	public void qaListnenr(ActiveEntity entity)
	{
		cepstrumCalService.calculation(entity.getEntity(), entity.getJo());
		frequencyCalService.calculation(entity.getEntity(), entity.getJo());
	}
	
}
