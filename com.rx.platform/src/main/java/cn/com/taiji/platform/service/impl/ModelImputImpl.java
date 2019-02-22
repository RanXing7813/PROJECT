package cn.com.taiji.platform.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.platform.entity.ModelImput;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.platform.entity.UploadFile;
import cn.com.taiji.platform.repository.ModelImputRepository;
import cn.com.taiji.platform.service.ModelImputService;
import cn.com.taiji.platform.service.ModelSysService;
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.lang.StringTool;

/** 
* 
* @ClassName:  ModelSysImpl
* @author ranxing
* @date 2019年1月16日 上午11:15:26
*/
@Service
public class ModelImputImpl implements ModelImputService {//implements ModelSysService{
	
	@Inject
	JdbcTemplate jdbcTemplate;
	
	@Inject
	ModelImputRepository modelImputRepository; 
	
	/* (non-Javadoc)
	 * @see cn.com.taiji.platform.service.ModelSysService#getModelTableList(java.util.Map, cn.com.taiji.gxwz.dto.ModelImputImplDto, cn.com.taiji.util.page.Pagination) overriding methods
	 */
	public Pagination<ModelImput> getList  (  Map<String, Object> searchParameters , ModelImput dto , Pagination<ModelImput> pag )throws Exception    {

			StringBuffer sql=new StringBuffer("  select imput_id,imp_model_name ,imp_status,imp_data_num,imp_version,imp_table_name, date_format( mi.create_time, '%Y-%m-%d %T' )  create_time from model_imput mi  where is_del != 'Y' "); 
			  
			  //where
			    List<String> paramList=new ArrayList<String>();
			     Object[] args = new Object[]{};
			     
			        sql.append(" and create_id =  ?");
				    paramList.add(searchParameters.get("userId")+"");
			     
				if(!StringTool.isNull( dto.getImp_model_name())){
				    sql.append(" and imp_model_name like ?");
				    paramList.add("%"+dto.getImp_model_name()+"%");
				}
				if(!StringTool.isNull( dto.getImp_table_name())){
				    sql.append(" and imp_table_name like ?");
				    paramList.add("%"+dto.getImp_table_name()+"%");
				}
				/*if(!StringTool.isNull( dto.getImp_status())){
					sql.append(" and imp_status like ?");
					paramList.add("%"+dto.getImp_table_name()+"%");
				}*/
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
					if("imp_model_name".equals(filedSort)){
						sql.append(" order by  imp_model_name "+filedOrder);
					}else if("imp_table_name".equals(filedSort)){
						sql.append(" order by imp_table_name "+filedOrder);
					} else if("imp_data_num".equals(filedSort)){
						sql.append(" order by  cast(imp_data_num as UNSIGNED INTEGER) "+filedOrder);
					} else if("imp_status".equals(filedSort)){
						sql.append(" order by  imp_status "+filedOrder);
					} 
				}else{
					sql.append(" order by  create_time desc " );
				}
				
			  //search  
			      pag  = pag.findPagination(sql.toString(),pag,args,new RowMapper<ModelImput>(){
			      @Override  
		            public ModelImput mapRow(ResultSet rs, int rowNum) throws SQLException {  
			    	  ModelImput info = new ModelImput(); 
			    	  info.setImp_data_num( rs.getString("imp_data_num"));
			    	  info.setImp_model_name(  rs.getString("imp_model_name"));
			    	  info.setImp_status(  rs.getString("imp_status"));
			    	  info.setImp_table_name(  rs.getString("imp_table_name"));
			    	  info.setImp_version(  rs.getString("imp_version"));
			    	  info.setImput_id(  rs.getString("imput_id"));
			    	  if(rs.getObject("create_time")!=null){
			    		  info.setCreate_time(  (Date) rs.getObject("create_time"));
			    	  }
				     return info;   
		            }   
			  },jdbcTemplate,"mysql");
			  
		 return pag;
	}

	/**
	 * @param dto
	 * @return methods
	 */
	public Object findMOne(ModelImput dto) {
		return modelImputRepository.findOne(dto.getImput_id());
	}

	/**
	 * @param dto
	 * @param user
	 * @return methods
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String saveOne(ModelImput dto, SysUser user) {
		return "";
	}
	
	public Object delModelInfo(ModelImput dto) {
		modelImputRepository.delOne(dto.getImput_id());
		return null;
	}
	
	
	public 	void updateStatus(String id, String num){
		modelImputRepository. updateStatus(  id,   num);
	}

	public 	void updateImput(String id, String num){
		modelImputRepository. updateImput(  id,   num);
	}

	/**
	 * @param sheet
	 * @param fkid methods
	 */
	public String insertDatas(Sheet sheet, String fkid, SysUser user,UploadFile uf) throws Exception {
		// TODO Auto-generated method stub
		Object obj = sheet.getLastRowNum();

		//获取第二行信息
		Row row = sheet.getRow(1);
		if(row !=null){
			// 获取表名
			Cell cell = row.getCell(1); // add
			String tableName = row.getCell(1).getStringCellValue();
			
			//模版资源名称:
			cell = row.getCell(3); // add
			String modelZyname  = row.getCell(3).getStringCellValue();
			
			//版本号
			cell = row.getCell(5); // add
			String versionStr  = row.getCell(5).getStringCellValue();
			
			ModelImput dto = new ModelImput();
			dto.setCreate_id(user.getId());
			dto.setCreate_name(user.getLoginname());
			dto.setImp_model_name(uf.getFilename());
			dto.setImp_table_name(tableName);
			dto.setImp_version(versionStr);
			dto.setImput_id(fkid);
			dto.setImp_status("0");
			if(tableName==null || tableName.isEmpty()){
				return "未设置数据表名";
			}
			if(modelZyname==null || modelZyname.isEmpty()){
				return "未设置模板资源名称";
			}
			if(versionStr==null || versionStr.isEmpty()){
				return "未设置版本号";
			}
			
			//资源id
			cell = row.getCell(7); // add
			String zyidStr  = row.getCell(7).getStringCellValue();
			
			String sqlModel = " select c.* from model_data c left join model_data_publish d  on  c.model_id = d.model_id left join model_data_dept mdd on c.model_id = mdd.model_id     where  c.model_info_table_name = ? and c.model_version = ? and c.model_zyid = ? and c.is_del = 'N'   and d.publish_status = 'Y' and mdd.dept_id = ? ";
//			String sqlModel = "select * from model_data where model_info_table_name = ? and model_version = ? and model_zyid = ?   and is_del = 'N'";
			List<Map<String, Object>> listModel = jdbcTemplate.queryForList(sqlModel,  new Object[] { tableName, versionStr, zyidStr, user.getDeptid()  });
			if(listModel!=null && listModel.size() == 1){
				modelImputRepository.saveAndFlush(dto);
			}else{
				return "账号无对应模板录入权限,模板资源编号:"+zyidStr;
			}
			
		}

		return "";
	}

	/**
	 * @param dto methods
	 */
	public void delOne(ModelImput dto) {
		modelImputRepository.delOne(dto.getImput_id());		
	}
	
}
