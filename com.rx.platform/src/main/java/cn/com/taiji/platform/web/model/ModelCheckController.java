package cn.com.taiji.platform.web.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import cn.com.taiji.platform.entity.ModelImput;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.platform.entity.UploadFile;
import cn.com.taiji.platform.service.ModelCheckService;
import cn.com.taiji.util.page.Pagination;

/** 
* @ClassName: ModelSystemController 
* @Description: 模板系统  模板管理  模板录入校验
* @author ranxing 
* @date 2017年8月2日 下午3:06:23 
*  
*/
@Controller
@RequestMapping(value = "/platform/modcheck")
public class ModelCheckController extends BaseAction {
	
	private static final Logger log = LoggerFactory.getLogger(ModelCheckController.class);
	
	@Inject 
	ModelCheckService modelCheckService;
	
	/**
	 * 弹出页面   模板录入校验列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "get_list", method = {RequestMethod.GET })
	public String getListPage(Model model){
		return "/platform/model/model_info_list";
	}
	
	/**
	 * 模板录入校验列表数据
	 * @param model
	 * @param models
	 */
	@RequestMapping(value = "get_list_json", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody  
	public Object getListJson(HttpServletRequest request, ModelSysTableDto dto, Pagination<ModelSysTableDto> pag,
						@RequestParam(value = "models", required = false) String models){
		    //* 信息类编目表名
		    Map<String, Object> searchParameters = new HashMap<String, Object>();
		    searchParameters = init(models);
			searchParameters.put("field", request.getParameter("field"));
			searchParameters.put("order", request.getParameter("order"));
			int page = Integer.parseInt(request.getParameter("pagecurrentnum"))-1;
			int pageSize = Integer.parseInt(request.getParameter("selectlimitnum"));
			
			try {
				 //缓存获取list
				List list = (List) request.getSession().getAttribute("dataList"+request.getParameter("id"));
				if(list!=null){
					if (pageSize < 1)   pageSize = 10;
					if (pageSize > 500)  pageSize = 500;
					
				    int x = list.size();
				    
				    int b = page*pageSize ;
				      int bg = b > x ? x : b ;
				      
				    int e = (page+1)*pageSize   ;
				      int ed = e > x ? x : e ;
				      
				     list = list.subList(bg, ed);//开始的角标 ,  数组长度
				     searchParameters.put("totle", x);
				}
				
				searchParameters.put("code", 0);
				searchParameters.put("msg", 0);
				searchParameters.put("list", list);
				
			} catch (Exception e) {
				searchParameters.put("code", -1);
				searchParameters.put("msg", "数据错误");
				e.printStackTrace();
			}
	  		return searchParameters;  
	}
	
	/**
	 * 弹出页面   模板录入校验   
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "check_page", method = {RequestMethod.GET })
	public String modelCheck(Model model,HttpServletRequest request, ModelImput dto){
		
		if(dto.getImput_id()!=null && !dto.getImput_id().isEmpty()){
			dto =  (ModelImput) modelCheckService.findOne(dto);
			UploadFile uploadDto =  modelCheckService.findUploadOne(dto);
			SysUser user = (SysUser) request.getSession().getAttribute("user");

			String filePath = uploadDto.getFilesavepath()+uploadDto.getFilesavename()+"."+uploadDto.getFiletype() ;
			//此时的Workbook应该是从 客户端浏览器上传过来的 uploadFile了,其实跟读取本地磁盘的一个样  
			File file = new File(filePath);
	    	if(!file.exists()){
	    		model.addAttribute("message","文件不存在");
	    	}
	    	
			FileInputStream fis;
			Sheet sheet ;
			try {
				fis = new FileInputStream(filePath);
				boolean is03Excell = filePath.matches("^.+\\.(?i)(xls)$")?true:false;
				Workbook wb = is03Excell ? new HSSFWorkbook(fis):new XSSFWorkbook(fis);
				sheet = wb.getSheetAt(0);
				//数据校验
				Map   map = (Map) modelCheckService.checkExcel(sheet,dto.getImput_id(),user);
				//存入session 给分页用
				request.getSession().setAttribute("dataList"+dto.getImput_id(), map.get("alldataList"));
				model.addAttribute("map", map);
				model.addAttribute("html_", map.get("html_"));
				model.addAttribute("id", dto.getImput_id());
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			
		}
		return "/platform/model/model_imput_data_list";
	}
	/**
	 * 弹出页面   模板录入数据   
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "push_page", method = {RequestMethod.GET })
	public String modelPush(Model model,HttpServletRequest request, ModelImput dto){
		
		if(dto.getImput_id()!=null && !dto.getImput_id().isEmpty()){
			dto =  (ModelImput) modelCheckService.findOne(dto);
			UploadFile uploadDto =  modelCheckService.findUploadOne(dto);
			SysUser user = (SysUser) request.getSession().getAttribute("user");

			String filePath = uploadDto.getFilesavepath()+uploadDto.getFilesavename()+"."+uploadDto.getFiletype() ;
			//此时的Workbook应该是从 客户端浏览器上传过来的 uploadFile了,其实跟读取本地磁盘的一个样  
			File file = new File(filePath);
	    	if(!file.exists()){
	    		model.addAttribute("message","文件不存在");
	    	}
	    	
			FileInputStream fis;
			Sheet sheet ;
			try {
				fis = new FileInputStream(filePath);
				boolean is03Excell = filePath.matches("^.+\\.(?i)(xls)$")?true:false;
				Workbook wb = is03Excell ? new HSSFWorkbook(fis):new XSSFWorkbook(fis);
				sheet = wb.getSheetAt(0);
				//数据校验
				Map<String,Object> map;
				try {
					map = (Map<String, Object>) modelCheckService.pushExcel(sheet,dto.getImput_id(),user);
					model.addAttribute("error", map.get("error"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			
		}
		return "/platform/success/success2";
	}

}
