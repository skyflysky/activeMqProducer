
package sky.tool.activemq.producer.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * 微信接口使用~工具类 
 */
@SuppressWarnings({ "rawtypes", "deprecation" })
public class WeChatUtil
{
	
	private WeChatUtil()
	{
		
	}

	// 设置参数时需要用到的字符处理函数
	public static String trimString(String value)
	{
		String ret = null;
		if (value != null)
		{
			ret = value.trim();
		}
		return ret;
	}

	// 产生随机字符串，不长于32位
	public static String createNoncestr()
	{
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 32; i++)
		{
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	// 产生随机字符串，不长于6位
	public static String createCode()
	{
		String chars = "0123456789";
		String res = "";
		for (int i = 0; i < 6; i++)
		{
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	// 格式化参数，签名过程需要用到
	public static String formatBizQueryParaMap(SortedMap<Object, Object> parameters)
	{
		StringBuilder sb = new StringBuilder();
		Set<Map.Entry<Object, Object>> es = parameters.entrySet();
		for (Object e : es)
		{
			Map.Entry entry = (Map.Entry) e;
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k))
			{
				sb.append(k).append("=").append(v).append("&");
			}
		}
		return sb.toString();
	}

	/**
	 * 获取签名
	 * 
	 * @param characterEncoding
	 *            编码
	 * @param parameters
	 *            需要加密的对象集合
	 * @param key
	 *            商户支付密钥Key
	 * @return 签名字符串
	 */
	public static String getSign(String characterEncoding, SortedMap<Object, Object> parameters, String key)
	{
		return MD5Util.MD5Encode(formatBizQueryParaMap(parameters) + "key=" + key, characterEncoding).toUpperCase();
	}

	// array转xml
	public static String arrayToXml(SortedMap<Object, Object> parameters)
	{

		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		Set<Map.Entry<Object, Object>> es = parameters.entrySet();
		for (Object e : es)
		{
			Map.Entry entry = (Map.Entry) e;
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k))
			{
				sb.append("<").append(k).append(">").append("<![CDATA[").append(v).append("]]></").append(k)
						.append(">");
			}
			else
			{
				sb.append("<").append(k).append(">").append(v).append("</").append(k).append(">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	// xml转 array
	public static Map<String, String> xmlToArray(String strxml) throws JDOMException, IOException
	{
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if (null == strxml || "".equals(strxml))
		{
			return new HashMap<String, String>();
		}

		Map<String, String> m = new HashMap<String, String>();

		InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		for (Object aList : list)
		{
			Element e = (Element) aList;
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty())
			{
				v = e.getTextNormalize();
			}
			else
			{
				v = getChildrenText(children);
			}

			m.put(k, v);
		}

		// 关闭流
		in.close();

		return m;
	}

	/**
	 * 获取子结点的xml
	 * 
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children)
	{
		StringBuilder sb = new StringBuilder();
		if (!children.isEmpty())
		{
			for (Object aChildren : children)
			{
				Element e = (Element) aChildren;
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<").append(name).append(">");
				if (!list.isEmpty())
				{
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</").append(name).append(">");
			}
		}

		return sb.toString();
	}

	/**
	 * 将元为单位的转换为分 替换小数点，支持以逗号区分的金额
	 *
	 * @param amount
	 * @return
	 */
	public static String changeY2F(String amount)
	{
		String currency = amount.replaceAll("\\$|\\￥|\\,", ""); // 处理包含, ￥ 或者$的金额
		int index = currency.indexOf(".");
		int length = currency.length();
		Long amLong = 0L;
		if (index == -1)
		{
			amLong = Long.valueOf(currency + "00");
		}
		else
			if (length - index >= 3)
			{
				amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
			}
			else
				if (length - index == 2)
				{
					amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
				}
				else
				{
					amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
				}

		return amLong.toString();
	}

	/**
	 * @author 李欣桦
	 * @date 2014-12-5下午2:32:05
	 * @Description：将请求参数转换为xml格式的string
	 * @param parameters
	 *            请求参数
	 * @return
	 */
	public static String getRequestXml(SortedMap<Object, Object> parameters)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		Set<Map.Entry<Object, Object>> es = parameters.entrySet();
		for (Map.Entry<Object, Object> e : es)
		{
			Map.Entry entry = (Map.Entry) e;
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k))
			{
				sb.append("<").append(k).append(">").append("<![CDATA[").append(v).append("]]></").append(k)
						.append(">");
			}
			else
			{
				sb.append("<").append(k).append(">").append(v).append("</").append(k).append(">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 带证书的https请求
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param outputStr
	 *            提交的数据
	 * @return 返回微信服务器响应的信息
	 */
	public static String httpsRequestSSL(String certPath, String certPassword, String requestUrl, String outputStr)
	{

		KeyStore keyStore = null;
		try
		{
			keyStore = KeyStore.getInstance("PKCS12");
		}
		catch (KeyStoreException e)
		{
			e.printStackTrace();
			System.err.println("获取数字证书实例失败");
		}

		FileInputStream instream = null;
		try
		{
			instream = new FileInputStream(new File(certPath));// 证书路径
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.err.println("获取文件失败，请传入正确路径");
		}
		try
		{
			if (keyStore != null)
			{
				keyStore.load(instream, certPassword.toCharArray());// 123..为证书密码,默认为商户ID
			}
		}
		catch (NoSuchAlgorithmException | CertificateException | IOException e)
		{
			System.err.println("证书密码错误");
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (instream != null)
				{
					instream.close();
				}
			}
			catch (IOException e)
			{
				System.err.println("关闭流失败");
				e.printStackTrace();
			}
		}

		SSLContext sslcontext = null;
		try
		{
			sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, certPassword.toCharArray()).build();
		}
		catch (KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException | KeyStoreException e)
		{
			e.printStackTrace();
			System.err.println("证书密码错误");
		} // 123..为证书密码
		SSLConnectionSocketFactory sslsf = null;
		if (sslcontext != null)
		{
			sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
					SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		}
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try
		{

			HttpPost httpPost = new HttpPost(requestUrl);
			StringEntity myEntity = new StringEntity(outputStr, "UTF-8");
			httpPost.setEntity(myEntity);
			// httppost.addHeader("Content-Type", "text/xml");

			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			httpPost.addHeader("Accept", "*/*");

			try (CloseableHttpResponse response = httpclient.execute(httpPost))
			{
				HttpEntity entity = response.getEntity();
				if (entity != null)
				{
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
					String text;
					String res = "";// 结果
					while ((text = bufferedReader.readLine()) != null)
					{
						res += text;
					}
					return res;
				}
				EntityUtils.consume(null);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.err.println("HttpClient：执行带有证书的请求失败");
		}
		finally
		{
			try
			{
				httpclient.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
				System.err.println("关闭流失败");
			}
		}
		return null;
	}

	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * 
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map<String, String> doXMLParse(String strxml) throws JDOMException, IOException
	{
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if (null == strxml || "".equals(strxml))
		{
			return null;
		}

		Map<String, String> m = new HashMap<String, String>();

		InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List<?> list = root.getChildren();
		Iterator<?> it = list.iterator();
		while (it.hasNext())
		{
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List<?> children = e.getChildren();
			if (children.isEmpty())
			{
				v = e.getTextNormalize();
			}
			else
			{
				v = getChildrenText(children);
			}

			m.put(k, v);
		}

		// 关闭流
		in.close();

		return m;
	}

}
