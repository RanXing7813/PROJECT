package cn.com.taiji.platform.service;

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
import cn.com.taiji.platform.service.ModelSysService;
import cn.com.taiji.util.page.Pagination;
import cn.com.taiji.util.tools.lang.StringTool;

/** 
* 
* @ClassName:  ModelImputService
* @author ranxing
* @date 2019年1月16日 上午11:15:26
*/
public interface ModelImputService { 
	
	public Pagination<ModelImput> getList  (  Map<String, Object> searchParameters , ModelImput dto , Pagination<ModelImput> pag )throws Exception    ;

	/**
	 * @param dto
	 * @return methods
	 */
	public Object findMOne(ModelImput dto) ;

	/**
	 * @param dto
	 * @param user
	 * @return methods
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String saveOne(ModelImput dto, SysUser user) ;
	
	public Object delModelInfo(ModelImput dto) ;
	
	
	public 	void updateStatus(String id, String num);

	public 	void updateImput(String id, String num);

	/**
	 * @param sheet
	 * @param fkid methods
	 */
	public String insertDatas(Sheet sheet, String fkid, SysUser user,UploadFile uf) throws Exception ;

	/**
	 * @param dto methods
	 */
	public void delOne(ModelImput dto) ;
	
}
