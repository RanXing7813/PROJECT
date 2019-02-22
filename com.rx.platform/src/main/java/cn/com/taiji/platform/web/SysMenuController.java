package cn.com.taiji.platform.web;

import java.util.HashMap;
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

import cn.com.taiji.platform.dto.SysMenuTreeDto;
import cn.com.taiji.platform.entity.SysMenu;
import cn.com.taiji.platform.service.LogService;
import cn.com.taiji.platform.service.SysMenuService;
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.RTools;
import cn.com.taiji.util.tools.log.LogBean;

@Controller
@RequestMapping(value = "/platform/sys")
public class SysMenuController {

	private static final Logger log = LoggerFactory.getLogger(SysMenuController.class);

	@Inject
	SysMenuService sysMenuService;
	@Inject
	LogService logService;

	@RequestMapping(value = "menu_list")
	public String listMenu(Model model, HttpServletRequest request) {
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

		page = sysMenuService.findMenuList(page, search);
		model.addAttribute("list", page.getDatalist());
		model.addAttribute("blanklist", page.getBlanklist());
		model.addAttribute("search", search);
		model.addAttribute("page", page);
		logService.success(begin, page, request);
		return "platform/sys/menu/menu_list";
	}

	// 首页左边功能菜单
	@RequestMapping(value = "menu_tree", method = RequestMethod.GET)
	@ResponseBody
	public String menuTree(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		String rootId = "1";
		List<String> roleIds = (List<String>) request.getSession().getAttribute("roleIds");
		List<SysMenuTreeDto> list = sysMenuService.findMeunTreeJson(rootId, roleIds);
		logService.success(begin, list, request);
		return RTools.json.toJson(list);
	}

	@RequestMapping(value = "menu_manager", method = RequestMethod.GET)
	public String menuManage(Model model, HttpServletRequest request) {
		return "platform/sys/menu/menutree";
	}

	@RequestMapping(value = "menu_tree_manage", method = RequestMethod.GET)
	@ResponseBody
	public String menuTreeManage(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		//List<SysMenuTreeManageDto> list = sysMenuService.findMeunTreeManageJson(rootId);
		List<Map> list = sysMenuService.findMenuZTree();
		logService.success(begin, list, request);
		return RTools.json.toJson(list);
	}

	@RequestMapping(value = "menu_author_page", method = RequestMethod.GET)
	public String menuAuthManage(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		String roleId = request.getParameter("roleId");
		model.addAttribute("roleId", roleId);
		logService.success(begin, "角色授权页面", request);
		return "platform/sys/menu/menu_tree_author";
	}

	@RequestMapping(value = "menu_author", method = RequestMethod.POST)
	@ResponseBody
	public String findMenuZTree(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		String roleId = request.getParameter("roleId");
		List<Map> list = sysMenuService.findMenuZTree(roleId);
		logService.success(begin, "菜单授权页面", request);
		return RTools.json.toJson(list);
	}

	@RequestMapping(value = "menu_author_save", method = RequestMethod.POST)
	@ResponseBody
	public String saveMenuAuthor(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		try {

			String roleId = request.getParameter("roleId");
			String selectedIds = request.getParameter("selectedIds");
			String changeCheckedIds = request.getParameter("changeCheckedIds");
			String[] ids = selectedIds.split(";");
			String[] removeIds = changeCheckedIds.split(";");
			sysMenuService.saveRoleAuth(roleId, ids);
			sysMenuService.removeRoleAuth(roleId, removeIds, selectedIds);
			map.put("state", "0");
			map.put("msg", "授权成功！");
			logService.success(begin, "授权成功", request);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("state", "1");
			map.put("msg", e.getMessage());
			logService.fail(begin,map, request);
			e.printStackTrace();
		} finally {
			return RTools.json.toJson(map);
		}
	}
	
	@RequestMapping(value = "menu_add", method = RequestMethod.GET)
	public String addMenus(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		
		List iconsList = sysMenuService.findAllIcons();
		model.addAttribute("iconsList", iconsList);
		logService.success(begin, "菜单选择页面", request);
		return "platform/sys/menu/menu_add";
	}
	
	@RequestMapping(value = "menu_save", method = RequestMethod.POST)
	public String saveMenu(SysMenu menu, Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		try {
			if (RTools.string.isEmpty(menu.getId())) {
				menu.setId(RTools.encoding.getUUID32());
				sysMenuService.saveMenu(menu);
			} else {
				sysMenuService.saveMenu(menu);
			}
			model.addAttribute("diyMsg", "操作成功！");
			logService.success(begin, "菜单保存成功！", request);
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("diyMsg", "操作失败！");
			model.addAttribute("exceptionMessage", e.getMessage());
			logService.fail(begin, "菜单保存失败！", request);
			log.debug(e.getMessage());
		} finally {
			return "platform/success/success";
		}
	}
	
	@RequestMapping(value = "menu_select_page", method = RequestMethod.GET)
	public String menuSelect(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		logService.success(begin, "菜单选择页面", request);
		return "platform/sys/menu/menu_tree_select";
	}
	
	@RequestMapping(value = "menu_del", method = RequestMethod.POST)
	@ResponseBody
	public String delMenu(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		try {
			String menuId = request.getParameter("menuId");
			sysMenuService.delMenu(menuId);
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
