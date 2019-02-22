package cn.com.taiji.platform.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.gxwz.dto.ModelSysTableDto;
import cn.com.taiji.platform.entity.ModelSysTable;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.platform.repository.ModelSysTableRepository;
import cn.com.taiji.platform.service.ModelInfoService;
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.lang.StringTool;

/** 
* 
* @ClassName:  ModelSysImpl
* @author ranxing
* @date 2019年1月16日 上午11:15:26
*/
@Service
public class ModelInfoImpl implements ModelInfoService{

	@Inject
	JdbcTemplate jdbcTemplate;
	@Inject
	JdbcTemplate jdbcTemplate_modelImput;
	
	@Inject
	ModelSysTableRepository modelSysTableRepository; 
	
	/* (non-Javadoc)
	 * @see cn.com.taiji.platform.service.ModelSysService#getModelTableList(java.util.Map, cn.com.taiji.gxwz.dto.ModelSysTableDto, cn.com.taiji.util.page.Pagination) overriding methods
	 */
	public Pagination<ModelSysTableDto> getModelTableList  (  Map<String, Object> searchParameters , ModelSysTableDto dto , Pagination<ModelSysTableDto> pag )throws Exception    {

			StringBuffer sql=new StringBuffer("  select id_table_name,table_name,table_cname,data_count from model_sys_table   where 1 =1  "); 
			  
			  //where
			    List<String> paramList=new ArrayList<String>();
			     Object[] args = new Object[]{};
				if(!StringTool.isNull( dto.getTable_name() )){
				    sql.append(" and table_name like ?");
				    paramList.add("%"+dto.getTable_name()+"%");
				}
				if(!StringTool.isNull( dto.getTable_cname())){
				    sql.append(" and table_cname like ?");
				    paramList.add("%"+dto.getTable_cname()+"%");
				}
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
					if("table_name".equals(filedSort)){
						sql.append(" order by  table_name "+filedOrder);
					}else if("table_cname".equals(filedSort)){
						sql.append(" order by table_cname "+filedOrder);
					} else if("data_count".equals(filedSort)){
						sql.append(" order by  cast(data_count as UNSIGNED INTEGER) "+filedOrder);
					} 
				}else{
					sql.append(" order by  table_name " );
				}
				
			  //search  
			      pag  = pag.findPagination(sql.toString(),pag,args,new RowMapper<ModelSysTableDto>(){
			      @Override  
		            public ModelSysTableDto mapRow(ResultSet rs, int rowNum) throws SQLException {  
			    	  ModelSysTableDto info = new ModelSysTableDto(); 
			    	  info.setId_table_name( rs.getString("id_table_name"));
			    	  info.setTable_name(    rs.getString("table_name"));
			    	  info.setTable_cname(   rs.getString("table_cname"));
			    	  info.setData_count(    rs.getString("data_count"));
				     return info;   
		            }   
			  },jdbcTemplate,"mysql");
			  
		 return pag;
	}

	/**
	 * @param dto
	 * @return methods
	 */
	public Object findModelInfo(ModelSysTable dto) {
		return modelSysTableRepository.findOne(dto.getId_table_name());
	}

	/**
	 * @param dto
	 * @param user
	 * @return methods
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String saveModelInfo(ModelSysTable dto, SysUser user) {
		//新增
		if(dto.getId_table_name() ==null ||  dto.getId_table_name().equals("")){
			dto.setId_table_name( StringTool.getUUID()	 ) ;
		}else{
			 
		}
		modelSysTableRepository.save(dto);
		return dto.getId_table_name();
	}
	
	public Object delModelInfo(ModelSysTable dto) {
		modelSysTableRepository.delete(dto.getId_table_name());
		return null;
	}
	
	
	public String check(String  clounm,   String uuid ) {
		String sql = " select count(1) from information_schema.`TABLES` t WHERE t.TABLE_SCHEMA = (select database()) and TABLE_NAME = ? ";
		int count = jdbcTemplate_modelImput.queryForObject(sql, new Object[]{clounm}, Integer.class);
		if(count != 1 ){
			return "请确认信息类库中此表是否存在,查出来"+count+"个此表!";
		}
		
		if(uuid != null && !"".equals(uuid)){
			//修改
			sql = " SELECT COUNT(1) FROM model_sys_table a  WHERE a.id_table_name != ? and a.table_name = ?  ";
			count = jdbcTemplate.queryForObject(sql, new Object[]{ uuid, clounm }, Integer.class);
		}else{
			//新增
			sql = " SELECT COUNT(1) FROM model_sys_table a  WHERE a.table_name = ? ";
			count = jdbcTemplate.queryForObject(sql, new Object[]{clounm}, Integer.class);
		}
		if(count != 0 ){
			return "请确认是否重复,已查出来"+count+"个此表数据!";
		}
		
		return "N";
	}

	public void countInfoTables(){
		String sql = " select table_name from model_sys_table ";
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
		for (Map<String, Object> map : list) {
			String table_name = String.valueOf(map.get("table_name") );
			
			String countSql = " select count(1) from "+table_name;
			int count = jdbcTemplate_modelImput.queryForObject(countSql, Integer.class);

			String updateSql = " update  model_sys_table set data_count = "+count+" WHERE   `table_name` = "+table_name;
			jdbcTemplate.execute(updateSql);
		}

	}
	
}
