package sky.tool.activemq.producer.intercepter;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

import sky.tool.activemq.producer.util.IpUtil;

/**
 * 所有请求的log 记录层
 * @author sky
 * @email  zhangyuntian@ztrx.com.cn
 * @date 2019年4月30日 下午6:46:38
 * @company 广东智通睿新智能科技有限公司
 */
public class LogIntercepter	implements HandlerInterceptor
{
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception
	{
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception
	{
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2) throws Exception
	{
		logRequestLog(request);
		return true;
	}
	
	private void logRequestLog(HttpServletRequest request)
	{
		Map<String, String[]> origeinalMap = request.getParameterMap();
		Map<String, Object> targetMap = new LinkedHashMap<>();
		for (String key : origeinalMap.keySet())
		{
			String[] array = origeinalMap.get(key);
			if(array.length > 1)
			{
				targetMap.put(key, origeinalMap.get(key));
			}
			else
			{
				targetMap.put(key, array[0]);
			}
		}
		JSONObject ob = new JSONObject(targetMap);
		ob.put("QueryString", request.getQueryString());
		ob.put("RemoteHost", IpUtil.getIpAddr(request));
		logger.info("控制层请求日志 请求接口:'" + request.getServletPath() + "' 内容:'" + ob.toJSONString() +"'");
	}
}
