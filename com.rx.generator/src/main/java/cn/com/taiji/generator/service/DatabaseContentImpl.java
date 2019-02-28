/**   
* @Title: DatabaseContentImpl.java 
* @Package cn.com.taiji.generator.service 
* @Description: TODO(用一句话描述该文件做什么) 
* @author ranxing   
* @date 2019年2月26日 上午9:48:36 
* @version V1.0   
*/
package cn.com.taiji.generator.service;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.szmirren.options.DatabaseConfig;

import cn.com.taiji.generator.entity.DatabaseContent;
import cn.com.taiji.generator.repository.DatabaseContentRepository;

/** 
* @ClassName: DatabaseContentImpl 
* @Description: TODO 
* @author ranxing 
* @date 2019年2月26日 上午9:48:36 
*  
*/
@Service
public class DatabaseContentImpl {

	@Inject
	DatabaseContentRepository databaseContentRepository;
	@Inject
	JdbcTemplate jdbcTemplate_generator;
	/** 
	* @Title: saveOne 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param dto    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	@Transactional
	public void saveOne(DatabaseContent dto) throws Exception {
		// TODO Auto-generated method stub
		databaseContentRepository.save(dto);
	}

	/** 
	* @Title: getDatabaseConfigByDbName 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param dbName
	* @param @return    设定文件 
	* @return DatabaseConfig    返回类型 
	* @throws 
	*/
	@Transactional
	public DatabaseConfig getDatabaseConfigByDbName(String dbName) throws Exception {
		// TODO Auto-generated method stub
		DatabaseContent dto = databaseContentRepository.findOne(dbName);
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setConnName(dto.getDisplayName());
		dbConfig.setConnURL(dto.getHost());
		dbConfig.setDbName(dto.getDbName());
		dbConfig.setDbType(dto.getDbType());
		dbConfig.setEncoding(dto.getEncoding());
		dbConfig.setListenPort(dto.getPort());
		dbConfig.setUserName(dto.getUserName());
		dbConfig.setUserPwd(dto.getUserPwd());
		return dbConfig;
	}

	/** 
	* @Title: getDBList 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return List    返回类型 
	* @throws 
	*/
	public List<Map<String, Object>> getDBList() {
		// TODO Auto-generated method stub
		return jdbcTemplate_generator.queryForList("select * from databaseconfig ");
	}

	/** 
	* @Title: setTableNameText 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param @param map
	* @param @return    设定文件 
	* @return Map    返回类型 
	* @throws 
	*/
	public Map setTableNameText(Map map) {
		// TODO Auto-generated method stub
		List<Map<String, Object>>  list = jdbcTemplate_generator.queryForList("select * from tablepackagepath ");
		map.put("code", 0);
		map.put("msg", 0);
		map.put("data", list);
		map.put("count", list.size());
		return map;
	}

}
