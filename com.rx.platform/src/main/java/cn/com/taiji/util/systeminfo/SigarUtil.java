package cn.com.taiji.util.systeminfo;

import java.io.File;
import com.google.common.io.Resources;
import org.hyperic.sigar.Sigar;

/**
 * 
 * @ClassName:  SigarUtil   
 * @Description:TODO 获取服务器信息
 * @author: zhongdd
 * @date:   2018年5月21日 上午11:59:47   
 *     
 *     如果是Tomcat，现在项目已经可以使用了，但是如果使用其他中间件时报错的话就在终端输入
 * export LD_LIBRARY_PATH=DIR_where_Your_Sigar_Lib_is:$LD_LIBRARY_PATH
 * 或者改/etc/ld.so.conf就可以了。如果使用第一种方式。
 * 服务器重启后就需要重新设置，所以我们还是修改/etc/ld.so.conf吧。
 * /etc/ld.so.conf下面加一行sigar的路径，
 * 保存过后为了让动态链接库为系统所共享,
 * 还需运行动态链接库的管理命令ldconfig一下
 *     
 *
 */
public class SigarUtil {
	private static class SigarUtilHolder {
		private static final SigarUtil INSTANCE = new SigarUtil();
		private static final Sigar Sigar = new Sigar();
	}

	private SigarUtil() {
		try {
			String file = Resources.getResource("sigar/.sigar_shellrc").getFile();
			File classPath = new File(file).getParentFile();
			String path = System.getProperty("java.library.path");
			if (OsCheck.getOperatingSystemType() == OsCheck.OSType.Windows) {
				path += ";" + classPath.getCanonicalPath();
			} else {
				path += ":" + classPath.getCanonicalPath();
			}
			System.setProperty("java.library.path", path);
		} catch (Exception e) {
		}
	}

	public static final Sigar getInstance() {
		return SigarUtilHolder.Sigar;
	}

	public static final SigarUtil getSigarUtilInstance() {
		return SigarUtilHolder.INSTANCE;
	}
}