package cn.com.taiji.platform.web.model;

import java.io.File;
import java.io.FileInputStream;
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
import org.springframework.web.multipart.MultipartFile;

import cn.com.taiji.gxwz.dto.ModelSysTableDto;
import cn.com.taiji.gxwz.web.BaseAction;
import cn.com.taiji.platform.entity.ModelImput;
import cn.com.taiji.platform.entity.ModelSysTable;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.platform.entity.UploadFile;
import cn.com.taiji.platform.service.LogService;
import cn.com.taiji.platform.service.ModelImputService;
import cn.com.taiji.platform.service.UploadFileService;
import cn.com.taiji.platform.service.impl.ModelImputImpl;
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.RTools;
import cn.com.taiji.util.tools.lang.StringTool;
import cn.com.taiji.util.tools.log.LogBean;

/** 
* 模板管理 - 数据录入
* @ClassName:  ModelImputController
* @author ranxing
* @date 2019年1月18日 上午10:38:22
*/
@Controller
@RequestMapping(value = "/platform/modImp")
public class ModelImputController extends BaseAction {
	private static final Logger log = LoggerFactory.getLogger(ModelImputController.class);
	
	@Inject
	ModelImputService modelImputService;
	@Inject
	UploadFileService uploadFileService;
	@Inject
	LogService logService;
	/**
	 * 模板数据 -导入列表
	 * @param model
	 * @return methods
	 */
	@RequestMapping(value="imput_list")
	public String getList(Model model){
		
		return "/platform/model/model_imput_list";
	}

