package sky.tool.activemq.producer.intercepter;

import javax.validation.ValidationException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import sky.tool.activemq.producer.common.JsonResult;
import sky.tool.activemq.producer.constant.HttpResultConstant;

/**
 * 全局错误抓取
 */
@RestControllerAdvice
public class ErrorHandler
{
	//private Logger logger = Logger.getLogger(getClass());

	@ExceptionHandler
	public JsonResult defaultErrorHandler(Exception e)
	{
	//	logger.error("未知异常拦截", e);
		return JsonResult.build(HttpResultConstant.SYSTEM_ERROR.getCode(), HttpResultConstant.SYSTEM_ERROR.getMessage(),
				e.getMessage());
	}

	@ExceptionHandler
	public JsonResult wrongParamHandler(ValidationException e)
	{
		return JsonResult.build(HttpResultConstant.INVALID_PARAM.getCode(),HttpResultConstant.INVALID_PARAM.getMessage(), e.getMessage());
	}
	
	@ExceptionHandler
	public JsonResult wrongParamHandler(NumberFormatException e)
	{
		return JsonResult.build(HttpResultConstant.INVALID_PARAM.getCode(), HttpResultConstant.INVALID_PARAM.getMessage() , e.getMessage());
	}
	

}
