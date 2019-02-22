package cn.com.taiji.platform.service.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.taiji.platform.dto.ModelDataDto;
import cn.com.taiji.platform.entity.ModelData;
import cn.com.taiji.platform.entity.ModelDataDept;
import cn.com.taiji.platform.entity.ModelPush;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.platform.repository.ModelDataDeptRepository;
import cn.com.taiji.platform.repository.ModelDataRepository;
import cn.com.taiji.platform.repository.ModelPushRepository;
import cn.com.taiji.platform.service.ModelService;
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.date.DateUtil;
import cn.com.taiji.util.tools.lang.StringTool;

/** 
* 
* @ClassName:  ModelSysImpl
* @author ranxing
* @date 2019年1月16日 上午11:15:26
*/
@Service
public class ModelImpl implements ModelService  {

	@Inject
	JdbcTemplate jdbcTemplate;
	
	@Inject
	ModelDataRepository modelDataRepository;
	@Inject
	ModelPushRepository modelPushRepository;
	@Inject
	ModelDataDeptRepository modelDataDeptRepository;
	
	/* (non-Javadoc)
	 * @see cn.com.taiji.platform.service.ModelSysService#getModelTableList(java.util.Map, cn.com.taiji.gxwz.dto.ModelSysTableDto, cn.com.taiji.util.page.Pagination) overriding methods
	 */
	public Pagination<ModelDataDto> getListJson  (  Map<String, Object> searchParameters , ModelDataDto dto , Pagination<ModelDataDto> pag )throws Exception    {

			StringBuffer sql=new StringBuffer("  select c.*, d.publish_status, DATE_FORMAT(d.publish_time,'%Y-%m-%d %T') publish_time  from model_data c left join model_data_publish d  on  c.model_id = d.model_id     where c.is_del = 'N'  "); 
			 if("Y".equals( dto.getPublish_status()  ) ){
				 sql=new StringBuffer("  select c.*, d.publish_status, DATE_FORMAT(d.publish_time,'%Y-%m-%d %T') publish_time  from model_data c left join model_data_publish d  on  c.model_id = d.model_id left join model_data_dept mdd on c.model_id = mdd.model_id     where c.is_del = 'N'  "); 
	    	   }
			  //where
			    List<String> paramList=new ArrayList<String>();
			     Object[] args = new Object[]{};
			     
			       if(!StringTool.isNull( dto.getModel_info_table_name() )){
			    	 sql.append(" and (   c.model_info_table_name like ?  or c.model_info_table_cname like ?  or c.model_zyid like ?  or c.model_zyname like ?  or c.model_name like ? ) ");
				     paramList.add("%"+dto.getModel_info_table_name()+"%");
				     paramList.add("%"+dto.getModel_info_table_name()+"%");
				     paramList.add("%"+dto.getModel_info_table_name()+"%");
				     paramList.add("%"+dto.getModel_info_table_name()+"%");
				     paramList.add("%"+dto.getModel_info_table_name()+"%");
					}
			     
			       
			    	   if("0".equals( String.valueOf( searchParameters.get("isMyDatas"))) || "null".equals( String.valueOf( searchParameters.get("isMyDatas")))     ){
			    		   sql.append(" and c.cread_id = ? ");
						    paramList.add( searchParameters.get("cread_id")+"" );
			    	   }
			    	   if("Y".equals( dto.getPublish_status()  ) ){
			    		   sql.append(" and d.publish_status = 'Y'  and mdd.dept_id = ? ");
			    		   paramList.add( searchParameters.get("dept_id")+"" );
			    	   }
			       
//				if(!StringTool.isNull( dto.getModel_info_table_name() )){
//				    sql.append(" and c.model_info_table_name like ?");
//				    paramList.add("%"+dto.getModel_info_table_name()+"%");
//				}
//				if(!StringTool.isNull( dto.getModel_info_table_cname())){
//				    sql.append(" and c.model_info_table_cname like ?");
//				    paramList.add("%"+dto.getModel_info_table_cname()+"%");
//				}
//				if(!StringTool.isNull( dto.getModel_zyid())){
//				    sql.append(" and c.model_zyid like ?");
//				    paramList.add("%"+dto.getModel_zyid()+"%");
//				}
//				if(!StringTool.isNull( dto.getModel_zyname())){
//				    sql.append(" and c.model_zyname like ?");
//				    paramList.add("%"+dto.getModel_zyname()+"%");
//				}
//				if(!StringTool.isNull( dto.getModel_name())){
//					sql.append(" and c.model_name like ?");
//					paramList.add("%"+dto.getModel_name()+"%");
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
					if("model_info_table_name".equals(filedSort)){
						sql.append(" order by cread_id desc, model_info_table_name "+filedOrder);
					}
					else if("model_info_table_cname".equals(filedSort)){
						sql.append(" order by cread_id desc,model_info_table_cname "+filedOrder);
					}
					else if("model_zyid".equals(filedSort)){
						sql.append(" order by cread_id desc,model_zyid "+filedOrder);
					}
					else if("model_zyname".equals(filedSort)){
						sql.append(" order by cread_id desc,model_zyname "+filedOrder);
					}
					else if("model_name".equals(filedSort)){
						sql.append(" order by cread_id desc,model_name "+filedOrder);
					}
					else if("model_version".equals(filedSort)){
						sql.append(" order by cread_id desc,model_version "+filedOrder);
					}
					else if("data_count".equals(filedSort)){
						sql.append(" order by  cread_id desc,cast(data_count as UNSIGNED INTEGER) "+filedOrder);
					} 
				}else{
					sql.append(" order by  cread_id desc,cread_time desc " );
				}
				
