package cn.com.taiji.util.systeminfo;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.hyperic.sigar.Swap;

import cn.com.taiji.util.tools.RTools;

/**
 * 
 * @ClassName:  SystemInfoUtil   
 * @Description:TODO 服务器信息工具类
 * @author: zhongdd
 * @date:   2018年5月21日 下午12:00:55   
 *     
 *
 */
public class SystemInfoUtil {
	public static Map SystemProperty() {
		Map monitorMap = new HashMap();
		Runtime r = Runtime.getRuntime();
		Properties props = System.getProperties();
		InetAddress addr = null;
		String ip = "";
		String hostName = "";
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			ip = "无法获取主机IP";
			hostName = "无法获取主机名";
		}
		if (addr != null) {
			try {
				ip = addr.getHostAddress();
			} catch (Exception e) {
				ip = "无法获取主机IP";
			}
			try {
				hostName = addr.getHostName();
			} catch (Exception e) {
				hostName = "无法获取主机名";
			}
		}
		monitorMap.put("hostIp", ip);
		monitorMap.put("hostName", hostName);
		monitorMap.put("osName", props.getProperty("os.name"));
		monitorMap.put("arch", props.getProperty("os.arch"));
		monitorMap.put("osVersion", props.getProperty("os.version"));
		monitorMap.put("processors", Integer.valueOf(r.availableProcessors()));
		monitorMap.put("javaVersion", props.getProperty("java.version"));
		monitorMap.put("vendor", props.getProperty("java.vendor"));
		monitorMap.put("javaUrl", props.getProperty("java.vendor.url"));
		monitorMap.put("javaHome", props.getProperty("java.home"));
		monitorMap.put("tmpdir", props.getProperty("java.io.tmpdir"));
		return monitorMap;
	}

	public static Map memory(Sigar sigar) {
		Map monitorMap = new HashMap();
		try {
			Runtime r = Runtime.getRuntime();
			monitorMap.put("jvmTotal", div(r.totalMemory(), 1048576.0D, 2) + "M");
			monitorMap.put("jvmUse", div(r.totalMemory() - r.freeMemory(), 1048576.0D, 2) + "M");
			monitorMap.put("jvmFree", div(r.freeMemory(), 1048576.0D, 2) + "M");
			monitorMap.put("jvmUsage", Double.valueOf(div(r.totalMemory() - r.freeMemory(), r.totalMemory(), 2)));

			Mem mem = sigar.getMem();

			monitorMap.put("ramTotal", div(mem.getTotal(), 1073741824.0D, 2) + "G");
			monitorMap.put("ramUse", div(mem.getUsed(), 1073741824.0D, 2) + "G");
			monitorMap.put("ramFree", div(mem.getFree(), 1073741824.0D, 2) + "G");
			monitorMap.put("ramUsage", Double.valueOf(div(mem.getUsed(), mem.getTotal(), 2)));

			Swap swap = sigar.getSwap();

			monitorMap.put("swapTotal", div(swap.getTotal(), 1073741824.0D, 2) + "G");

			monitorMap.put("swapUse", div(swap.getUsed(), 1073741824.0D, 2) + "G");

			monitorMap.put("swapFree", div(swap.getFree(), 1073741824.0D, 2) + "G");
			monitorMap.put("swapUsage", Double.valueOf(div(swap.getUsed(), swap.getTotal(), 2)));
		} catch (Exception localException) {
		}
		return monitorMap;
	}

	public static Map usage(Sigar sigar) {
		Map monitorMap = new HashMap();
		try {
			Runtime r = Runtime.getRuntime();
			monitorMap.put("jvmUsage",
					Long.valueOf(Math.round(div(r.totalMemory() - r.freeMemory(), r.totalMemory(), 2) * 100.0D)));

			Mem mem = sigar.getMem();

			monitorMap.put("ramUsage", Long.valueOf(Math.round(div(mem.getUsed(), mem.getTotal(), 2) * 100.0D)));

			List<Map> cpu = cpuInfos(sigar);
			double b = 0.0D;
			for (Map m : cpu) {
				b += Double.valueOf(String.valueOf(m.get("cpuTotal"))).doubleValue();
			}
			monitorMap.put("cpuUsage", Long.valueOf(Math.round(b / cpu.size())));
		} catch (Exception localException) {
		}
		return monitorMap;
	}

	public static List<Map> cpuInfos(Sigar sigar) {
		List monitorMaps = new ArrayList();
		try {
			CpuPerc[] cpuList = sigar.getCpuPercList();
			for (CpuPerc cpuPerc : cpuList) {
				Map monitorMap = new HashMap();
				monitorMap.put("cpuUserUse", Long.valueOf(Math.round(cpuPerc.getUser() * 100.0D)));
				monitorMap.put("cpuSysUse", Long.valueOf(Math.round(cpuPerc.getSys() * 100.0D)));
				monitorMap.put("cpuWait", Long.valueOf(Math.round(cpuPerc.getWait() * 100.0D)));
				monitorMap.put("cpuFree", Long.valueOf(Math.round(cpuPerc.getIdle() * 100.0D)));
				monitorMap.put("cpuTotal", Long.valueOf(Math.round(cpuPerc.getCombined() * 100.0D)));
				monitorMaps.add(monitorMap);
			}
		} catch (Exception localException) {
		}
		return monitorMaps;
	}

	public static List<Map> diskInfos(Sigar sigar) throws SigarException {
		List monitorMaps = new ArrayList();
		FileSystem[] fslist = sigar.getFileSystemList();
		for (int i = 0; i < fslist.length; i++) {
			Map monitorMap = new HashMap();
			FileSystem fs = fslist[i];

			FileSystemUsage usage = null;
			try {
				usage = sigar.getFileSystemUsage(fs.getDirName());
				switch (fs.getType()) {
				case 0:
					break;
				case 1:
					break;
				case 2:
					monitorMap.put("diskName", fs.getDevName());
					monitorMap.put("diskType", fs.getSysTypeName());

					monitorMap.put("diskTotal", (Long.valueOf(usage.getFree())+Long.valueOf(usage.getUsed()))/(1024*1024)+" GB");

					monitorMap.put("diskFree", Long.valueOf(usage.getFree())/(1024*1024)+" GB");

					monitorMap.put("diskUse", Long.valueOf(usage.getUsed())/(1024*1024)+" GB");
					double usePercent = usage.getUsePercent() * 100.0D;

					monitorMap.put("diskUsage", Double.valueOf(usePercent)+"%");
					monitorMaps.add(monitorMap);
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
				case 6:
				}
			} catch (SigarException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("异常信息："+e.getMessage());
			}

		}

		return monitorMaps;
	}

	public static double div(double v1, double v2) {
		return div(v1, v2, 10);
	}

	public static double div(double v1, double v2, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, 4).doubleValue();
	}
	
	public static void main(String[] args) {
//		Sigar sigar = SigarUtil.getInstance();
//		System.out.println(RTools.json.toJson(SystemProperty()));
//		System.out.println("---------");
//		System.out.println(RTools.json.toJson(memory(sigar)));
//		System.out.println("---------");
//		System.out.println(RTools.json.toJson(usage(sigar)));
//		System.out.println("---------");
//		try {
//			System.out.println(RTools.json.toJson(diskInfos(sigar)));
//		} catch (SigarException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("---------");
//		System.out.println(RTools.json.toJson(cpuInfos(sigar)));
	}
}