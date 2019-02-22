package cn.com.taiji.platform.web;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.taiji.platform.service.SysOfficeService;
import cn.com.taiji.util.tools.RTools;

@Controller
@RequestMapping(value = "/platform/sys")
public class SysOfficeController {

	private static final Logger log = LoggerFactory.getLogger(SysOfficeController.class);

	@Inject
	SysOfficeService sysOfficeService;

	// 机构树
	@RequestMapping(value = "office_iframe")
	public String deptIframe(Model model, HttpServletRequest request) {
		return "platform/sys/dept/dept_iframe";
	}

	// 机构数
	@RequestMapping(value = "office_tree_page")
	public String deptTreePage(Model model, HttpServletRequest request) {
		String type = request.getParameter("type");
		String chkStyle = request.getParameter("chkStyle");
		String rootId = request.getParameter("id");
		String selectedIds = request.getParameter("selectedIds");
		if ("select".equals(type)) {// 可选
			model.addAttribute("rootId", rootId);
			model.addAttribute("chkStyle", chkStyle);
			model.addAttribute("selectedIds", selectedIds);
			return "platform/sys/office/office_tree_select";
		} else {
			String listtype = request.getParameter("listtype");
			model.addAttribute("listtype", listtype);
			model.addAttribute("rootId", rootId);
			return "platform/sys/dept/dept_tree";
		}
	}

	@RequestMapping(value = "office_tree")
	@ResponseBody
	public String deptTree(Model model, HttpServletRequest request) {
		String rootId = request.getParameter("id");
		String checkedIds = request.getParameter("selectedIds");
		if (RTools.string.isEmpty(rootId)) {
			rootId = "0";
		}
		List<Map> deptList = sysOfficeService.listDeptByRootId(rootId, checkedIds);
		return RTools.json.toJson(deptList);
	}

}
