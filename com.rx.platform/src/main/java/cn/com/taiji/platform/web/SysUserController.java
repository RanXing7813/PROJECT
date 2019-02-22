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

import cn.com.taiji.platform.dto.SysUserDto;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.platform.service.LogService;
import cn.com.taiji.platform.service.SysUserService;
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.RTools;
import cn.com.taiji.util.tools.log.LogBean;

@Controller
@RequestMapping(value = "/platform/sys")
public class SysUserController {

	private static final Logger log = LoggerFactory.getLogger(SysUserController.class);

	@Inject
	SysUserService sysUserService;

	@Inject
	LogService logService;

	@RequestMapping(value = "user_list")
	public String listUser(Model model, HttpServletRequest request) {
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

		page = sysUserService.findUserList(page, search);
		model.addAttribute("list", page.getDatalist());
		model.addAttribute("blanklist", page.getBlanklist());
		model.addAttribute("search", search);
		model.addAttribute("page", page);
		logService.success(begin, page, request);
		return "platform/sys/user/user_list";
	}

	@RequestMapping(value = "userrole_list")
	public String findUsersByRoleId(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		String roleId = request.getParameter("roleId");
		String rolename = request.getParameter("rolename");
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

		page = sysUserService.findUsersByRoleId(page, roleId, search);
		model.addAttribute("list", page.getDatalist());
		model.addAttribute("blanklist", page.getBlanklist());
		model.addAttribute("search", search);
		model.addAttribute("roleId", roleId);
		model.addAttribute("rolename", rolename);
		model.addAttribute("page", page);
		logService.success(begin, page, request);
		return "platform/sys/user/userrole_list";
	}

	@RequestMapping(value = "userdept_list")
	public String findUsersByDeptCode(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		String type = request.getParameter("type");
		try {
			String deptcode = request.getParameter("deptcode");
			String search = request.getParameter("search");
			String currentpage = request.getParameter("currentpage");
			String pagesize = request.getParameter("pagesize");

			if (RTools.string.isEmpty(pagesize)) {
				pagesize = "5";
			}
			if (RTools.string.isEmpty(currentpage)) {
				currentpage = "1";
			}

			Pagination page = new Pagination(Integer.valueOf(currentpage), Integer.valueOf(pagesize));

			page = sysUserService.findUsersByDeptCode(page, deptcode, search);
			model.addAttribute("list", page.getDatalist());
			model.addAttribute("blanklist", page.getBlanklist());
			model.addAttribute("search", search);
			model.addAttribute("deptcode", deptcode);
			model.addAttribute("type", type);
			model.addAttribute("page", page);
			logService.success(begin, page, request);
		} catch (Exception e) {
			// TODO: handle exception
			logService.fail(begin, e.getMessage(), request);
			e.printStackTrace();
		}
		if ("checkbox".equals(type)) {
			return "platform/sys/user/userdept_select";
		} else {
			return "platform/sys/user/userdept_list";
		}

	}

	@RequestMapping(value = "user_add", method = RequestMethod.GET)
	public String addUser(Model model, HttpServletRequest request) {
		return "platform/sys/user/user_add";
	}

	@RequestMapping(value = "user_edit", method = RequestMethod.GET)
	public String editUser(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		String id = request.getParameter("id");
		SysUserDto user = sysUserService.findUserById(id);
		model.addAttribute("user", user);
		logService.success(begin, "用户编辑！", request);
		return "platform/sys/user/user_edit";
	}

	@RequestMapping(value = "user_view", method = RequestMethod.GET)
	public String viewUser(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		String id = request.getParameter("id");
		SysUserDto user = sysUserService.findUserById(id);
		model.addAttribute("user", user);
		logService.success(begin, "用户查看", request);
		return "platform/sys/user/user_view";
	}

	@RequestMapping(value = "user_save", method = RequestMethod.POST)
	public String saveUser(SysUser sysUser, Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		try {
			if (RTools.string.isEmpty(sysUser.getId())) {
				sysUser.setId(RTools.encoding.getUUID32());
				String password2 = request.getParameter("password2");
				sysUser.setPassword(RTools.encoding.MD5Encode(password2));
				sysUser.setCreatetime(RTools.dateTimeUtil.getNowTime(1));
				sysUserService.saveUser(sysUser);
			} else {
				sysUser.setUpdatetime(RTools.dateTimeUtil.getNowTime(1));
				sysUserService.saveUser(sysUser);
			}
			model.addAttribute("diyMsg", "操作成功！");
			logService.success(begin, "用户保存成功！", request);
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

	@RequestMapping(value = "user_check", method = RequestMethod.POST)
	@ResponseBody
	public String checkRepeate(SysUser sysUser, Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		try {
			// 重名校验
			SysUser u = sysUserService.findUserByLoginName(sysUser.getLoginname());

			if (u == null) {
				sysUser.setId(RTools.encoding.getUUID32());
				map.put("state", "0");
				map.put("msg", "登录名不重复！");
			} else {
				map.put("state", "1");
				map.put("msg", "登录名重复，请修改登录名！");
			}
			logService.success(begin, map, request);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("state", "1");
			map.put("msg", e.getMessage());
			logService.fail(begin, map, request);
		}
		return RTools.json.toJson(map);
	}

	@RequestMapping(value = "user_del", method = RequestMethod.POST)
	@ResponseBody
	public String delUser(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		try {

			String[] ids = request.getParameterValues("checked");

			sysUserService.delUser(ids);

			map.put("state", "0");
			map.put("msg", "删除成功！");
			logService.success(begin, map, request);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("state", "1");
			map.put("msg", e.getMessage());
			logService.fail(begin, map, request);
		}
		return RTools.json.toJson(map);
	}

	@RequestMapping(value = "user_changePwd", method = RequestMethod.POST)
	@ResponseBody
	public String changePwd(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		try {

			String id = request.getParameter("id");
			String password1 = request.getParameter("password1");

			SysUserDto usreDto = sysUserService.findUserById(id);
			SysUser user = new SysUser(usreDto);

			user.setPassword(RTools.encoding.MD5Encode(password1));
			user.setUpdatetime(RTools.dateTimeUtil.getNowTime(1));

			sysUserService.saveUser(user);

			map.put("state", "0");
			map.put("msg", "密码修改成功！");
			logService.success(begin, map, request);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("state", "1");
			map.put("msg", e.getMessage());
			logService.fail(begin, map, request);
		}
		return RTools.json.toJson(map);
	}

	@RequestMapping(value = "user_resetpw", method = RequestMethod.POST)
	@ResponseBody
	public String resetPwd(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		try {

			String id = request.getParameter("id");
			String password1 = "Aa123123";

			SysUserDto usreDto = sysUserService.findUserById(id);
			SysUser user = new SysUser(usreDto);

			user.setPassword(RTools.encoding.MD5Encode(password1));
			user.setUpdatetime(RTools.dateTimeUtil.getNowTime(1));

			sysUserService.saveUser(user);

			map.put("state", "0");
			map.put("msg", "密码重置成功！密码为：" + password1);

			logService.success(begin, map, request);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("state", "1");
			map.put("msg", "重置失败！ " + e.getMessage());
			logService.fail(begin, map, request);
		}
		return RTools.json.toJson(map);
	}

}
