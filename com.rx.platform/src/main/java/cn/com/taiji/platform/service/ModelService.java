package cn.com.taiji.platform.service;

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
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.date.DateUtil;
import cn.com.taiji.util.tools.lang.StringTool;

/** 
* 
* @ClassName:  ModelService
* @author ranxing
* @date 2019年1月16日 上午11:15:26
*/
public interface ModelService  {

	public Pagination<ModelDataDto> getListJson  (  Map<String, Object> searchParameters , ModelDataDto dto , Pagination<ModelDataDto> pag )throws Exception    ;

	/**
	 * @param dto
	 * @return methods
	 */
	public Object findOne(ModelDataDto dto) throws Exception  ;

	/**
	 * @param dto
	 * @param user
	 * @return methods
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String saveData(ModelData dto, SysUser user) throws Exception ;
	
	public Object delOne(ModelData dto)  throws Exception ;
	
	
	public int checkOne(String  clounm,   String uuid ) throws Exception ;

	/**
	 * @param ids methods
	 */
	public void delDatas(String[] ids) ;

	/**
	 * @param ids methods
	 */
	public void backDatas(String[] ids, SysUser user ) ;


	public Object pushDatas(String model_id);

	/**
	 * @param model_id
	 * @param ids
	 * @param names methods
	 */
	public void pushSave(String[] model_id, String[] ids, String[] names, SysUser user ) ;
	
	
	
	
	
}
