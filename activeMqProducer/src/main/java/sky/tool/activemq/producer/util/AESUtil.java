
package sky.tool.activemq.producer.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import sun.misc.BASE64Decoder;

public class AESUtil
{
	public static byte[] encode(byte[] target, byte[] key)
	{
		try
		{
			Key k = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, k);
			return cipher.doFinal(target);
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] decode(byte[] target, byte[] key)
	{
		try
		{
			Key k = new SecretKeySpec(key, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, k);
			return cipher.doFinal(target);
		}
		catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException
				| BadPaddingException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String byte2HexString(byte[] target)
	{
		return Hex.encodeHexString(target, false);
	}

	public static byte[] hexString2Byte(String target)
	{
		try
		{
			return Hex.decodeHex(target);
		}
		catch (DecoderException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String base64Encrepter(byte[] target)
	{
		return Base64.encodeBase64String(target);
	}

	public static byte[] base64Decrepter(String target)
	{
		try
		{
			return new BASE64Decoder().decodeBuffer(target);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] generateKey()
	{
		try
		{
			KeyGenerator gen = KeyGenerator.getInstance("AES");
			gen.init(new SecureRandom());
			return gen.generateKey().getEncoded();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] obj2Byte(Serializable target)
	{
		ObjectOutputStream oos = null;
		ByteArrayOutputStream bos = null;
		try
		{
			bos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(bos);
			oos.writeObject(target);
			byte[] b = bos.toByteArray();
			return b;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (oos != null)
				{
					oos.close();
				}
				if (bos != null)
				{
					bos.close();
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return null;
	}

	public static Object byte2Obj(byte[] target)
	{
		ByteArrayInputStream bais = null;
		try
		{
			// 反序列化
			bais = new ByteArrayInputStream(target);
			ObjectInputStream ois = new ObjectInputStream(bais);
			return ois.readObject();
		}
		catch (IOException | ClassNotFoundException e)
		{
			
		}
		finally
		{
			try
			{
				if (bais != null)
				{
					bais.close();
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
		return null;
	}
}