	/**
	 *  模板数据 -导入列表数据
	 * @param model
	 * @param models
	 */
	@RequestMapping(value = "imput_list_json", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody  
	public Object getList(HttpServletRequest request, ModelImput dto, Pagination<ModelImput> pag,
						@RequestParam(value = "models", required = false) String models){
		
			SysUser user = (SysUser) request.getSession().getAttribute("user");

		    //* 信息类编目表名
		    Map<String, Object> searchParameters = new HashMap<String, Object>();
		    searchParameters = init(models);
			searchParameters.put("field", request.getParameter("field"));
			searchParameters.put("order", request.getParameter("order"));
			searchParameters.put("userId", user.getId());
			int currentPageStr = Integer.parseInt(request.getParameter("pagecurrentnum"));
			int pagesize = Integer.parseInt(request.getParameter("selectlimitnum"));
			Pagination<ModelImput> page = new Pagination<ModelImput>(currentPageStr, pagesize);
			
			try {
				page = modelImputService.getList(searchParameters, dto, page);
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
	 * 删除  导入数据 
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value ="imput_del", method = { RequestMethod.POST })
	public Object  modelInfoDel(Model model,  ModelImput dto, HttpServletRequest request){
	    	try {
	    		  modelImputService.delOne(dto);
				
				  Map<String, Object> searchParameters = new HashMap<String, Object>();
				  searchParameters.put("code", "Y");
				  return searchParameters;
			} catch (Exception e) {
				e.printStackTrace();
				return "N";
			}
	}
	
	
	
	/**
	 * 上传  上传文件页面
	 * @param model
	 * @param request
	 * @return methods
	 */
	@RequestMapping(value = "attachment", method = RequestMethod.GET)
	public String attachment(Model model, HttpServletRequest request) {
		return "platform/model/attachment";
	}
	/**
	 * 上传 上传文件
	 * @param model
	 * @param request
	 * @param file
	 * @param fkid
	 * @return methods
	 */
	@RequestMapping(value = "mult_upload")
	@ResponseBody
	public String uploadFiles(Model model, HttpServletRequest request, @RequestParam("file") MultipartFile file,
			@RequestParam("fkid") String fkid) {

		LogBean begin = logService.begin(request.getParameterMap());
		Map map = new HashMap<String, Object>();
		try {

			// 最大文件限制
			Long maxUploadSize = Long.valueOf(RTools.properties.getValue("maxUploadSize"));

			boolean isUpload = true;

			// 如果文件不为空，写入上传路径
			if (!file.isEmpty()) {

				// 上传文件路径
				String path = RTools.properties.getValue("file_save_path");
				// System.out.println("文件上传路径：" + path);
				// 上传文件名
				String filename = file.getOriginalFilename();
				// System.out.println("上传文件名：" + filename);
				// 上传文件后缀
				String suffix = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
				// System.out.println("上传文件后缀：" + suffix);

				if (!checkFile(filename)) {
					map.put("code", "1");
					map.put("msg", "上传失败！");
					map.put("info", "文件类型不在可允许上传的文件类型之内！");
					isUpload = false;
				}

				// 上传文件大小
				Long fileSize = file.getSize();
				// System.out.println("上传文件大小：" + fileSize);
				if (isUpload) {
					if (fileSize <= maxUploadSize) {

						// 文件保存名称 用32为UUID 避免文件覆盖 同时文件的ID也使用该值
						String filesavename = RTools.encoding.getUUID32();
						// 文件保存文件夹路径
						String filesavepath = path + File.separator + RTools.dateTimeUtil.getNowTime(3)
								+ File.separator;
						// 文件路径
						String targetpath = filesavepath + filesavename + "." + suffix;

						File filepath = new File(targetpath);
						// 判断路径是否存在，如果不存在就创建一个
						if (!filepath.getParentFile().exists()) {
							filepath.getParentFile().mkdirs();
						}
						
						
						// 将上传文件保存到目标文件当中
						file.transferTo(new File(targetpath));
						File file2 = new File(targetpath);
				    	if(!file2.exists()){
				    		map.put("code", "1");
							map.put("msg", "上传失败！");
							map.put("info", "文件不能为空！");
				    	}else{
				    		FileInputStream fis = new FileInputStream(targetpath);
							boolean is03Excell = filename.matches("^.+\\.(?i)(xls)$")?true:false;
							Workbook wb = is03Excell ? new HSSFWorkbook(fis):new XSSFWorkbook(fis);
							Sheet sheet = wb.getSheetAt(0);
							
							if(fkid ==null || fkid.isEmpty())
								fkid = StringTool.getUUID();
							
							SysUser user = (SysUser) request.getSession().getAttribute("user");
							
							// 入库保存附件信息
							UploadFile uf = new UploadFile();
							uf.setFileid(filesavename);
							uf.setCreattime(RTools.dateTimeUtil.getNowTime(1));
							uf.setDeleteflog("1");
							uf.setFilesize(fileSize);
							uf.setFiletype(suffix);
							uf.setFilename(filename);
							uf.setFilesavepath(filesavepath);
							uf.setFilesavename(filesavename);
							uf.setFkid(fkid);
							
							String msg =	modelImputService.insertDatas(sheet,fkid,user,uf );
							if(msg!=null && !msg.isEmpty()){
								map.put("msg",msg );
								map.put("code", "1");
								uf.setFkid("");
								file2.delete();
							}else{
								map.put("code", "0");
								map.put("msg", "上传成功！");
								
							}
							uploadFileService.saveUploadFile(uf);

							
							map.put("fid", uf.getFileid());
							map.put("fname", filename);
							map.put("info", msg);
							
				    	}
						
					} else {
						map.put("code", "1");
						map.put("msg", "上传失败！");
						map.put("info", "文件超过所允许上传的文件大小！");
					}

				}
			} else {
				map.put("code", "1");
				map.put("msg", "上传失败！");
				map.put("info", "文件不能为空！");
			}
			logService.success(begin, map, request);
		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
			map.put("code", "1");
			map.put("msg", "上传失败！");
			map.put("info", e.getMessage());
			logService.fail(begin, map, request);
		} finally {
			return RTools.json.toJson(map);
		}

	}
	
	
	
	/**
	 * 判断是否为允许的上传文件类型,true表示允许
	 */
	private boolean checkFile(String fileName) {
		// 设置允许上传文件类型
		String suffixList = RTools.properties.getValue("modelImp");
		if ("*".equals(suffixList)) {
			return true;
		}
		// 获取文件后缀
		String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		if (suffixList.contains(suffix.trim().toLowerCase())) {
			return true;
		}
		return false;
	}
}
