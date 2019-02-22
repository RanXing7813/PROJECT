package cn.com.taiji.platform.service;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import cn.com.taiji.platform.entity.ModelImput;
import cn.com.taiji.platform.entity.SysUser;


/** 
* 模板导入 - 检查
* @ClassName:  ModelCheckImpl
* @author ranxing
* @date 2019年1月19日 上午11:22:41
*/
public interface ModelCheckService {

	
	public Object checkExcel(Sheet sheet, String id, SysUser user);
	

	/**
	 * @param dto
	 * @return methods
	 */
	public Object findOne(ModelImput dto);

	/**
	 * @param dto
	 * @return methods
	 */
	public cn.com.taiji.platform.entity.UploadFile findUploadOne(ModelImput dto);
	
	
	
	public Object pushExcel(Sheet sheet, String id, SysUser user) throws Exception;
	
	
}