			  //search  
			      pag  = pag.findPagination(sql.toString(),pag,args,new RowMapper<ModelDataDto>(){
			      @Override  
		            public ModelDataDto mapRow(ResultSet rs, int rowNum) throws SQLException {  
			    	  ModelDataDto info = new ModelDataDto();  
			    	  info.setCread_id( rs.getString("cread_id"));
			    	  info.setCread_name( rs.getString("cread_name"));
			    	  if(rs.getObject("create_time")!=null){
			    		  info.setCread_time(  (Date) rs.getObject("create_time"));
			    	  }
			    	  info.setModel_id( rs.getString("model_id"));
			    	  info.setModel_info_table_cname( rs.getString("model_info_table_cname"));
			    	  info.setModel_info_table_name( rs.getString("model_info_table_name"));
			    	  info.setModel_name( rs.getString("model_name"));
			    	  info.setModel_version( rs.getString("model_version"));
			    	  info.setModel_zydept_id( rs.getString("model_zydept_id")) ;
			    	  info.setModel_zydept_name( rs.getString("model_zydept_name"));
			    	  info.setModel_zyid( rs.getString("model_zyid"));
			    	  info.setModel_zyname( rs.getString("model_zyname"));
			    	  info.setPublish_status( rs.getString("publish_status"));
			    	  info.setPublish_time( rs.getString("publish_time"));
			    	  
				     return info;   
		            }   
			  },jdbcTemplate,"mysql");
			  
