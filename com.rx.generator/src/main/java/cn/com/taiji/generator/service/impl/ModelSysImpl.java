package cn.com.taiji.generator.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import cn.com.taiji.generator.entity.ModelSysExport;
import cn.com.taiji.generator.repository.ModelSysExportRepository;
import cn.com.taiji.generator.service.ModelSysService;

import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.util.page.PaginationUtil;
import cn.com.taiji.util.tools.lang.StringTool;

/** 
* 
* @ClassName:  ModelSysImpl
* @author ranxing
* @date 2019年1月16日 上午11:15:26
*/
@Service
public class ModelSysImpl  implements ModelSysService{

	
	@Inject
	JdbcTemplate jdbcTemplate;
	
	@Inject
	JdbcTemplate jdbcTemplate_modelImput;
	
	@Inject
	ModelSysExportRepository modelSysExportRepository;
	
	
	/* (non-Javadoc)
	 * @see cn.com.taiji.platform.service.ModelSysService#getModelTableList(java.util.Map, cn.com.taiji.gxwz.dto.ModelSysTableDto, cn.com.taiji.util.page.Pagination) overriding methods
	 */
	@SuppressWarnings("static-access")
	@Override   
	public  Map<String,Object> getListJson  (  Map<String, Object> searchParameters , ModelSysExport dto , PaginationUtil<ModelSysExport> pag )throws Exception    {

			StringBuffer sql=new StringBuffer("  select col_id,col_name,col_comment,column_type,is_uuid,model_id from model_sys_export t1   where model_id = ? "); 
			  
			
			  //where
			    List<String> paramList=new ArrayList<String>();
			    paramList.add( dto.getModel_id());
			     Object[] args = new Object[]{};
//				if(!StringTool.isNull( dto.getTable_name() )){
//				    sql.append(" and table_name like ?");
//				    paramList.add("%"+dto.getTable_name()+"%");
//				}
//				if(!StringTool.isNull( dto.getTable_cname())){
//				    sql.append(" and table_cname like ?");
//				    paramList.add("%"+dto.getTable_cname()+"%");
//				}
				int length = paramList.size();
				  if(length>0){
				      args=new Object[length];
				      for(int i=0;i<length;i++){
					  args[i] = paramList.get(i);
				      }
				  }
				  
		      //order
				String filedSort =StringTool.null2Empty(searchParameters.get("field")) ;
				String filedOrder = StringTool.null2Empty(searchParameters.get("order")) ;
				if(filedSort!=null && !filedSort.isEmpty()){
					if("col_name".equals(filedSort)){
						sql.append(" order by  col_name "+filedOrder);
					}else if("col_comment".equals(filedSort)){
						sql.append(" order by col_comment "+filedOrder);
					} else if("data_count".equals(filedSort)){
						sql.append(" order by  cast(data_count as UNSIGNED INTEGER) "+filedOrder);
					} 
				}else{
					sql.append(" order by  is_uuid desc, index_ desc " );
				}
				
			  //search  
			      pag  = pag.findPagination(sql.toString(),pag,args,new RowMapper<ModelSysExport>(){
			      @Override  
		            public ModelSysExport mapRow(ResultSet rs, int rowNum) throws SQLException {  
			    	  ModelSysExport info = new ModelSysExport(); 
			    	  info.setCol_id( rs.getString("col_id"));
			    	  info.setCol_name( rs.getString("col_name"));
			    	  info.setCol_comment( rs.getString("col_comment"));
			    	  info.setIs_uuid( rs.getString("is_uuid").toCharArray()[0]);
			    	  info.setModel_id( rs.getString("model_id"));
				     return info;   
		            }   
			  },jdbcTemplate,"mysql");
			      
			        searchParameters.put("code", pag.RETURN_SUCCESS_CODE );
					searchParameters.put("msg",  pag.RETURN_SUCCESS_MSG);
					searchParameters.put("list", pag.getDataList());
					searchParameters.put("totle", pag.getDataCount());
			  
		 return searchParameters;
	}


	/**
	 * @param model_id
	 * @return methods
	 */
	@Override   
	public List<Map<String, Object>> getOldColumnList(String model_id) {
		//获取模板
		String sqlModel = "select * from model_data where   model_id = ? and is_del = 'N' ";
		List<Map<String, Object>> listModel = jdbcTemplate.queryForList(sqlModel,  new Object[] { model_id });
		
		String tableName = "";
		if(listModel!=null && listModel.size()>0){
			tableName = StringTool.null2Empty( listModel.get(0).get("model_info_table_name"));
		}
		
		//原始表键列表
		String sqlColumOriginal = " select * from information_schema.columns where  TABLE_SCHEMA = (select database())  and   table_name = ?  ";
		List<Map<String, Object>> listColumOriginal = jdbcTemplate_modelImput.queryForList(sqlColumOriginal,  new Object[] { tableName });
		
		//当前设置表键列表
		String sql = "select * from model_sys_export  where model_id = ?  ";
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,  new Object[] { model_id });
		Map<String, Object> mapExport = new HashMap<String, Object>();
		for (Map<String, Object> map : list) {
			mapExport.put( StringTool.null2Empty( map.get("col_name")),  map.get("col_name"));
		}
		
