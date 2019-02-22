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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.taiji.platform.entity.CmsInfoLink;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.platform.service.InfoLinkService;
import cn.com.taiji.platform.service.LogService;
import cn.com.taiji.platform.service.UploadFileService;
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.RTools;
import cn.com.taiji.util.tools.log.LogBean;

@Controller
@RequestMapping(value = "/platform/cms")
public class InfoLinkController {

	private static final Logger log = LoggerFactory.getLogger(InfoLinkController.class);

	@Inject
	InfoLinkService infoLinkService;

	@Inject
	UploadFileService uploadFileService;

	@Inject
	LogService logService;

	@RequestMapping(value = "infolink_list")
	public String findInfoLinkByInfoSortId(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		try {
			String infosortid = request.getParameter("infosortid");
			String infosortname = request.getParameter("infosortname");
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

			SysUser user = (SysUser) request.getSession().getAttribute("user");

			page = infoLinkService.findInfoLinkByInfoSortId(page, infosortid, search, user.getId());
			model.addAttribute("list", page.getDatalist());
			model.addAttribute("blanklist", page.getBlanklist());
			model.addAttribute("search", search);
			model.addAttribute("infosortid", infosortid);
			model.addAttribute("infosortname", RTools.string.isEmpty(infosortname) ? "无" : infosortname);
			model.addAttribute("page", page);
			logService.success(begin, page, request);
		} catch (Exception e) {
			// TODO: handle exceptions
			logService.fail(begin, e.getMessage(), request);
		}
		return "platform/cms/infolink/infolink_list";

	}

	@RequestMapping(value = "infolink_add")
	public String infoLinkAdd(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		try {
			String infosortid = request.getParameter("infosortid");
			CmsInfoLink info = new CmsInfoLink();
			info.setInfolinkid(RTools.encoding.getUUID32());
			info.setDeleteflog(1);

			// info.setInfolinktitle("测试信息");
			// info.setContent("fdsafdsafsd");
			// info.setInfolinkorigin("开发测试");
			 info.setInfolinktype("0");
			// info.setKeyword("测试");

			model.addAttribute("infosortid", infosortid);
			model.addAttribute("info", info);

			logService.success(begin, "新增信息", request);
		} catch (Exception e) {
			// TODO: handle exception
			logService.fail(begin, e.getMessage(), request);
			e.printStackTrace();
		}
		return "platform/cms/infolink/infolink_add";

	}

	@RequestMapping(value = "infolink_save")
	public String infoLinkSave(Model model, HttpServletRequest request, CmsInfoLink infoLink) {
		String infoSortId = request.getParameter("infosortid");
		LogBean begin = logService.begin(request.getParameterMap());
		try {

			if (RTools.string.isEmpty(infoLink.getUserid())) {
				SysUser user = (SysUser) request.getSession().getAttribute("user");
				infoLink.setCreatedtime(RTools.dateTimeUtil.getNowTime(1));
				infoLink.setUpdatetime(RTools.dateTimeUtil.getNowTime(1));
				infoLink.setUserid(user.getId());
				infoLink.setDeptid(user.getDeptid());
			} else {
				infoLink.setUpdatetime(RTools.dateTimeUtil.getNowTime(1));
			}
			infoLinkService.infoLinkSave(infoLink, infoSortId);
			model.addAttribute("diyMsg", "保存成功！");
			logService.success(begin, "保存成功！", request);
		} catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("diyMsg", "保存失败！");
			model.addAttribute("exceptionMessage", e.getMessage());
			logService.fail(begin, e.getMessage(), request);
			e.printStackTrace();
		} finally {
			return "platform/success/success";
		}
	}

	@RequestMapping(value = "infolink_publish")
	@ResponseBody
	public String infoLinkPublish(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		String[] ids = request.getParameterValues("checked");
		try {

			infoLinkService.infoLinkPublish(ids);
			map.put("state", "0");
			map.put("msg", "发布成功！");
			logService.success(begin, map, request);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("state", "1");
			map.put("msg", "删除失败");
			logService.fail(begin, e.getMessage(), request);
			e.printStackTrace();
		} finally {
			return RTools.json.toJson(map);
		}
	}

	@RequestMapping(value = "infolink_del")
	@ResponseBody
	public String infoLinkDel(Model model, HttpServletRequest request) {
		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		String[] ids = request.getParameterValues("checked");
		try {

			infoLinkService.delInfoLink(ids);
			map.put("state", "0");
			map.put("msg", "删除成功！");
			logService.success(begin, map, request);
		} catch (Exception e) {
			// TODO: handle exception
			map.put("state", "1");
			map.put("msg", "删除失败");
			logService.fail(begin, e.getMessage(), request);
			e.printStackTrace();
		} finally {
			return RTools.json.toJson(map);
		}
	}

	@RequestMapping(value = "infolink_edit")
	public String infoLinkEdit(Model model, HttpServletRequest request, @RequestParam("infolinkid") String infolinkid) {
		LogBean begin = logService.begin(request.getParameterMap());
		try {
			CmsInfoLink info = infoLinkService.getInfoLink(infolinkid);
			List fileList = uploadFileService.fileList(infolinkid);
			model.addAttribute("info", info);
			model.addAttribute("flist", fileList);
			logService.success(begin, "信息编辑", request);
		} catch (Exception e) {
			// TODO: handle exception
			logService.fail(begin, e.getMessage(), request);
			e.printStackTrace();
		}
		return "platform/cms/infolink/infolink_add";

	}

	@RequestMapping(value = "infolink_view")
	public String infoLinkView(Model model, HttpServletRequest request, @RequestParam("infolinkid") String infolinkid) {
		LogBean begin = logService.begin(request.getParameterMap());
		try {
			CmsInfoLink info = infoLinkService.getInfoLink(infolinkid);
			List fileList = uploadFileService.fileList(infolinkid);
			model.addAttribute("info", info);
			model.addAttribute("flist", fileList);
			logService.success(begin, "信息查看", request);
		} catch (Exception e) {
			// TODO: handle exception
			logService.fail(begin, e.getMessage(), request);
			e.printStackTrace();
		}
		return "platform/cms/infolink/infolink_view";

	}

}
