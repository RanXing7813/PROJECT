package cn.com.taiji.platform.service;

import java.util.List;
import java.util.Map;

import cn.com.taiji.platform.entity.ModelSysExport;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.util.page.Pagination;

/** 
* 
* @ClassName:  ModelSysImpl
* @author ranxing
* @date 2019年1月16日 上午11:15:26
*/
public interface ModelSysService {
	public Pagination<ModelSysExport> getListJson  (  Map<String, Object> searchParameters , ModelSysExport dto , Pagination<ModelSysExport> pag )throws Exception    ;


	/**
	 * @param model_id
	 * @return methods
	 */
	public List<Map<String, Object>> getOldColumnList(String model_id) ;


	/**
	 * @param model_id
	 * @param ids
	 * @param names
	 * @param types 
	 * @param user methods
	 */
	public void saveOne(String model_id, String[] ids, String[] names, String[] types, SysUser user) ;


	/**
	 * @param dto
	 * @return methods
	 */
	public Object findSomeOne(ModelSysExport dto) ;


	/**
	 * @param dto
	 * @param user methods
	 */
	public void saveSomeOne(ModelSysExport dto, SysUser user) ;


	/**
	 * @param ids methods
	 */
	public void delSomeData(String[] ids) ;


	/**
	 * @param type
	 * @param model_id
	 * @param col_name
	 * @param col_id 
	 * @return methods
	 */
	public String check(String type, String model_id, String col_name, String col_id) ;


	/**
	 * @param dto
	 * @return methods
	 */
	public List<Map<String, Object>> priList(ModelSysExport dto) ;


//	/**
//	 * 获取列表
//	 * @param searchParameters
//	 * @param dto
//	 * @param pag
//	 * @return
//	 * @throws Exception methods
//	 */
//	public Pagination<ModelSysTableDto> getModelTableList  (  Map<String, Object> searchParameters , ModelSysTableDto dto , Pagination<ModelSysTableDto> pag )throws Exception;  
//
//	/**
//	 * 查找信息类
//	 * @param dto
//	 * @return methods
//	 */
//	public Object findModelInfo(ModelSysTable dto);
//
//	/**
//	 * 保存信息类
//	 * @param dto
//	 * @param user
//	 * @return methods
//	 */
//	public String saveModelInfo(ModelSysTable dto, SysUser user);
//	
//	/**
//	 * 删除信息类
//	 * @param dto
//	 * @return methods
//	 */
//	public Object delModelInfo(ModelSysTable dto);
//	
//	
//	/**
//	 * 检查表名称唯一
//	 * @param clounm
//	 * @param uuid
//	 * @return methods
//	 */
//	public int check(String  clounm,   String uuid );

	
	
}
