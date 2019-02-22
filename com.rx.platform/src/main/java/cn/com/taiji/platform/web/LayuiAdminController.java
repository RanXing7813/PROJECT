package cn.com.taiji.platform.web;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.taiji.platform.dto.SysMenuTreeDto;
import cn.com.taiji.platform.service.LogService;
import cn.com.taiji.platform.service.SysMenuService;

@Controller
@RequestMapping(value = "/layuiadmin")
public class LayuiAdminController {

	private static final Logger log = LoggerFactory.getLogger(LayuiAdminController.class);

	@Inject
	LogService logService;
	

	@Inject
	SysMenuService sysMenuService;
	
	@RequestMapping(value = "home/console", method = RequestMethod.GET)
	public String homeConsole(Model model, HttpServletRequest request) {
		return "platform/views/home/console";
	}
	
	@RequestMapping(value = "theme", method = RequestMethod.GET)
	public String theme(Model model, HttpServletRequest request) {
		return "platform/views/component/tpl/system/theme";
	}
	
	//新的后台框架首页
	@RequestMapping(value = "index_new", method = RequestMethod.GET)
	public String indexAdminNew(Model model, HttpServletRequest request) {
		
		//获取菜单 临时采用
		String rootId = "1";
		List<String> roleIds = (List<String>) request.getSession().getAttribute("roleIds");
		List<SysMenuTreeDto> list = sysMenuService.findMeunTreeJson(rootId, roleIds);
		
		StringBuilder stringBuilder=new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			SysMenuTreeDto md1 = list.get(i);
			stringBuilder.append("<li data-name=\""+md1.getIcon()+"\" class=\"layui-nav-item\">");
			if(md1.getChildren()!=null&&md1.getChildren().size()>0) {
				List<SysMenuTreeDto> list2 = md1.getChildren();
				stringBuilder.append("<a href=\"javascript:;\" lay-tips=\""+md1.getTitle()+"\" lay-direction=\"2\"> <i class=\"layui-icon "+md1.getIcon()+"\"></i> <cite>"+md1.getTitle()+"</cite></a>");
				stringBuilder.append("<dl class=\"layui-nav-child\">");
				for (int j = 0; j < list2.size(); j++) {
					SysMenuTreeDto md2 = list2.get(j);
					
					stringBuilder.append("<dd data-name=\""+md2.getIcon()+"\">");
					if(md2.getChildren()!=null&&md2.getChildren().size()>0) {
						stringBuilder.append("<a href=\"javascript:;\"><i class=\"layui-icon "+md2.getIcon()+"\"></i> <cite>"+md2.getTitle()+"</cite></a>");
						stringBuilder.append("<dl class=\"layui-nav-child\">");
						List<SysMenuTreeDto> list3 = md2.getChildren();
						for (int k = 0; k < list3.size(); k++) {
							SysMenuTreeDto md3 = list3.get(k);
							stringBuilder.append("<dd data-name=\""+md3.getIcon()+"\">");
							stringBuilder.append("<a lay-href=\""+md3.getHref()+"\"><i class=\"layui-icon "+md3.getIcon()+"\"></i> <cite>"+md3.getTitle()+"</cite></a>");
							stringBuilder.append("</dd>");
						}
						stringBuilder.append("</dl>");
					}else {
						stringBuilder.append("<a lay-href=\""+md2.getHref()+"\"><i class=\"layui-icon "+md2.getIcon()+"\"></i> <cite>"+md2.getTitle()+"</cite></a>");
					}
					
					stringBuilder.append("</dd>");
				}
				stringBuilder.append("</dl>");
			}else {
				stringBuilder.append("<a href=\"javascript:;\" lay-href=\""+md1.getHref()+"\" lay-tips=\""+md1.getTitle()+"\" lay-direction=\"2\"> <i class=\"layui-icon "+md1.getIcon()+"\"></i> <cite>"+md1.getTitle()+"</cite></a>");
			}
			stringBuilder.append("</li>");
		}
		
		model.addAttribute("meun", stringBuilder.toString());
		return "platform/index_new";
	}

}
