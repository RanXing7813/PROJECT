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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.taiji.platform.entity.SysDept;
import cn.com.taiji.platform.service.LogService;
import cn.com.taiji.platform.service.SysDeptService;
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.RTools;
import cn.com.taiji.util.tools.log.LogBean;

@Controller
@RequestMapping(value = "/platform/sys")
public class SysDeptController {

	private static final Logger log = LoggerFactory.getLogger(SysDeptController.class);

	@Inject
	SysDeptService sysDeptService;

	@Inject
	LogService logService;

	@RequestMapping(value = "dept_list")
	public String listDept(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		String search = request.getParameter("search");
		String rootId = request.getParameter("rootId");
		String rootdeptname = request.getParameter("rootdeptname");
		String currentpage = request.getParameter("currentpage");
		String pagesize = request.getParameter("pagesize");

		if (RTools.string.isEmpty(pagesize)) {
			pagesize = "10";
		}
		if (RTools.string.isEmpty(currentpage)) {
			currentpage = "1";
		}

		Pagination page = new Pagination(Integer.valueOf(currentpage), Integer.valueOf(pagesize));

		page = sysDeptService.findDeptList(page, search, rootId);
		model.addAttribute("list", page.getDatalist());
		model.addAttribute("blanklist", page.getBlanklist());
		model.addAttribute("search", search);
		model.addAttribute("rootId", rootId);
		model.addAttribute("rootdeptname", rootdeptname);
		model.addAttribute("page", page);
		logService.success(begin, page, request);
		return "platform/sys/dept/dept_list";
	}

	// 机构树
	@RequestMapping(value = "dept_iframe")
	public String deptIframe(Model model, HttpServletRequest request) {
		return "platform/sys/dept/dept_iframe";
	}

	// 机构-人员树
	@RequestMapping(value = "deptuser_iframe")
	public String deptUserIframe(Model model, HttpServletRequest request) {
		return "platform/sys/dept/dept_user_iframe";
	}

	// 机构-人员选择
	@RequestMapping(value = "deptuser_select_iframe")
	public String deptUserSelectIframe(Model model, HttpServletRequest request) {
		return "platform/sys/dept/dept_user_select_iframe";
	}

	// 机构数
	@RequestMapping(value = "dept_tree_page")
	public String deptTreePage(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		String type = request.getParameter("type");
		String chkStyle = request.getParameter("chkStyle");
		String rootId = request.getParameter("id");
		String selectedIds = request.getParameter("selectedIds");
		if ("select".equals(type)) {// 可选
			model.addAttribute("rootId", rootId);
			model.addAttribute("chkStyle", chkStyle);
			model.addAttribute("selectedIds", selectedIds);
			logService.success(begin, "机构数", request);
			return "platform/sys/dept/dept_tree_select";
		} else {
			String listtype = request.getParameter("listtype");
			model.addAttribute("listtype", listtype);
			model.addAttribute("rootId", rootId);
			logService.success(begin, "机构数", request);
			return "platform/sys/dept/dept_tree";
		}
	}

	@RequestMapping(value = "dept_tree")
	@ResponseBody
	public String deptTree(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		String rootId = request.getParameter("id");
		String checkedIds = request.getParameter("selectedIds");
		if (RTools.string.isEmpty(rootId)) {
			rootId = "0";
		}
		List<Map> deptList = sysDeptService.listDeptByRootId(rootId, checkedIds);
		logService.success(begin, deptList, request);
		return RTools.json.toJson(deptList);
	}
	
	@RequestMapping(value = "dept_add")
	public String deptAdd(Model model, HttpServletRequest request) {
		return "platform/sys/dept/dept_add";
	}
	
	@RequestMapping(value = "dept_save", method = RequestMethod.POST)
	public String saveUser(SysDept sysDept, Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		try {
			
			if (RTools.string.isEmpty(sysDept.getId())) {
				sysDept.setId(RTools.encoding.getUUID32());
				sysDept.setFlag(1);
				sysDeptService.saveDept(sysDept);
				
			} else {
				sysDeptService.saveDept(sysDept);
			}
			
			model.addAttribute("diyMsg", "操作成功！");
			logService.success(begin, "机构保存成功！", request);
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("diyMsg", "操作失败！");
			model.addAttribute("exceptionMessage", e.getMessage());
			logService.fail(begin, "用户保存失败！", request);
			e.printStackTrace();
		} finally {
			return "platform/success/success";
		}
	}

}
