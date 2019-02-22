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
import cn.com.taiji.platform.dto.ModelDataDto;
import cn.com.taiji.platform.entity.ModelData;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.platform.service.ModelService;
import cn.com.taiji.platform.service.impl.ModelImpl;
import cn.com.taiji.util.page.Pagination;

/** 
* 模板管理 - 模板列表
* @ClassName:  ModelController
* @author ranxing
* @date 2019年1月22日 上午9:35:43
*/
@Controller
@RequestMapping(value = "/platform/model")
public class ModelController  extends BaseAction {
	
	private static final Logger log = LoggerFactory.getLogger(ModelController.class);
	
	@Inject
	ModelService modelService;
	
	/**
	 * 弹出页面   模板信息类列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "data_list", method = {RequestMethod.GET })
	public String toListPage(Model model){
		return "/platform/model/model_data_list";
	}
	
	/**
	 * 弹出页面   模板信息 下载  类列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "data_download_list", method = {RequestMethod.GET })
	public String toListPage2(Model model){
		return "/platform/model/model_data_download_list";
	}
	
	/**
	 * 模板信息类列表数据
	 * @param model
	 * @param models
	 */
	@RequestMapping(value = "data_list_json", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody  
	public Object getListJson(HttpServletRequest request, ModelDataDto dto, Pagination<ModelSysTableDto> pag,
						@RequestParam(value = "models", required = false) String models){
		    //* 信息类编目表名
			SysUser user = (SysUser) request.getSession().getAttribute("user");
		
		    Map<String, Object> searchParameters = new HashMap<String, Object>();
		    searchParameters = init(models);
			searchParameters.put("field", request.getParameter("field"));
			searchParameters.put("order", request.getParameter("order"));
			searchParameters.put("isMyDatas", request.getParameter("isMyDatas"));
			searchParameters.put("cread_id", user.getId());
			searchParameters.put("dept_id", user.getDeptid());
			int currentPageStr = Integer.parseInt(request.getParameter("pagecurrentnum"));
			int pagesize = Integer.parseInt(request.getParameter("selectlimitnum"));
			Pagination<ModelDataDto> page = new Pagination<ModelDataDto>(currentPageStr, pagesize);
			
			try {
				page = modelService.getListJson(searchParameters, dto, page);
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
	@RequestMapping(value = "data_edit", method = {RequestMethod.GET })
	public String toEditPage(Model model, ModelDataDto dto){
		try {
			if(dto.getModel_id()!=null && !dto.getModel_id().isEmpty()){
				model.addAttribute("dto", modelService.findOne(dto));
			}else{
				model.addAttribute("dto", dto);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/platform/model/model_data_edit";
	}
	
	/**
	 * 弹出页面   模板信息类  保存
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="data_edit_save", method = { RequestMethod.POST })
	public String  saveData (Model model,  ModelData dto, HttpServletRequest request){
			SysUser user = (SysUser) request.getSession().getAttribute("user");
	    	try {
	    		modelService.saveData(dto, user);
				
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
	    		  modelService.delDatas(ids);
				  Map<String, Object> searchParameters = new HashMap<String, Object>();
				  searchParameters.put("code", "Y");
				  return searchParameters;
			} catch (Exception e) {
				e.printStackTrace();
				return "N";
			}
	}
	
	/**
	 * 弹出页面   模板  撤销
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="data_back", method = { RequestMethod.POST })
	public Object  backDatas(Model model,  ModelData dto, String [] ids, HttpServletRequest request){
	    	try {
				SysUser user = (SysUser) request.getSession().getAttribute("user");
	    			modelService.backDatas(ids, user);
				  Map<String, Object> searchParameters = new HashMap<String, Object>();
				  searchParameters.put("code", "Y");
				  return searchParameters;
			} catch (Exception e) {
				e.printStackTrace();
				return "N";
			}
	}
	
	
	/**
	 * 弹出页面   模板发布部门页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "data_push", method = {RequestMethod.GET })
	public String toPushPage(Model model, String  model_id){
		model.addAttribute("deptList", modelService.pushDatas(model_id));
		return "/platform/model/model_push_select";
	}
	
	/**
	 * 弹出页面   模板  撤销
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="data_push_save", method = { RequestMethod.POST })
	public Object  pushSave(Model model, String [] model_id , String [] ids, String [] names, HttpServletRequest request){
	    	try {
				SysUser user = (SysUser) request.getSession().getAttribute("user");

	    		  modelService.pushSave(model_id,ids,names,user);
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
	@RequestMapping(value = "data_check", method = { RequestMethod.POST })
	public Object  checkTableName (Model model,  @RequestParam(value="table_name", required=true)String table_name,@RequestParam(value="id", required=true)String id, HttpServletRequest request ){
		try {
			int x = modelService.checkOne(table_name, id);
			
			  Map<String, Object> searchParameters = new HashMap<String, Object>();
			  searchParameters.put("code", x>0?"Y":"N");
			  log.debug("x:"+ (x>0?"Y":"N"));
			return searchParameters;
		} catch (Exception e) {
			e.printStackTrace();
			return "N";
		}
	}
	
	

}
