package cn.com.taiji.generator.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.szmirren.common.DBUtil;
import com.szmirren.common.StrUtil;
import com.szmirren.options.DatabaseConfig;
import com.szmirren.spi.DatabaseTypeNames;

import cn.com.taiji.generator.entity.DatabaseContent;
import cn.com.taiji.generator.service.DatabaseContentImpl;

/** 
* 常规代码生成首页
* @ClassName: IndexController 
* @Description: TODO 
* @author ranxing 
* @date 2019年2月25日 下午2:43:52 
*  
*/
@Controller
@RequestMapping(value = "/generator/normal")
public class IndexController {
	
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);
	
	@Inject
	DatabaseContentImpl databaseContentImpl;
	
	/**
	 * 默认代码生成器首页
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "index", method = {RequestMethod.GET })
	public String toIndexPage(Model model){
		List list =databaseContentImpl.getDBList();
		model.addAttribute("list", list);
		return "generator/normal/index";
	}
	
	@RequestMapping(value = "toConnectionPage", method = {RequestMethod.GET })
	public String toAddConnectionPage(Model model, DatabaseContent dto){
		model.addAttribute("dto", dto);
		model.addAttribute("DatabaseTypeNames", DatabaseTypeNames.dbTypeNames());
		return "generator/normal/connection";
	}
	
	@RequestMapping(value = "saveConnectionPage", method = {RequestMethod.POST })
	public String saveAddConnectionPage(DatabaseContent dto, HttpServletRequest request){
		log.debug("执行保存数据库信息....");
		try {
			databaseContentImpl.saveOne(dto);
		} catch (Exception e) {
			log.error("保存数据库信息失败!!!" , e);
			e.printStackTrace();
		}
    	return "/platform/success/success2";
	}
	@ResponseBody
	@RequestMapping(value = "getTableNames", method = {RequestMethod.GET })
	public Object getTableNames(Model model,  String dbName){
		log.debug("执行打开数据库连接....");
		try {
			DatabaseConfig selectedConfig = (DatabaseConfig) databaseContentImpl.getDatabaseConfigByDbName(dbName);;
			List<String> tables = DBUtil.getTableNames(selectedConfig);
			Map map = new HashMap();
			map.put("list", tables);
			return map;
		} catch (Exception e) {
			log.error("打开连接失败!!!" , e);
		}
		return null;
	}
	@ResponseBody
	@RequestMapping(value = "setTableNameText", method = {RequestMethod.GET })
	public Object setTableNameText(Model model,  String dbName, String tabeName){
		log.debug("执行打开数据库连接....");
		try {
			log.debug("将表的数据加载到数据面板...");
//			String tableName = treeCell.getTreeItem().getValue();
//			selectedDatabaseConfig = (DatabaseConfig) treeItem.getParent().getGraphic().getUserData();
//			selectedTableName = tableName;
//			txtTableName.setText(tableName);
//			String pascalTableName = StrUtil.unlineToPascal(tableName);
//			txtEntityName.setText(entityNamePlace.replace("{c}", pascalTableName));
//			txtServiceName.setText(serviceNamePlace.replace("{c}", pascalTableName));
//			txtServiceImplName.setText(serviceImplNamePlace.replace("{c}", pascalTableName));
//			txtRouterName.setText(routerNamePlace.replace("{c}", pascalTableName));
//			txtSqlName.setText(daoNamePlace.replace("{c}", pascalTableName));
//			txtMapperName.setText(mapperNamePlace.replace("{c}", pascalTableName));
//			txtUnitTestName.setText(unitTestPlace.replace("{c}", pascalTableName));
			log.debug("将表的数据加载到数据面板成功!");
			Map map = new HashMap();
			
//			{
//				  "code": "0",
//				  "msg": 0,
//				  "count": 0,
//				  "data": [
//							  {"lblEntityPackage":"实体类包名",					"lblEntityName":"cn.com.taiji.entity"}
////							  {"lblServicePackage":"Service包名",				"lblServiceName":"cn.com.taiji.service"},
////							  {"lblServiceImplPackage":"ServiceImpl包名",		"lblServiceImplName":"cn.com.taiji.service.impl"},
////							  {"lblSqlPackage":"Dao包名",						"lblSqlName":"cn.com.taiji.dao"},
////							  {"lblMapperPackage":"映射文件包名",				"lblMapperName":"cn.com.taiji.mapper"},
////							  {"lblRouterPackage":"Controller包名:",			"lblRouterName":"cn.com.taiji.web"},
////							  {"lblUnitTestPackage":"单元测试包名:",				"lblUnitTestName":"cn.com.taiji.test"}
////							  {"lblAssistPackage":"Assist包名",				"lblAssistName":"cn.com.taiji.common"},
//						  ]
//			}
			map.put("code", 0);
			map.put("msg", 0);
			map.put("count", 10);
			List list = new ArrayList();
			Map maplist1 = new HashMap();
			maplist1.put("lblEntityPackage", "实体类包名");
			list.add(maplist1);
			
			map.put("data", list);
			
			Map map2 = databaseContentImpl.setTableNameText(map);
			return map2;
		} catch (Exception e) {
			log.error("将表的数据加载到数据面板失败!!!" , e);
		}
		return null;
	}
	
	
}
