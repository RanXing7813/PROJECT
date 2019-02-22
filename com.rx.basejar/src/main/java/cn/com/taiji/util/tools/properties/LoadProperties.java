package cn.com.taiji.util.tools.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @ClassName: LoadProperties
 * @Description:TODO 加载Properties文件
 * @author: zhongdd
 * @date: 2018年5月19日 上午8:07:03
 * 
 *
 */
public final class LoadProperties {

	private volatile static Properties mProperties;

	static {
		mProperties = new Properties();
		InputStream in = LoadProperties.class.getResourceAsStream("/properties/applications.properties");
		InputStream upload = LoadProperties.class.getResourceAsStream("/properties/upload.properties");
		try {
			mProperties.load(in);
			mProperties.load(upload);
			System.out.println("  ====== Properties文件加载结束。。。 ====== ");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Description: TODO 通过key获取properties文件中对应的配置信息
	 * @author: zhongdd
	 * @param: @param key
	 * @param: @return       
	 * @return: String      
	 * @throws
	 */
	public static String getValue(String key) {
		if (mProperties == null)
			return "";
		return mProperties.getProperty(key, "");
	}

	public static void main(String[] args) {
		System.out.println(getValue("TDBS_USERNAME"));
	}

}
