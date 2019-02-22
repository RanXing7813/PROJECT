package cn.com.taiji.platform.web.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.taiji.gxwz.web.BaseAction;
import cn.com.taiji.platform.entity.ModelData;
import cn.com.taiji.platform.entity.ModelSysExport;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.platform.service.ModelSysService;
import cn.com.taiji.util.page.Pagination;

/** 
* 模板管理 - 模板字段设置
* @ClassName:  ModelSysController
* @author ranxing
* @date 2019年1月22日 上午9:35:43
*/
@Controller
@RequestMapping(value = "/platform/sys")
public class ModelSysController  extends BaseAction {
	
	private static final Logger log = LoggerFactory.getLogger(ModelSysController.class);
	
	@Inject
	ModelSysService modelSysService;
	
	/**
	 * 弹出页面   模板信息类列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "column_list", method = {RequestMethod.GET })
	public String toListPage(Model model, String model_id, String cread_id, HttpServletRequest request){
		model.addAttribute("model_id", model_id);
		model.addAttribute("cread_id", cread_id);
		model.addAttribute("publish_status", request.getParameter("publish_status"));
		return "/platform/model/model_sys_list";
	}
	
	/**
	 * 模板信息类列表数据
	 * @param model
	 * @param models
	 */
	@RequestMapping(value = "column_list_json", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody  
	public Object getListJson(HttpServletRequest request, ModelSysExport dto, Pagination<ModelSysExport> pag,
						@RequestParam(value = "models", required = false) String models){
		    //* 信息类编目表名
		    Map<String, Object> searchParameters = new HashMap<String, Object>();
		    searchParameters = init(models);
			searchParameters.put("field", request.getParameter("field"));
			searchParameters.put("order", request.getParameter("order"));
			int currentPageStr = Integer.parseInt(request.getParameter("pagecurrentnum"));
			int pagesize = Integer.parseInt(request.getParameter("selectlimitnum"));
			Pagination<ModelSysExport> page = new Pagination<ModelSysExport>(currentPageStr, pagesize);
			
			try {
				page = modelSysService.getListJson(searchParameters, dto, page);
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
	 * 弹出页面   模板原始字段列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "column_old_list", method = {RequestMethod.GET })
	public String toColumnListPage(Model model, String model_id){
		model.addAttribute("model_id", model_id);
		
		List<Map<String,Object>> list = modelSysService.getOldColumnList(model_id);
		model.addAttribute("list", list);
		return "/platform/model/model_old_column_list3";
	}
	
	
	/**
	 * 弹出页面   模板字段 保存
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="column_save", method = { RequestMethod.POST })
	public Object  pushSave(Model model, String  model_id , String [] ids, String [] names, String [] types, HttpServletRequest request){
	    	try {
				  SysUser user = (SysUser) request.getSession().getAttribute("user");

				  modelSysService.saveOne(model_id,ids,names,types,user);
				  Map<String, Object> searchParameters = new HashMap<String, Object>();
				  searchParameters.put("code", "Y");
				  return searchParameters;
			} catch (Exception e) {
				e.printStackTrace();
				return "N";
			}
	}
	
	
	/**
	 * 弹出页面   模板原始字段列表
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "column_old_list_json", method = {RequestMethod.GET })
	public Object toColumnListJson(Model model, String model_id){
		List<Map<String,Object>> list = modelSysService.getOldColumnList(model_id);
		
	    Map<String, Object> searchParameters = new HashMap<String, Object>();
		searchParameters.put("code", 0);
		searchParameters.put("msg", 0);
		searchParameters.put("data", list);
		searchParameters.put("count", list.size());
		return searchParameters;  
	}
	
	/**
	 * 弹出页面   模板信息类  新增/编辑
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "data_edit", method = {RequestMethod.GET })
	public String toEditPage(Model model, ModelSysExport dto){
		try {
			if(dto.getCol_id()!=null && !dto.getCol_id().isEmpty()){
				model.addAttribute("dto", modelSysService.findSomeOne(dto));
			}else{
				dto.setIndex_(1);
				model.addAttribute("dto", dto);
			}
			//非自增的主键列表
			model.addAttribute("pri", modelSysService.priList(dto));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/platform/model/model_sys_edit";
	}
	
	/**
	 * 弹出页面   模板信息类  保存
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="data_edit_save", method = { RequestMethod.POST })
	public String  saveData (Model model,  ModelSysExport dto, HttpServletRequest request){
			SysUser user = (SysUser) request.getSession().getAttribute("user");
	    	try {
	    		modelSysService.saveSomeOne(dto, user);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	return "/platform/success/success2";
	}
	
	/**
	 * 弹出页面   模板  删除
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="data_del", method = { RequestMethod.POST })
	public Object  delDatas(Model model,  ModelData dto, String [] ids, HttpServletRequest request){
	    	try {
	    		modelSysService.delSomeData(ids);
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
	@RequestMapping(value = "data_check{type}", method = { RequestMethod.POST })
	public Object  checkTableName (Model model, @PathVariable("type")String type, HttpServletRequest request,
			@RequestParam(value="model_id")String model_id,
			@RequestParam(value="col_id")String col_id,
			@RequestParam(value="col_name", required=true)String col_name ){
		try {
			  String msg = modelSysService.check(type, model_id, col_name, col_id);//
			  Map<String, Object> searchParameters = new HashMap<String, Object>();
			  if(msg!=null && !msg.isEmpty()){
				  searchParameters.put("code", "N" );
				  searchParameters.put("msg", msg );
			  }else{
				  searchParameters.put("code", "Y" );
				  searchParameters.put("msg", msg );
			  }
			return searchParameters;
		} catch (Exception e) {
			e.printStackTrace();
			return "N";
		}
	}
	

}