		list = new ArrayList<Map<String, Object>>(); 
		for (Map<String, Object> map : listColumOriginal) {
			String tableName1 = StringTool.null2Empty( map.get("COLUMN_NAME"));
			if( !mapExport.containsKey(tableName1)  ){
				list.add(map);
			} 
		}
		
		return list;
	}


	/**
	 * @param model_id
	 * @param ids
	 * @param names
	 * @param user methods
	 */
	@Override   
	public void saveOne(String model_id, String[] ids, String[] names, String[] types, SysUser user) {
		
		List<ModelSysExport> list = new ArrayList<ModelSysExport>();
		for (int i = 0; i < ids.length; i++) {
			String strId = ids[i];
			String strName = names[i];
			if(strName==null || "null".equals(strName) || strName.isEmpty()){
				strName = "-";
			}
			
			ModelSysExport info = new ModelSysExport();
			info.setCol_id(StringTool.getUUID());
			info.setCol_name(strId);
			info.setCol_comment( strName     );
			info.setColumn_type(types[i]);
			info.setIs_uuid('N');
			info.setModel_id(model_id);
			info.setIndex_(10);
			list.add(info);
		}
		modelSysExportRepository.save(list);
		
	}


	/**
	 * @param dto
	 * @return methods
	 */
	@Override   
	public Object findSomeOne(ModelSysExport dto) {
		return modelSysExportRepository.findOne(dto.getCol_id());
	}


	/**
	 * @param dto
	 * @param user methods
	 */
	@Override   
	public void saveSomeOne(ModelSysExport dto, SysUser user) {
		if(dto.getCol_id() ==null ||  dto.getCol_id().equals("")){
			dto.setCol_id( StringTool.getUUID()	 ) ;
		}else{
			
		}
		
		
		modelSysExportRepository.save(dto);
	}


	/**
	 * @param ids methods
	 */
	@Override   
	public void delSomeData(String[] ids) {
		for (String id : ids) {
			modelSysExportRepository.delete(id);	
		}
	}


	/**
	 * @param type
	 * @param model_id
	 * @param col_name
	 * @param col_id 
	 * @return methods
	 */
	@Override   
	public String check(String type, String model_id, String col_name, String col_id) {
				//获取模板 表名
				String sqlModel = "select * from model_data where   model_id = ? and is_del = 'N' ";
				List<Map<String, Object>> listModel = jdbcTemplate.queryForList(sqlModel,  new Object[] { model_id });
				String tableName = "";
				if(listModel!=null && listModel.size()>0){
					tableName = StringTool.null2Empty( listModel.get(0).get("model_info_table_name"));
				}
				
				//当前设置表主键列表
				String sql = "select * from model_sys_export  where model_id = ? and is_uuid = 'Y'   ";
//				if(col_id!=null && !col_id.isEmpty()){//编辑校验
//					
//				}
				
				List<Map<String, Object>> list = jdbcTemplate.queryForList(sql,  new Object[] { model_id  });
				Map<String, Object> mapExport = new HashMap<String, Object>();
				for (Map<String, Object> map : list) {
					mapExport.put( StringTool.null2Empty( map.get("col_name")),  map.get("col_name"));
				}
				
				//存在主键
				if (list!=null && list.size()>0) {
					return "已存在主键设置,请确认唯一性!!!";
				} 
				
				//原始表主键列表
				String sqlColumOriginal = " select * from information_schema.columns where  TABLE_SCHEMA = (select database())  and   table_name = ?  and COLUMN_KEY = 'PRI' ";
				List<Map<String, Object>> listColumOriginal = jdbcTemplate_modelImput.queryForList(sqlColumOriginal,  new Object[] { tableName });
				
				list = new ArrayList<Map<String, Object>>(); 
				for (Map<String, Object> map : listColumOriginal) {
					String COLUMN_NAME = StringTool.null2Empty( map.get("COLUMN_NAME"));
					if( COLUMN_NAME.equals(col_name) ){
						list.add(map);
					} 
				}
				
				//不存在主键  
				//主键字段正确
				if (list!=null && list.size()>0) {
					return "";
				}else{
					//主键字段错误
					return "请确认主键字段是否书写正确,注意区分大小写!!!";
				}
	}


	/**
	 * @param dto
	 * @return methods
	 */
	@Override   
	public List<Map<String, Object>> priList(ModelSysExport dto) {
		// TODO Auto-generated method stub
		//获取模板
		String sqlModel = "select * from model_data where   model_id = ? and is_del = 'N' ";
		List<Map<String, Object>> listModel = jdbcTemplate.queryForList(sqlModel,  new Object[] { dto.getModel_id() });
		
		String tableName = "";
		if(listModel!=null && listModel.size()>0){
			tableName = StringTool.null2Empty( listModel.get(0).get("model_info_table_name"));
		}
		
		//原始表主键列表
		String sqlColumOriginal = " select * from information_schema.columns where  TABLE_SCHEMA = (select database())  and   table_name = ?  and COLUMN_KEY = 'PRI' and EXTRA != 'auto_increment' ";
		List<Map<String, Object>> listColumOriginal = jdbcTemplate_modelImput.queryForList(sqlColumOriginal,  new Object[] { tableName });
		
		return listColumOriginal;
	}


}
