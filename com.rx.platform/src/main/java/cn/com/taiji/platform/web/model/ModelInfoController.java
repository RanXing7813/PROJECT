package cn.com.taiji.platform.web.model;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.taiji.gxwz.dto.ModelSysTableDto;
import cn.com.taiji.gxwz.web.BaseAction;
import cn.com.taiji.platform.entity.ModelSysTable;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.platform.service.ModelInfoService;
import cn.com.taiji.util.page.Pagination;

/** 
* @ClassName: ModelInfoController 
* @Description: 模板系统  模板管理  模板信息类
* @author ranxing 
* @date 2017年8月2日 下午3:06:23 
*  
*/
@Controller
@RequestMapping(value = "/platform/modInfo")
public class ModelInfoController extends BaseAction {
	
	private static final Logger log = LoggerFactory.getLogger(ModelInfoController.class);
	
	@Inject 
	ModelInfoService modelInfoService;
	
	/**
	 * 弹出页面   模板信息类列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "info_tableName_list", method = {RequestMethod.GET })
	public String modelInfoList(Model model){
		return "/platform/model/model_info_list";
	}
	@RequestMapping(value = "info_tableName_checklist", method = {RequestMethod.GET })
	public String modelInfoCheckList(Model model){
		return "/platform/model/model_info_checklist";
	}
	
	/**
	 * 模板信息类列表数据
	 * @param model
	 * @param models
	 */
	@RequestMapping(value = "info_tableName_list_json", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody  
	public Object modelInfoListJson(HttpServletRequest request, ModelSysTableDto dto, Pagination<ModelSysTableDto> pag,
						@RequestParam(value = "models", required = false) String models){
		    //* 信息类编目表名
		    Map<String, Object> searchParameters = new HashMap<String, Object>();
		    searchParameters = init(models);
			searchParameters.put("field", request.getParameter("field"));
			searchParameters.put("order", request.getParameter("order"));
			int currentPageStr = Integer.parseInt(request.getParameter("pagecurrentnum"));
			int pagesize = Integer.parseInt(request.getParameter("selectlimitnum"));
			Pagination<ModelSysTableDto> page = new Pagination<ModelSysTableDto>(currentPageStr, pagesize);
			
			try {
				page = modelInfoService.getModelTableList(searchParameters, dto, page);
				searchParameters.put("code", 0);
				searchParameters.put("msg", 0);
				searchParameters.put("list", page.getDatalist());
				searchParameters.put("totle", page.getDatacount());
			} catch (Exception e) {
				searchParameters.put("code", -1);
				searchParameters.put("msg", "数据错误");
				searchParameters.put("list", page.getDatalist());
				searchParameters.put("totle", page.getDatacount());
				e.printStackTrace();
			}
	  		return searchParameters;  
	}
	
	/**
	 * 弹出页面   模板信息类  新增/编辑
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "info_tableName_edit", method = {RequestMethod.GET })
	public String modelInfo(Model model, ModelSysTable dto){
		
		if(dto.getId_table_name()!=null && !dto.getId_table_name().isEmpty()){
			model.addAttribute("dto", modelInfoService.findModelInfo(dto));
		}else{
			model.addAttribute("dto", dto);
		}
		return "/platform/model/model_info_edit";
	}
	
	/**
	 * 弹出页面   模板信息类  保存
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="info_tableName_edit_save", method = { RequestMethod.POST })
	public String  modelInfoSave(Model model,  ModelSysTable dto, HttpServletRequest request){
			SysUser user = (SysUser) request.getSession().getAttribute("user");
		
	    	try {
				modelInfoService.saveModelInfo(dto, user);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return "/platform/success/success2";
	}
	
	/**
	 * 弹出页面   模板信息类  物理删除
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="info_tableName_edit_del", method = { RequestMethod.POST })
	public Object  modelInfoDel(Model model,  ModelSysTable dto, HttpServletRequest request){
	    	try {
				   modelInfoService.delModelInfo(dto);
				
				  Map<String, Object> searchParameters = new HashMap<String, Object>();
				  searchParameters.put("code", "Y");
				  return searchParameters;
			} catch (Exception e) {
				e.printStackTrace();
				return "N";
			}
	}
	
	/**
	 * 信息类  检查表名唯一
	 * @param model
	 * @param table_name
	 * @param id
	 * @param request
	 * @return methods
	 */
	@ResponseBody
	@RequestMapping(value = "info_tableName_edit_checkTableName", method = { RequestMethod.POST })
	public Object  checkTableName (Model model,  @RequestParam(value="table_name", required=true)String table_name,@RequestParam(value="id", required=true)String id, HttpServletRequest request ){
		try {
			String msg = modelInfoService.check(table_name, id);
			
			  Map<String, Object> searchParameters = new HashMap<String, Object>();
			  searchParameters.put("code", msg );
			return searchParameters;
		} catch (Exception e) {
			e.printStackTrace();
			return "N";
		}
	}
	

}
