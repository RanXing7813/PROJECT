package cn.com.taiji.platform.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import cn.com.taiji.platform.entity.ModelImput;
import cn.com.taiji.platform.entity.SysUser;
import cn.com.taiji.platform.repository.ModelImputRepository;
import cn.com.taiji.platform.repository.UploadFileRepository;
import cn.com.taiji.platform.service.ModelCheckService;
import cn.com.taiji.platform.web.model.ParityController;
import cn.com.taiji.util.tools.lang.StringTool;


/** 
* 模板导入 - 检查
* @ClassName:  ModelCheckImpl
* @author ranxing
* @date 2019年1月19日 上午11:22:41
*/
@Service
public class ModelCheckImpl implements  ModelCheckService{

	
	private int maxRowLength = 50004;//可校验表单最大行数
	
	@Inject
	JdbcTemplate jdbcTemplate;
	@Inject
	JdbcTemplate jdbcTemplate_modelImput;
	@Inject
	ModelImputRepository modelImputRepository;
	@Inject
	UploadFileRepository uploadFileRepository;
	
	@SuppressWarnings("deprecation")
	public Object checkExcel(Sheet sheet, String id, SysUser user ){
		
		//获取excel 字段
		StringBuffer error = new StringBuffer("");
//		List<Map<String, String>> rowdataList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> alldataList = new ArrayList<Map<String, String>>();

		Map<String, Object> resultMap = new HashMap<String, Object>();
//		Object obj = sheet.getLastRowNum();
		
			int nowLength = 0 ;
			Row row = sheet.getRow(1);
			if (row == null) {
				error.append("第二行:不能为空");
				resultMap.put("error", error.toString());
				return resultMap;
			}
			
			Cell cell = row.getCell(1); // 获取表名称
			String tableName = row.getCell(1).getStringCellValue();
			
			cell = row.getCell(3); // 获取模板资源名称
			String modelZyname  = row.getCell(3).getStringCellValue();
			
			cell = row.getCell(5); // 获取版本号
			String versionStr  = row.getCell(5).getStringCellValue();
			
			cell = row.getCell(7); // 获取版本号
			String model_zyid  = row.getCell(7).getStringCellValue();
			
			if(tableName==null || tableName.isEmpty()){
				error.append("第二行:数据表名不能为空 /n ");
			}
			if(modelZyname==null || modelZyname.isEmpty()){
				error.append("第二行:未设置模板资源名称 /n ");
			}
			if(versionStr==null || versionStr.isEmpty()){
				error.append("第二行:未设置版本号 /n ");
			}
			if(model_zyid==null || model_zyid.isEmpty()){
				error.append("第二行:未设置资源编号 /n ");
			}
			if (tableName==null || tableName.isEmpty() || modelZyname==null || modelZyname.isEmpty() || versionStr==null || versionStr.isEmpty() || model_zyid==null || model_zyid.isEmpty() ) {
				resultMap.put("error", error.toString()+"<br>");
				return resultMap;
			}
			
			String  model_id = "" ;
			//获取模板字段设置 的非主键字段
			String sqlModel = "select * from model_data where model_info_table_name = ? and model_version = ? and model_zyid = ? and is_del = 'N'";
			List<Map<String, Object>> listModel = jdbcTemplate.queryForList(sqlModel,  new Object[] { tableName, versionStr, model_zyid  });
			if(listModel!=null && listModel.size()>0){
				model_id = StringTool.null2Empty(listModel.get(0).get("model_id"));
			}else{
				error.append("第二行:资源编号,版本号 和 模板不匹配 /n ");
				resultMap.put("error", error.toString()+"<br>");
				return resultMap;
			}
			
			String sqlExcelColum = "SELECT * FROM model_sys_export WHERE model_id = ? and is_uuid = 'N'  ";
			List<Map<String, Object>> listExcelColum = jdbcTemplate.queryForList(sqlExcelColum,  new Object[] { model_id });
			
			int excelColumLength = listExcelColum.size();
			if ( excelColumLength == 0) {
				error.append("未在  模板设置中  设置模板字段<br>");
				resultMap.put("error", error.toString());
				return resultMap;
			}
			// 获取 第三行 表字段
			row = sheet.getRow(2);
			if (row == null) {
				error.append("第三行:不能为空<br>");
				resultMap.put("error", error.toString());
				return resultMap;
			}
			//获取Excel中的列顺序
			Map<Integer, String> columExcelMap = new HashMap<Integer, String>();
			String html_ = "{\"type\": \"numbers\", \"title\":\"序号\", \"fixed\": \"left\",\"width\":\"60\"}";
			Row row4 = sheet.getRow(3);
			for (int i = 0; i < excelColumLength; i++) {
				Cell cell2 = row.getCell(i);
				if (cell2 == null || "".equals(cell2.getStringCellValue())) {
					error.append("第三行:模版第"+(i+1)+"列英文名称不能为空<br>");
					resultMap.put("error", error.toString());
					return resultMap;
				}
				columExcelMap.put(i, cell2.getStringCellValue());
				html_ += ",{\"width\":\"152\",\"field\":\""+cell2+"\", \"title\":\""+row4.getCell(i)+"\" }";
			}
			
			
			//获取模板字段设置 的主键字段
			String sqlExcelColumIsUUID = "SELECT * FROM model_sys_export WHERE model_id = ? and is_uuid = 'Y'  ";
			List<Map<String, Object>> listExcelColumIsUUID = jdbcTemplate.queryForList(sqlExcelColumIsUUID,  new Object[] { model_id });
			String uuidColumn_name = "";
			for (int i = 0; i < listExcelColumIsUUID.size(); i++) {
				uuidColumn_name = StringTool.null2Empty (listExcelColumIsUUID.get(i).get("col_name"));
			}
			
			//获取模板表的  原始字段信息
			String sqlColumOriginal = " select * from information_schema.columns where  TABLE_SCHEMA = (select database())  and  table_name = ? order by  ORDINAL_POSITION desc ";
			List<Map<String, Object>> listColumOriginal = jdbcTemplate_modelImput.queryForList(sqlColumOriginal,  new Object[] { tableName });
			Map<Object, Object> columOriginalMap = new HashMap<Object, Object>();// 校验
			for (Map<String, Object> map : listColumOriginal) {
				columOriginalMap.put( map.get("COLUMN_NAME"), map);  //{"int_6":map}
				
				String PRI = StringTool.null2Empty (map.get("COLUMN_KEY"));
				if("PRI".equals(PRI)){
					// && listExcelColumIsUUID.size()!=0
					String COLUMN_NAME = StringTool.null2Empty (map.get("COLUMN_NAME"));
					if(!uuidColumn_name.isEmpty() &&  !uuidColumn_name.equals(COLUMN_NAME)){
						error.append("模版未正确设置主键字段"+COLUMN_NAME+",当前设置的主键字段为"+uuidColumn_name+" <br>");
						resultMap.put("error", error.toString());
						return resultMap;
					}
					
				}
				
			}
			
			//校验字段
			// 循环行//进行逐行校验//这里转换成实际行数//原先角标从0,现在从1
			boolean nullBreak = false;//后面加的功能, 行数据全为空的状态
			for (int i = 4; i <= maxRowLength; i++) {
				int emptyNum = 0;
				String errorStr = ""; // 存放当前行错误信息"第"+nowLength+" 行" +
				row = sheet.getRow( i ); // 获取当前行 按照从0开始排列的
				if (row == null) {
					break;
				}
	
				// 回填数据
				Map<String, String> rowdataListMap = new LinkedHashMap<String, String>();
//				Map<String, String> alldataListMap = new LinkedHashMap<String, String>();
					// 循环列当  列为空时结束循环
					for (int j = 0; j < excelColumLength; j++) {
						cell = row.getCell(j);
						String cellValue = "";
						int type = 3;
						if (cell != null)  type = cell.getCellType();
						cellValue = getCellValue(type, cell);
						
						// 进行列为空计数
						if (cellValue == null || cellValue.isEmpty())  	emptyNum++;
						if (emptyNum == excelColumLength) {
							nullBreak = true;
							break;
						}  
					}
					nowLength = i ;
					if (nullBreak)  break; //行为空跳出不进行下面的校验
					
					// 循环列当  列为空时结束循环
					for (int j = 0; j < excelColumLength; j++) {
						cell = row.getCell(j);
						String cellValue = "";
						int type = 3;
						if (cell != null)  type = cell.getCellType();
						cellValue = getCellValue(type, cell);
						
						//存入返回数据中
						rowdataListMap.put(columExcelMap.get(j), cellValue);
						
						if(  columOriginalMap.containsKey( columExcelMap.get(j)  )   ){
							String str = ParityController.parityColum(j,cellValue, (Map)columOriginalMap.get(columExcelMap.get(j)));
								errorStr += str ;
						}else{
							error.append( "第"+nowLength+" 行, "+(j+1)+"列  数据和模板字段设置匹配异常 <br>");
						}
					}
					alldataList.add(rowdataListMap);
					if(errorStr!=null && !errorStr.isEmpty()){
						error.append("第"+nowLength+" 行"+errorStr);
					}
					
			}
			
			
			// 如果没有错误就修改状态为验证通过
			ModelImput sf = modelImputRepository.findOne(id);
			String status = sf.getImp_status(); // 数据状态  0:未校验  1:校验通过  2:校验不通过 3:数据已导入
			
			if ("0".equals(status)) { //&& ( "0".equals(sf.getImp_data_num()) )
				// 如果没有错误就修改状态为验证通过
				if (error.toString().length() == 0) {
					modelImputRepository.updateStatus(id, "1");
				} else {
					modelImputRepository.updateStatus(id, "2");
				}
			}else if(  "1".equals(status)  && error.toString().length() >0){
				error.append("模板被修改,已不符合现校验规则");
				modelImputRepository.updateStatus(id, "2");
				
			}else if(   "3".equals(status)  && error.toString().length() >0){
				error.append("模板被修改,已不符合现校验规则");
				
			}
			
			if(nowLength > maxRowLength){
				error.append("数据大于"+(maxRowLength-4)+"条,请分成多个excel文件导入,检测到"+(sheet.getLastRowNum()-4)+"条数据");
			}
		
		resultMap.put("error", error.toString());
		resultMap.put("alldataList", alldataList);
		resultMap.put("html_", html_);
		return resultMap;
	}
	
	
	@SuppressWarnings({ "deprecation" })
	private String getCellValue(int type, Cell cell){
		switch (type) {
			case 0:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					return DateFormatUtils.format(date, "yyyy-MM-dd");
				} else {
					cell.setCellType(1);
					return cell.getStringCellValue();
				}
			case 1:
					cell.setCellType(1);
					return cell.getStringCellValue();
			case 2:
					return cell.getCellFormula();
			case 3:
					return "";
			default : return cell.getStringCellValue();
		}
	}


	/**
	 * @param dto
	 * @return methods
	 */
	public Object findOne(ModelImput dto) {
		return modelImputRepository.findOne(dto.getImput_id());
	}


	/**
	 * @param dto
	 * @return methods
	 */
	public cn.com.taiji.platform.entity.UploadFile findUploadOne(ModelImput dto) {
		return uploadFileRepository.getOneByFkid(dto.getImput_id());
	}
	
	
	
	@SuppressWarnings("deprecation")
	public Object pushExcel(Sheet sheet, String id,  SysUser user) throws Exception{
		//获取excel 字段
		StringBuilder error = new StringBuilder("");
		Map<String, Object> resultMap = new HashMap<String, Object>();
			
			
			ModelImput sf = modelImputRepository.findOne(id);
			String status = sf.getImp_status(); // 数据状态  0:未校验  1:校验通过  2:校验不通过 3:数据已导入
		
		switch (status) {
		case "0":
			error.append("数据未校验");
			resultMap.put("error", error.toString());
			return resultMap;
		case "2":
			error.append("数据验证未通过");
			resultMap.put("error", error.toString());
			return resultMap;
		case "3":
			error.append("已导入过的数据不能重新导入");
			resultMap.put("error", error.toString());
			return resultMap;
		}

		StringBuilder sql = new StringBuilder(" INSERT INTO ");
		StringBuilder sqlOtherColumn = new StringBuilder( );
		StringBuilder batcharg = new StringBuilder();
	
			int nowLength = 0 ;
			Row row = sheet.getRow(1);
			
			Cell cell = row.getCell(1); // 获取表名称
			String tableName = row.getCell(1).getStringCellValue();
			sql.append("`" + tableName + "` (");
			
			cell = row.getCell(3); // 获取模板资源名称
			String modelZyname  = row.getCell(3).getStringCellValue();
			
			cell = row.getCell(5); // 获取版本号
			String versionStr  = row.getCell(5).getStringCellValue();
			
			cell = row.getCell(7); // 获取版本号
			String model_zyid  = row.getCell(7).getStringCellValue();
			
			String  model_id = "" ;
			//判断模板发布  并且属于登录人的部门
			String sqlModel = " select c.* from model_data c left join model_data_publish d  on  c.model_id = d.model_id left join model_data_dept mdd on c.model_id = mdd.model_id     where  c.model_info_table_name = ? and c.model_version = ? and c.model_zyid = ? and c.is_del = 'N'   and d.publish_status = 'Y' and mdd.dept_id = ? ";
			List<Map<String, Object>> listModel = jdbcTemplate.queryForList(sqlModel,  new Object[] { tableName, versionStr, model_zyid , user.getDeptid()  });
			if(listModel!=null && listModel.size()>0){
				model_id = StringTool.null2Empty(listModel.get(0).get("model_id"));
			}else{
				error.append("模板为未发布状态 或 版本已更新, 账号无对应模板录入权限,模板资源编号:"+model_zyid);
				resultMap.put("error", error.toString());
				return resultMap;
			}
			
			//获取模板字段设置 的主键字段
			String sqlExcelColumIsUUID = "SELECT * FROM model_sys_export WHERE model_id = ? and is_uuid = 'Y'  ";
			List<Map<String, Object>> listExcelColumIsUUID = jdbcTemplate.queryForList(sqlExcelColumIsUUID,  new Object[] { model_id });
			for (int i = 0; i < listExcelColumIsUUID.size(); i++) {
//				if(i==(listExcelColumIsUUID.size()-1)){
//					sqlOtherColumn.append("`"+listExcelColumIsUUID.get(i).get("col_name")+"` ");
//					batcharg.append("?");
//				}else{
					sqlOtherColumn.append("`"+listExcelColumIsUUID.get(i).get("col_name")+"`,");
					batcharg.append("?,");
//				}
			}
			//获取模板字段设置 的非主键字段
			String sqlExcelColum = "SELECT * FROM model_sys_export WHERE model_id = ? and is_uuid = 'N'  ";
			List<Map<String, Object>> listExcelColum = jdbcTemplate.queryForList(sqlExcelColum,  new Object[] { model_id });
			int excelColumLength = listExcelColum.size();
			// 获取 第三行 表字段
			row = sheet.getRow(2);
			//获取Excel中的列顺序
			Map<Integer, String> columExcelMap = new HashMap<Integer, String>();
			for (int i = 0; i < excelColumLength; i++) {
				Cell cell2 = row.getCell(i);
				columExcelMap.put(i, cell2.getStringCellValue());
				if(i==(excelColumLength-1)){
					sqlOtherColumn.append("`"+cell2.getStringCellValue()+"`) VALUES (");
					batcharg.append("?)");
				}else{
					sqlOtherColumn.append("`"+cell2.getStringCellValue()+"`,");
					batcharg.append("?,");
				}
			}
			
			sql.append(sqlOtherColumn.toString());
			sql.append(batcharg.toString());
			
			//获取模板表的  原始字段信息
			String sqlColumOriginal = " select * from information_schema.columns where  TABLE_SCHEMA = (select database())  and  table_name = ? ; ";
			List<Map<String, Object>> listColumOriginal = jdbcTemplate_modelImput.queryForList(sqlColumOriginal,  new Object[] { tableName });
			Map<Object, Object> columOriginalMap = new HashMap<Object, Object>();// 校验
			for (Map<String, Object> map : listColumOriginal) {
				columOriginalMap.put( map.get("COLUMN_NAME"), map);  //{"int_6":map}
			}
			
			//校验字段
			// 循环行//进行逐行校验//这里转换成实际行数//原先角标从0,现在从1
			boolean nullBreak = false;//后面加的功能, 行数据全为空的状态
			List<Object[]> batchArgs = new ArrayList<Object[]>();
			// 声明一个batchaArgs数据list
			// 声明一个beach inset常量
			int beachNum = 0;
			int datanum = 0; // 插入计数
			for (int i = 4; i <= maxRowLength; i++) {
				int emptyNum = 0;
				row = sheet.getRow( i ); // 获取当前行 按照从0开始排列的
				if (row == null) {
					break;
				}
	
					// 循环列当  列为空时结束循环
					for (int j = 0; j < excelColumLength; j++) {
						cell = row.getCell(j);
						String cellValue = "";
						int type = 3;
						if (cell != null)  type = cell.getCellType();
						cellValue = getCellValue(type, cell);
						
						// 进行列为空计数
						if (cellValue == null || cellValue.isEmpty())  	emptyNum++;
						if (emptyNum == excelColumLength) {
							nullBreak = true;
							break;
						}  
					}
					nowLength = i ;
					if (nullBreak)  break; //行为空跳出不进行下面的校验
					Object[] params = new Object[excelColumLength+listExcelColumIsUUID.size()];
					for (int y = 0; y < listExcelColumIsUUID.size(); y++) {
							params[y]  = StringTool.getUUID();
					}
					
					// 循环列当  列为空时结束循环
					for (int j = 0; j < excelColumLength; j++) {
						cell = row.getCell(j);
						String cellValue = "";
						int type = 3;
						if (cell != null)  type = cell.getCellType();
						cellValue = getCellValue(type, cell);
						
						params[j+listExcelColumIsUUID.size()] = ParityController.parityColumInsert(j,cellValue, (Map)columOriginalMap.get(columExcelMap.get(j)));
					}
					beachNum ++ ;
					batchArgs.add(params);
					// 每1000条数据执行一次新增
					if (beachNum == 1000) {
						jdbcTemplate_modelImput.batchUpdate(sql.toString(), batchArgs);
						datanum += 1000;
						beachNum = 0;
						batchArgs = new ArrayList<Object[]>();
					}
			}
			
						// 最后不足1000条的数据进行批量插入
						if (batchArgs.size() > 0) {
							jdbcTemplate_modelImput.batchUpdate(sql.toString(), batchArgs);
							datanum += batchArgs.size();
						}
						//插入数据后写入数据条数
						if (datanum > 0) {
							modelImputRepository.updateImput(id, String.valueOf(datanum));
						}else if ("1".equals(status)){
							modelImputRepository.updateStatus(id, "3");
						}
			
			if(  "2".equals(status)  ){
				error.append("数据未通过校验,  不可导入");
			}else if(  "3".equals(status)  ){
				error.append("数据不可重复导入");
			}
			
		resultMap.put("error", error.toString());
		return resultMap;
	}
	
	
}
