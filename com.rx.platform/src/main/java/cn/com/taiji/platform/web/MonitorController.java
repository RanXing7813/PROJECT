package cn.com.taiji.platform.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.taiji.util.systeminfo.SigarUtil;
import cn.com.taiji.util.systeminfo.SystemInfoUtil;

/**
 * 
 * @Description:  系统监控控制类
 * @author: zhongdd     
 * @date:   2018年5月21日 下午12:35:54   
 * @version V1.0 
 *
 */
@Controller
@RequestMapping(value = "/platform/monitor")
public class MonitorController {

	/**
	 * 
	 * @Description:系统基本信息
	 * @author: zhongdd
	 * @date: 2018年5月21日
	 * @param: @param model
	 * @param: @param request
	 * @param: @param response
	 * @param: @return
	 * @param: @throws Exception       
	 * @return: Map      monitor
	 * @throws
	 */
	@RequestMapping(value = "/systemInfo")
	@ResponseBody
	public Map systemInfo(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		return SystemInfoUtil.SystemProperty();
	}

	/**
	 * 
	 * @Description:cpu,jvm,内存使用率相关信息
	 * @author: zhongdd
	 * @date: 2018年5月21日 下午12:36:11
	 * @param: @param model
	 * @param: @param request
	 * @param: @param response
	 * @param: @return
	 * @param: @throws Exception       
	 * @return: Map      
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/usage")
	public Map usage(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Sigar sigar = SigarUtil.getInstance();
		Map map = SystemInfoUtil.usage(sigar);
		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/memory")
	public Map memory(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Sigar sigar = SigarUtil.getInstance();
		Map map = SystemInfoUtil.memory(sigar);

		return map;
	}

	@ResponseBody
	@RequestMapping(value = "/diskInfos")
	public List<Map> diskInfos(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Sigar sigar = SigarUtil.getInstance();
		List<Map> map = SystemInfoUtil.diskInfos(sigar);

		return map;
	}

	@RequestMapping(value = "/monitorInfo")
	public String monitorInfo(Model model) {
		model.addAttribute("map", SystemInfoUtil.SystemProperty());
		Sigar sigar = SigarUtil.getInstance();
		try {
			List<Map> map = SystemInfoUtil.diskInfos(sigar);
			model.addAttribute("diskList", map);
		} catch (SigarException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return "platform/sys/monitor/monitorInfo";
	}
}
