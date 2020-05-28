
package sky.tool.activemq.calculation.util;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

public class HttpUtil
{
	private HttpUtil()
	{
		
	}
	
	/**
	 * get请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String get(String url, Map<String, Object> params)
	{
		// 创建请求
		HttpRequest request = HttpRequest.get(url).queryEncoding("utf-8");// 请求地址,编码
		// 设参,表单形式提交
		if (params != null && params.size() > 0)
			request.form(params).formEncoding("utf-8");
		// 发送请求
		HttpResponse response = request.send();
		// 成功返回
		if (response.statusCode() == 200)
			return response.bodyText();
		// 失败返回
		return null;
	}

	public static String postWithHeader(String url, Map<String, Object> params, Map<String, String> headers)
	{
		// 创建请求
		HttpRequest request = HttpRequest.post(url).queryEncoding("utf-8");// 请求地址,编码
		// 设参,表单形式提交
		if (params != null && params.size() > 0)
			request.form(params).formEncoding("utf-8");
		// 设置header
		for (String headerKey : headers.keySet())
		{
			request = request.header(headerKey, headers.get(headerKey));
		}
		// 发送请求
		HttpResponse response = request.send();
		response.charset("utf-8");
		// 成功返回
		if (response.statusCode() == 200)
			return response.bodyText();
		// 失败返回
		return null;
	}

	public static String get(String url)
	{
		return get(url, null);
	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, Map<String, Object> params)
	{
		// 创建请求
		HttpRequest request = HttpRequest.post(url).queryEncoding("utf-8");// 请求地址,编码
		// 设参,表单形式提交
		if (params != null && params.size() > 0)
			request.form(params).formEncoding("utf-8");
		// 发送请求
		HttpResponse response = request.send();
		// 成功返回
		if (response.statusCode() == 200)
			return response.bodyText();
		// 失败返回
		return null;
	}

	public static String post(String url)
	{
		return post(url, null);
	}

	/**
	 * post请求（JSON格式）
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String postJSON(String url, Map<String, Object> params)
	{
		// 创建请求
		HttpRequest request = HttpRequest.post(url).contentType("application/json").queryEncoding("utf-8");// 请求地址,编码
		// 设参
		if (params != null && params.size() > 0)
			request.body(JSONObject.toJSONString(params));
		// 发送请求
		HttpResponse response = request.send();
		// 成功返回
		if (response.statusCode() == 200)
			return response.bodyText();
		// 失败返回
		return null;
	}
}
