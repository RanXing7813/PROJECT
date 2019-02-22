package cn.com.taiji.platform.service;

import java.util.Map;


import cn.com.taiji.gxwz.dto.ModelSysTableDto;
import cn.com.taiji.platform.entity.ModelSysTable;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.util.page.Pagination;

/** 
* 
* @ClassName:  ModelInfoService
* @author ranxing
* @date 2019年1月16日 上午11:15:26
*/
public interface ModelInfoService {

	
	/**
	 * 获取列表
	 * @param searchParameters
	 * @param dto
	 * @param pag
	 * @return
	 * @throws Exception methods
	 */
	public Pagination<ModelSysTableDto> getModelTableList  (  Map<String, Object> searchParameters , ModelSysTableDto dto , Pagination<ModelSysTableDto> pag )throws Exception;  

	/**
	 * 查找信息类
	 * @param dto
	 * @return methods
	 */
	public Object findModelInfo(ModelSysTable dto);

	/**
	 * 保存信息类
	 * @param dto
	 * @param user
	 * @return methods
	 */
	public String saveModelInfo(ModelSysTable dto, SysUser user);
	
	/**
	 * 删除信息类
	 * @param dto
	 * @return methods
	 */
	public Object delModelInfo(ModelSysTable dto);
	
	
	/**
	 * 检查表名称唯一
	 * @param clounm
	 * @param uuid
	 * @return methods
	 */
	public String check(String  clounm,   String uuid );

	
	public void countInfoTables();
}
