
package sky.tool.activemq.producer.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * JWT工具类
 **/
public class JWTUtil
{
	private final static String BASE_64_SECRET = "abcdefg";
	private final static int EXPIRE_SECOND = 432000000;

	private JWTUtil ()
	{
		
	}
	
	/**
	 *
	 * 解析JWT字符串
	 * 
	 * @param jsonWebToken
	 * @return
	 * @throws Exception
	 */
	public static Claims parseJWT(String jsonWebToken)
	{
		Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(BASE_64_SECRET))
				.setAllowedClockSkewSeconds(300).parseClaimsJws(jsonWebToken).getBody();
		return claims;
	}

	public static void main(String[] args) throws InterruptedException
	{
		String token = createJWToken("312312312312");
		System.out.println(token);
		Claims cs = parseJWT(token);
		System.out.println(cs.get("user_id"));
		System.out.println(cs.isEmpty());
		System.out.println(DateUtil.dateTimeStr(cs.getExpiration()));
	}

	/**
	 * 用户登录成功后生成Jwt 使用Hs256算法 私匙使用用户密码
	 *
	 * @param userId
	 * @return
	 */
	public static String createJWToken(String userId)
	{
		// 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		// 生成JWT的时间
		long nowMillis = System.currentTimeMillis();
		Date now = new Date(nowMillis);

		// 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
		Map<String, Object> claims = new HashMap<String, Object>(5);
		claims.put("user_id", userId);

		// 生成签发人
		String subject = "ztrx";

		// 下面就是在为payload添加各种标准声明和私有声明了
		// 这里其实就是new一个JwtBuilder，设置jwt的body
		JwtBuilder builder = Jwts.builder()
				// 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
				.setClaims(claims)
				// 设置 102.
				// jti(JWT
				// 45)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
				.setId(UUID.randomUUID().toString())
				// iat: jwt的签发时间
				.setIssuedAt(now)
				// 代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
				.setSubject(subject)
				// 设置签名使用的签名算法和签名使用的秘钥
				.signWith(signatureAlgorithm, BASE_64_SECRET);

		if (EXPIRE_SECOND >= 0)
		{
			long expMillis = nowMillis + EXPIRE_SECOND;
			Date exp = new Date(expMillis);
			// 设置过期时间
			builder.setExpiration(exp);
		}

		return builder.compact();
	}
}
