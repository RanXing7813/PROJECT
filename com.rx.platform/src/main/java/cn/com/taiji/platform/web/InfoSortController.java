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

import cn.com.taiji.platform.entity.CmsInfoLink;
import cn.com.taiji.platform.entity.CmsInfoSort;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.platform.service.InfoSortService;
import cn.com.taiji.platform.service.LogService;
import cn.com.taiji.util.tools.RTools;
import cn.com.taiji.util.tools.log.LogBean;

@Controller
@RequestMapping(value = "/platform/cms")
public class InfoSortController {

	private static final Logger log = LoggerFactory.getLogger(InfoSortController.class);

	@Inject
	InfoSortService infoSortService;

	@Inject
	LogService logService;

	@RequestMapping(value = "cms_iframe", method = RequestMethod.GET)
	public String cmsIframe(Model model, HttpServletRequest request) {
		return "platform/cms/cms_iframe";
	}

	@RequestMapping(value = "infosort_tree_page", method = RequestMethod.GET)
	public String infoSortTreePage(Model model, HttpServletRequest request) {
		String rootId = request.getParameter("rootId");
		model.addAttribute("rootId", rootId);
		return "platform/cms/infosort/infosort_tree";
	}

	@RequestMapping(value = "infosort_tree", method = RequestMethod.POST)
	@ResponseBody
	public String findInfoSortZTree(Model model, HttpServletRequest request) {
		String rootId = request.getParameter("rootId");
		List<Map> list = infoSortService.findInfoSortZTree(rootId);
		return RTools.json.toJson(list);
	}

	@RequestMapping(value = "infosort_save")
	@ResponseBody
	public String infoSortSave(Model model, HttpServletRequest request, CmsInfoSort sort) {
		LogBean begin = logService.begin(request.getParameterMap());
		String msg = "保存成功！";
		try {
			infoSortService.saveInfoSort(sort);
			logService.success(begin, "保存成功！", request);
		} catch (Exception e) {
			// TODO: handle exception
			msg = "保存失败！";
			logService.fail(begin, e.getMessage(), request);
			e.printStackTrace();
		} finally {
			return msg;
		}
	}
	
	@RequestMapping(value = "infosort_del")
	@ResponseBody
	public String infoSortDel(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		String msg = "删除成功！";
		String id = request.getParameter("id");
		try {
			infoSortService.delInfoSrot(id);
			logService.success(begin, "删除成功！", request);
		} catch (Exception e) {
			// TODO: handle exception
			msg = "删除失败！";
			logService.fail(begin, e.getMessage(), request);
			e.printStackTrace();
		} finally {
			return msg;
		}
	}

}
