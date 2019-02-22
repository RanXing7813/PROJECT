package cn.com.taiji.platform.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.Query;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.com.taiji.platform.service.ModelExportService;
import cn.com.taiji.util.tools.lang.StringTool;


	/** 
	* 
	* @ClassName:  ModelExportImpl
	* @author ranxing
	* @date 2019年1月31日 下午4:06:23
	*/
@Service
	public class ModelExportImpl implements ModelExportService {
		

		@Inject
		JdbcTemplate jdbcTemplate;
		
		@Inject
		JdbcTemplate jdbcTemplate_modelImput;
		
		public Map<String,Object> getExprotTitle(String model_id, String salt) throws Exception {
	
			Map<String,Object> searchParameters =  new HashMap<String,Object>();
			
			//获取模板
			String sqlModel = "select * from model_data where   model_id = ? and is_del = 'N' ";
			List<Map<String, Object>> listModel = jdbcTemplate.queryForList(sqlModel,  new Object[] { model_id });
			
			String tableName = "";
			if(listModel!=null && listModel.size()>0){
				tableName = StringTool.null2Empty( listModel.get(0).get("model_info_table_name"));
				searchParameters = listModel.get(0);
			}
			
			//当前设置表主键列表
			String sql = "select * from model_sys_export  where model_id = ? and is_uuid = 'N' order by index_ ";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,  new Object[] { model_id });
			Map<String, Object> mapExport = new LinkedHashMap<String, Object>();
			for (Map<String, Object> map : list) {
				mapExport.put( StringTool.null2Empty( map.get("col_name")),  map.get("col_comment"));
			}
			searchParameters.put("titleList", list);
 			return searchParameters;
		}

}