		 return pag;
	}

	/**
	 * @param dto
	 * @return methods
	 */
	public Object findOne(ModelDataDto dto) throws Exception  {
		
		ModelData cf  =  modelDataRepository.findOne(dto.getModel_id());
		ModelPush cfP =  modelPushRepository.findOne(dto.getModel_id());
		
		BeanUtils.copyProperties(cf, dto);
		if(cfP !=null ){
			dto.setPublish_status(  String.valueOf(cfP.getPublish_status() ));
		}
		
		return dto;
	}

	/**
	 * @param dto
	 * @param user
	 * @return methods
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String saveData(ModelData dto, SysUser user) throws Exception {
		//新增
		if(dto.getModel_id() ==null ||  dto.getModel_id().equals("")){
			dto.setModel_id( StringTool.getUUID()	 ) ;
			//新增资源编号
			String sql = "select * from model_data t   ORDER BY t.cread_time DESC limit 0,1";
			List<Map<String,Object>> sqllist = jdbcTemplate.queryForList(sql);
			String mh_zyid =  "";
				if( sqllist !=null &&  sqllist.size()==1){
							mh_zyid =   StringTool.null2Empty(sqllist.get(0).get("model_zyid"))  ;
							if(mh_zyid==null ||mh_zyid.isEmpty() ){
								mh_zyid = DateUtil.getNowTime("yyyyMMdd")+"000001";
								dto.setModel_zyid("D"+mh_zyid);
		//						return "D"+mh_zyid;
							}else{
										String newStr = DateUtil.getNowTime("yyyyMMdd")+"000001";
										String str = mh_zyid.substring(1);
										try {
											BigDecimal newNum =new BigDecimal(newStr);
											BigDecimal oldNum =new BigDecimal(str); 
											BigDecimal add1=new BigDecimal(1); 
											
											if( newNum.compareTo(oldNum)== 1  ){
												dto.setModel_zyid("D"+newStr);
				//								return "D"+newStr;
											}else{
												dto.setModel_zyid("D"+(oldNum.add(add1)));
				//								return "D"+(oldNum.add(add1)) ;
											}
										} catch (Exception e) {
											e.printStackTrace();
											mh_zyid = DateUtil.getNowTime("yyyyMMdd")+"000001";
											dto.setModel_zyid("D"+mh_zyid);
				//							return "D"+mh_zyid;
										} 
							}
				}else{
					mh_zyid = DateUtil.getNowTime("yyyyMMdd")+"000001";
					dto.setModel_zyid("D"+mh_zyid);
//					return "D"+mh_zyid;
				}
				
			//新增版本号
				dto.setModel_version("0");
//			String versionSql = " select * from model_data t   ORDER BY   cast(t.model_version as UNSIGNED INTEGER) DESC limit 0,1";
//			List<Map<String,Object>> list = jdbcTemplate.queryForList(versionSql);
//			String model_version = "0";
//			if(list!=null && list.size()>0){
//				model_version =   StringTool.null2Empty(list.get(0).get("model_version"))  ;
//				if(model_version.isEmpty()){
//					model_version = "10";
//				}
//			}
//			dto.setModel_version(  (Integer.valueOf( model_version)+1)+"");
			if(user!=null){
				dto.setCread_id(user.getId());
				dto.setCread_name(user.getUsername());
			}
			dto.setCread_time(new Date());
		}else{
			if(user!=null){
				dto.setUpdate_id(user.getId());
				dto.setUpdate_name(user.getUsername());
				dto.setUpdate_time(new Date());
			}
			dto.setCread_time(new Date());
			
			ModelData dto2 = modelDataRepository.findOne(dto.getModel_id());
			dto.setCread_id(dto2.getCread_id());
			dto.setCread_name(dto2.getCread_name());
			dto.setCread_time(dto2.getCread_time());
			dto.setModel_version(dto2.getModel_version());
		}
		dto.setIs_del('N');
		modelDataRepository.save(dto);
		return dto.getModel_id();
	}
	
	public Object delOne(ModelData dto)  throws Exception {
		   modelDataRepository.delOne(dto.getModel_id());
		return null;
	}
	
	
	public int checkOne(String  clounm,   String uuid ) throws Exception { 
		String sql = "";
		if(uuid != null && !"".equals(uuid)){
			//修改
			sql = " SELECT COUNT(1) FROM model_sys_table a  WHERE a.id_table_name != '"+uuid+"' and     a.table_name ='"+clounm+"'  ";
		}else{
			//新增
			sql = " SELECT COUNT(1) FROM model_sys_table a  WHERE a.table_name ='"+clounm+"'   ";
		}
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		return count;
	}

	/**
	 * @param ids methods
	 */
	public void delDatas(String[] ids) {
		// TODO Auto-generated method stub
		modelDataRepository.delDatas(ids);
	}

	/**
	 * @param ids methods
	 */
	public void backDatas(String[] ids, SysUser user ) {
		// TODO Auto-generated method stub
		modelPushRepository.backSome(ids, user.getId());
		
	}


	public Object pushDatas(String model_id){
		String sql = "select id,parent_id as pId,dept_name as name,dept_desc as deptcode from dept t  where flag = '1' order by dept_index asc";
		List<Map<String,Object>> list = null;
		
		if(model_id!=null && !model_id.isEmpty()){
			sql = "	SELECT t.id, t.parent_id AS pId, t.dept_name AS name, t.dept_desc AS deptcode, mdd.model_id , CASE WHEN mdd.model_id  is  null THEN 'false' ELSE 'true' END checked  FROM dept t LEFT JOIN model_data_dept mdd ON t.id = mdd.dept_id  AND mdd.model_id = ?  WHERE t.flag = '1'  ORDER BY t.dept_index ASC";
			 list = jdbcTemplate.queryForList(sql,new Object[]{model_id});
		}else{
			list = jdbcTemplate.queryForList(sql);
		}
		return list;
	}

	/**
	 * @param model_id
	 * @param ids
	 * @param names methods
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void pushSave(String[] model_id, String[] ids, String[] names, SysUser user ) {
		//处理部门列表
		List<ModelDataDept> list = new ArrayList<ModelDataDept>();
		modelDataDeptRepository.delDatas(model_id);
		String userId = user.getId();
		for(String modelId  : model_id){
			for (int i = 0; i < ids.length; i++) {
				ModelDataDept dataDeptModel = new ModelDataDept();
				dataDeptModel.setModel_id(modelId);
				dataDeptModel.setDept_id(ids[i]);
				dataDeptModel.setDept_full_name(names[i]);
				list.add(dataDeptModel);
				if(i==500){
					modelDataDeptRepository.save(list);
					list.clear();
				}
			}
			if(list.size()>0){
				modelDataDeptRepository.save(list);
				list.clear();
			}
			
			//处理发布信息
			ModelPush pushModel = new ModelPush();
			pushModel.setModel_id(modelId);
			pushModel.setCread_id(userId);
			pushModel.setPublish_status('Y');
			modelPushRepository.save(pushModel);
			modelDataRepository.modelVersionAdd(modelId);
		}
	}
	
	
	
	
	
}
