package cn.com.taiji.generator.web.model;

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

import cn.com.taiji.generator.entity.ModelSysExport2;
import cn.com.taiji.generator.service.ModelSysService2;
import cn.com.taiji.gxwz.web.BaseAction;
import cn.com.taiji.platform.entity.ModelData;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.util.page.PaginationUtil;

/** 
* 模板管理 - 模板字段设置
* @ClassName:  ModelSysController
* @author ranxing
* @date 2019年1月22日 上午9:35:43
*/
@Controller
@RequestMapping(value = "/ModelSysController2")
public class ModelSysController2  extends BaseAction {
	
	private static final Logger log = LoggerFactory.getLogger(ModelSysController2.class);
	
	@Inject
	ModelSysService2 modelSysService2;
	
	/**
	 * 加载页面  列表
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toListPage", method = {RequestMethod.GET })
	public String toListPage(Model model, String model_id, String cread_id, HttpServletRequest request){
		return "/platform/model/listPage";
	}
	
	/**
	 * 加载数据  列表
	 * @param model
	 * @param models
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "getListJson", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody  
	public Object getListJson(HttpServletRequest request, ModelSysExport2 dto,
			PaginationUtil<ModelSysExport2> page,
						@RequestParam(value = "models", required = false) String models){
		    //* 信息类编目表名
		    Map<String, Object> searchParameters = new HashMap<String, Object>();
		    searchParameters = init(models);
			try {
				searchParameters = modelSysService2.getListJson(searchParameters, dto, page);
//				searchParameters.put("code", 0);
//				searchParameters.put("msg", "操作正常!");
//				searchParameters.put("list", page.getDataList());
//				searchParameters.put("totle", page.getDataCount());
			} catch (Exception e) {
				searchParameters.put("code", page.RETURN_ERROR_CODE);
				searchParameters.put("msg",	 page.RETURN_ERROR_MSG);
				e.printStackTrace();
			}
	  		return searchParameters;  
	}
	
	/**
	 * 弹出页面   模板信息类  新增
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toAddPage", method = {RequestMethod.GET })
	public String toAddPage(Model model, ModelSysExport2 dto){
				model.addAttribute("dto", dto);
		return "/platform/model/model_sys_edit";
	}
	
	/**
	 * 弹出页面   模板信息类  编辑
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "toEditPage{id}", method = {RequestMethod.GET })
	public String toEditPage(Model model, @PathVariable("id")String id, ModelSysExport2 dto){
				model.addAttribute("dto", modelSysService2.findSomeOne(dto));
		return "/platform/model/model_sys_edit";
	}
	
	/**
	 * 弹出页面   模板信息类  保存
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="saveData", method = { RequestMethod.POST })
	public String  saveData (Model model,  ModelSysExport2 dto, HttpServletRequest request){
			SysUser user = (SysUser) request.getSession().getAttribute("user");
	    	try {
	    		modelSysService2.saveSomeOne(dto, user);
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
	@RequestMapping(value ="delData", method = { RequestMethod.POST })
	public Object  delDatas(Model model,  ModelData dto, String [] ids, HttpServletRequest request){
	    	try {
	    		modelSysService2.delSomeData(ids);
				  Map<String, Object> searchParameters = new HashMap<String, Object>();
				  searchParameters.put("code", "Y");
				  return searchParameters;
			} catch (Exception e) {
				e.printStackTrace();
				return "N";
			}
	}
	
	@ResponseBody
	@RequestMapping(value = "data_check{type}", method = { RequestMethod.POST })
	public Object  checkTableName (Model model, @PathVariable("type")String type, HttpServletRequest request,
			@RequestParam(value="model_id")String model_id,
			@RequestParam(value="col_id")String col_id,
			@RequestParam(value="col_name", required=true)String col_name ){
		try {
			  String msg = modelSysService2.check(type, model_id, col_name, col_id);
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
