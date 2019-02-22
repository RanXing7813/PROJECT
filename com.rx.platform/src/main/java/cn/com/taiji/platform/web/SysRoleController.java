package cn.com.taiji.platform.web;

import java.util.HashMap;
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

import cn.com.taiji.platform.entity.SysRole;
import cn.com.taiji.platform.service.LogService;
import cn.com.taiji.platform.service.SysRoleService;
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.RTools;
import cn.com.taiji.util.tools.log.LogBean;

@Controller
@RequestMapping(value = "/platform/sys")
public class SysRoleController {

	private static final Logger log = LoggerFactory.getLogger(SysRoleController.class);

	@Inject
	SysRoleService sysRoleService;

	@Inject
	LogService logService;

	@RequestMapping(value = "role_list")
	public String listRole(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		String search = request.getParameter("search");
		String currentpage = request.getParameter("currentpage");
		String pagesize = request.getParameter("pagesize");

		if (RTools.string.isEmpty(pagesize)) {
			pagesize = "10";
		}
		if (RTools.string.isEmpty(currentpage)) {
			currentpage = "1";
		}

		Pagination page = new Pagination(Integer.valueOf(currentpage), Integer.valueOf(pagesize));

		page = sysRoleService.findRoleList(page, search);
		model.addAttribute("list", page.getDatalist());
		model.addAttribute("blanklist", page.getBlanklist());
		model.addAttribute("search", search);
		model.addAttribute("page", page);
		logService.success(begin, page, request);
		return "platform/sys/role/role_list";
	}

	@RequestMapping(value = "auth_adduser", method = RequestMethod.POST)
	@ResponseBody
	public String authUser(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		try {

			String roleId = request.getParameter("roleId");
			String selectedIds = request.getParameter("selectedIds");
			String[] ids = selectedIds.split(";");
			sysRoleService.addUserAuth(roleId, ids);
			map.put("state", "0");
			map.put("msg", "授权成功！");
			logService.success(begin, "授权成功", request);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("state", "1");
			map.put("msg", e.getMessage());
			log.debug(e.getMessage());
		} finally {
			return RTools.json.toJson(map);
		}

	}

	@RequestMapping(value = "auth_removeuser", method = RequestMethod.POST)
	@ResponseBody
	public String authRomoveUser(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		try {

			String roleId = request.getParameter("roleId");
			String[] ids = request.getParameterValues("checked");

			sysRoleService.removeUserAuth(roleId, ids);

			map.put("state", "0");
			map.put("msg", "删除成功！");
			logService.success(begin, "删除成功", request);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("state", "1");
			map.put("msg", e.getMessage());
			logService.fail(begin, map, request);
			log.debug(e.getMessage());
		}
		return RTools.json.toJson(map);
	}
	
	@RequestMapping(value = "role_add", method = RequestMethod.GET)
	public String addRole(Model model, HttpServletRequest request) {
		SysRole role = new SysRole();
		role.setId(null);
		role.setFlag("1");
		role.setUsrecount(0);
		model.addAttribute("role", role);
		return "platform/sys/role/role_add";
	}
	
	@RequestMapping(value = "role_save", method = RequestMethod.POST)
	public String saveRole(SysRole role, Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		try {
			if (RTools.string.isEmpty(role.getId())) {
				role.setId(RTools.encoding.getUUID32());
				sysRoleService.saveRole(role);
			} else {
				sysRoleService.saveRole(role);
			}
			model.addAttribute("diyMsg", "操作成功！");
			logService.success(begin, "角色保存成功！", request);
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("diyMsg", "操作失败！");
			model.addAttribute("exceptionMessage", e.getMessage());
			logService.fail(begin, "角色保存失败！", request);
			log.debug(e.getMessage());
		} finally {
			return "platform/success/success";
		}
	}
	
	@RequestMapping(value = "rolename_check", method = RequestMethod.POST)
	@ResponseBody
	public String checkRoleNameRepeate(SysRole role, Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		try {
			// 重名校验
			SysRole u = sysRoleService.findRoleByRoleName(role.getRoleName());

			if (u == null) {
				map.put("state", "0");
				map.put("msg", "角色名称不重复！");
			} else {
				map.put("state", "1");
				map.put("msg", "角色名称重复，请修改角色名称！");
			}
			logService.success(begin, map, request);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("state", "1");
			map.put("msg", e.getMessage());
			logService.fail(begin, map, request);
			log.debug(e.getMessage());
		}
		return RTools.json.toJson(map);
	}
	
	@RequestMapping(value = "role_del", method = RequestMethod.POST)
	@ResponseBody
	public String delRole(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		try {
			String roleId = request.getParameter("roleId");
			sysRoleService.delRole(roleId);
			map.put("state", "0");
			map.put("msg", "删除成功！");
			logService.success(begin, map, request);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("state", "1");
			map.put("msg", e.getMessage());
			logService.fail(begin, map, request);
			log.debug(e.getMessage());
			
		}
		return RTools.json.toJson(map);
	}

}
