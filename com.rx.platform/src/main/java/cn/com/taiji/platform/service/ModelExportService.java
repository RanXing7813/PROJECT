package cn.com.taiji.platform.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.Query;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.com.taiji.util.tools.lang.StringTool;


	/** 
	* 
	* @ClassName:  ModelExportService
	* @author ranxing
	* @date 2019年1月31日 下午4:06:23
	*/
	public interface ModelExportService {
		
		public Map<String,Object> getExprotTitle(String model_id, String salt) throws Exception ;

}
