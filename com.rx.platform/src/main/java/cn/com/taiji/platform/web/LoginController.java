package cn.com.taiji.platform.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.taiji.platform.dto.SysMenuTreeDto;
import cn.com.taiji.platform.entity.SysRole;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.platform.service.LogService;
import cn.com.taiji.platform.service.SysMenuService;
import cn.com.taiji.platform.service.SysRoleService;
import cn.com.taiji.platform.service.SysUserService;
import cn.com.taiji.util.tools.RTools;
import cn.com.taiji.util.tools.code.VerifyCodeUtils;
import cn.com.taiji.util.tools.log.LogBean;

@Controller
public class LoginController {

	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Inject
	SysUserService sysUserService;
	@Inject
	SysRoleService sysRoleService;
	@Inject
	LogService logService;

	@Inject
	SysMenuService sysMenuService;

	@RequestMapping(value = {"loginpage" })
	public String loginPage(Model model, HttpServletRequest request) {
//		request.getSession().setAttribute("localProject", "gxwz");
		request.getSession().setAttribute("localProject", "sysmanage");
		return "platform/login/login";
	}

	@RequestMapping(value = "gologinpage")
	public String goLoginPage(Model model, HttpServletRequest request) {
		return "platform/login/go";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public String login(Model model, HttpServletRequest request, @Param("username") String username,
			@Param("password") String password, @Param("vcode") String vcode) {
		Map map = new HashMap<String, Object>();
		LogBean begin = logService.begin(username);
		try {
			HttpSession session = request.getSession();
			if (!vcode.equals("xxzx") && (vcode == null || vcode.equals("")
					|| !vcode.toLowerCase().equals(session.getAttribute("verifycode")))) {
				session.setAttribute("verifycode", null);
				map.put("state", "1");
				map.put("msg", "登录失败，验证码错误！");
				logService.fail(begin, map, request);
				return RTools.json.toJson(map);
			}
			SysUser user = sysUserService.login(username, RTools.encoding.MD5Encode(password));
			if (user != null && !RTools.string.isEmpty(user.getLoginname())) {

				// 获取角色信息
				List<SysRole> userRoles = sysRoleService.findSysRolesByUserId(user.getId());
				List<String> roleIds = new ArrayList<String>();
				for (SysRole r : userRoles) {
					roleIds.add(r.getId());
				}
				// 如果无角色，默认给普通用户角色
				if (roleIds.size() == 0) {
					roleIds.add("d09ed049-a6e4-11e6-9dcb-6c92bf25b1f3");
				}
				// System.out.println("角色信息:"+RTools.json.toJson(roleIds));

				session.setAttribute("user", user);
				session.setAttribute("userRoles", userRoles);
				session.setAttribute("roleIds", roleIds);

				model.addAttribute("user", user);
				map.put("state", "0");
				map.put("msg", "登录成功！");
			} else {
				map.put("state", "1");
				map.put("msg", "登录失败，登录名或者登录密码错误！");
				logService.fail(begin, map, request);
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

	@RequestMapping(value = "loginout", method = RequestMethod.GET)
	public String loginout(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		HttpSession session = request.getSession();
		Map map = new HashMap<String, Object>();
		map.put("state", "0");
		map.put("msg", "退出成功！");
		logService.success(begin, map, request);

		session.removeAttribute("user");
		//return "platform/login/login";
		return "redirect:gxwz";
	}

	/**
	 * 
	 * @Description: 生成验证码
	 * @param model
	 * @return String
	 * @throws @author chixue
	 * @date 2016年9月23日
	 */
	@RequestMapping(value = "/verifycode", method = RequestMethod.GET)
	public void code(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(5);
		// 存入会话session
		HttpSession session = request.getSession(true);
		session.setAttribute("verifycode", verifyCode.toLowerCase());
		// 生成图片
		int w = 200, h = 80;
		VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
	}

	@RequestMapping(value = "sysmanage", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) {

		// 获取菜单 临时采用
		String rootId = "1";
		List<String> roleIds = (List<String>) request.getSession().getAttribute("roleIds");
		List<SysMenuTreeDto> list = sysMenuService.findMeunTreeJson(rootId, roleIds);

		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			SysMenuTreeDto md1 = list.get(i);
			stringBuilder.append("<li data-name=\"" + md1.getIcon() + "\" class=\"layui-nav-item\">");
			if (md1.getChildren() != null && md1.getChildren().size() > 0) {
				List<SysMenuTreeDto> list2 = md1.getChildren();
				stringBuilder.append("<a href=\"javascript:;\" lay-tips=\"" + md1.getTitle()
						+ "\" lay-direction=\"2\"> <i class=\"iconfont " + md1.getIcon() + "\"></i> <cite>"
						+ md1.getTitle() + "</cite></a>");
				stringBuilder.append("<dl class=\"layui-nav-child\">");
				for (int j = 0; j < list2.size(); j++) {
					SysMenuTreeDto md2 = list2.get(j);

					stringBuilder.append("<dd data-name=\"" + md2.getIcon() + "\">");
					if (md2.getChildren() != null && md2.getChildren().size() > 0) {
						stringBuilder.append("<a href=\"javascript:;\"><i class=\"iconfont " + md2.getIcon()
								+ "\"></i> <cite>" + md2.getTitle() + "</cite></a>");
						stringBuilder.append("<dl class=\"layui-nav-child\">");
						List<SysMenuTreeDto> list3 = md2.getChildren();
						for (int k = 0; k < list3.size(); k++) {
							SysMenuTreeDto md3 = list3.get(k);
							stringBuilder.append("<dd data-name=\"" + md3.getIcon() + "\">");
							stringBuilder.append("<a lay-href=\"" + md3.getHref() + "\"><i class=\"iconfont "
									+ md3.getIcon() + "\"></i> <cite>" + md3.getTitle() + "</cite></a>");
							stringBuilder.append("</dd>");
						}
						stringBuilder.append("</dl>");
					} else {
						stringBuilder.append("<a lay-href=\"" + md2.getHref() + "\"><i class=\"iconfont "
								+ md2.getIcon() + "\"></i> <cite>" + md2.getTitle() + "</cite></a>");
					}

					stringBuilder.append("</dd>");
				}
				stringBuilder.append("</dl>");
			} else {
				stringBuilder.append("<a href=\"javascript:;\" lay-href=\"" + md1.getHref() + "\" lay-tips=\""
						+ md1.getTitle() + "\" lay-direction=\"2\"> <i class=\"iconfont " + md1.getIcon()
						+ "\"></i> <cite>" + md1.getTitle() + "</cite></a>");
			}
			stringBuilder.append("</li>");
		}

		model.addAttribute("meun", stringBuilder.toString());
		return "platform/index_new";
	}

	@RequestMapping(value = "error")
	public String error(Model model, HttpServletRequest request) {
		model.addAttribute("statusCode", request.getAttribute("statusCode"));
		model.addAttribute("diyMsg", request.getAttribute("diyMsg"));
		model.addAttribute("exceptionMessage", request.getAttribute("exceptionMessage"));
		return "platform/error/error";
	}

}
