package cn.com.taiji.platform.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.RTools;

@Controller
@RequestMapping(value = "/platform")
public class PlatformController {

	@RequestMapping(value = "onlines")
	public String onLinesInfo(Model model, HttpServletRequest request) {
		Map<String, HttpSession> onLines = (Map<String, HttpSession>) request.getServletContext()
				.getAttribute("onLines");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 遍历onlines，针对每个session对象封装一个map(信息包含:name,createTime,lastAccessTime,ip)，把该map添加到list当中
		Iterator<Entry<String, HttpSession>> it = onLines.entrySet().iterator();
		while (it.hasNext()) {
			
			Entry<String, HttpSession> en = it.next();
			SysUser user = (SysUser) en.getValue().getAttribute("user");
			
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", en.getValue().getId());
			m.put("username", user.getUsername());
			m.put("loginname", user.getLoginname());
			m.put("deptname", user.getDeptname());
			String creationTime = sdf.format(new Date(en.getValue().getCreationTime()));
			m.put("creationTime", creationTime);
			String lastAccessTime = sdf.format(new Date(en.getValue().getLastAccessedTime()));
			m.put("lastAccessTime", lastAccessTime);
			m.put("ip", en.getValue().getAttribute("ip")); // 注意,该ip数据是在CharsetFilter中封装的

			list.add(m);
			
		}

		String currentpage = request.getParameter("currentpage");
		String pagesize = request.getParameter("pagesize");

		if (RTools.string.isEmpty(pagesize)) {
			pagesize = "10";
		}
		if (RTools.string.isEmpty(currentpage)) {
			currentpage = "1";
		}

		Pagination page = new Pagination(Integer.valueOf(currentpage), Integer.valueOf(pagesize));
		page.setPagecount((list.size() - 1) / page.getPagesize() + 1);
		page.setDatacount(list.size());
		// 对list进行截取
		page.setDatalist(list.subList(page.getStartno(), list.size() - page.getStartno() > page.getPagesize()
				? page.getStartno() + page.getPagesize() : list.size()));
		model.addAttribute("list", page.getDatalist());
		model.addAttribute("blanklist", page.getBlanklist());
		model.addAttribute("page", page);
		return "platform/sys/monitor/user_onlines_list";
	}

	@RequestMapping(value = "outlines")
	@ResponseBody
	public String outLines(Model model, HttpServletRequest request) {
		String id = request.getParameter("id");
		
		Map map = new HashMap<String, Object>();
		try {
			Map<String, HttpSession> onLines = (Map<String, HttpSession>) request.getServletContext()
					.getAttribute("onLines");
			if(!request.getSession().getId().equals(id)){
				if (onLines.containsKey(id)) {
					HttpSession session = onLines.get(id);
					session.invalidate();
					map.put("state", "0");
					map.put("msg", "强制退出成功！");
				} else {
					map.put("state", "1");
					map.put("msg", "用户已不存在！");
				}
			}else{
				map.put("state", "1");
				map.put("msg", "用户不能踢自己！");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			map.put("state", "1");
			map.put("msg", e.getMessage());
		}
		return RTools.json.toJson(map);
	}
	
	@RequestMapping(value = "home", method = RequestMethod.GET)
	public String home(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		return "platform/home";
	}
}
