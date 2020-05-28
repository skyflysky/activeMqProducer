
package sky.tool.activemq.calculation.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class FileUtil
{
	private FileUtil()
	{
		
	}
	
	public static byte[] toByteArray(File file)
	{
		try
		{
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
			ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
			System.out.println("Available bytes:" + in.available());
			byte[] temp = new byte[1024];
			int size = 0;
			while ((size = in.read(temp)) != -1)
			{
				out.write(temp, 0, size);
			}
			in.close();
			byte[] content = out.toByteArray();
			System.out.println("Readed bytes count:" + content.length);
			return out.toByteArray();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static String downloadFromUrl(String url, String dir)
	{
		try
		{
			URL httpurl = new URL(url);
			String fileName = getFileNameFromUrl(url);
			File f = new File(dir + fileName);
			org.apache.commons.io.FileUtils.copyURLToFile(httpurl, f);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "Fault!";
		}
		return "Successful!";
	}

	public static String getFileNameFromUrl(String url)
	{
		String name = new Long(System.currentTimeMillis()).toString() + ".X";
		int index = url.lastIndexOf("/");
		if (index > 0)
		{
			name = url.substring(index + 1);
			if (name.trim().length() > 0)
			{
				return name;
			}
		}
		return name;
	}
}
