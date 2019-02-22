package cn.com.taiji.platform.web.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.hssf.util.HSSFColor;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.taiji.gxwz.web.BaseAction;
import cn.com.taiji.platform.service.ModelExportService;
import cn.com.taiji.util.tools.lang.StringTool;



/** 
* 模板 之 导出功能    
* @ClassName:  ModelExportController
* @author ranxing
* @date 2019年1月31日 下午4:01:02
*/
@Controller
@RequestMapping(value = "/platform/export")
public class ModelExportController extends BaseAction {
	private String salt = RandomStringUtils.randomAscii(20);
	
	@Inject 
	ModelExportService modelExportService;
	
	@RequestMapping(value="download{type}" , method = { RequestMethod.POST})
	public void  modelExptTitile (HttpServletRequest request,  HttpServletResponse response ,
			@PathVariable("type")String type,
			@RequestParam(value="model_id")String model_id  ){
		try {
		//获取类型
			// 开始生成Excel文件
			// 创建Excel文件
			HSSFWorkbook wb = new HSSFWorkbook();
			//获取查询条件
			///String insurerName=java.net.URLDecoder.decode(insurerName,"UTF-8"); //jsp 转到后台乱码，此转换是jsp转换格式后，后台解析
			Map<String,Object> searchParameters =  new HashMap<String,Object>();
			Map<String, Object> map = modelExportService.getExprotTitle(model_id, salt);
			//生成Excel
			this.createSocialSheet(wb, request,map,map);
//			int maxSingleSheet = 5005;
//			if(map.get("operationQuestionList")!=null ){
//				List<Object[]>  list = (List) map.get("operationQuestionList") ;
//				int sheetCount = (int) Math.ceil(list.size()/maxSingleSheet)+1;
//				for (int i = 1; i <= sheetCount ; i++) {
//					
//					List<Object[]>  listObj = list.subList((i-1)*maxSingleSheet, i*maxSingleSheet > list.size() ? list.size() : i*maxSingleSheet );
//					
//					this.createSocialSheet(wb, request,""+i,listObj);
//				}
//			}
			// 导出
			String filedisplay = map.get("model_name")+".xls";
			//filedisplay = URLEncoder.encode(filedisplay, "UTF-8");
			filedisplay = new String(filedisplay.getBytes(),"iso-8859-1" );
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-download");
			response.addHeader("Content-Disposition", "attachment;filename=" + filedisplay);

			OutputStream out = null;
			try {
				out = response.getOutputStream();
				wb.write(out);
				//System.out.println("EXCEL导出成功!");
			} catch (FileNotFoundException e) {
				//System.out.println("导出失败~~~~~~~~~~~~~~~~~~~!");
				e.printStackTrace();
			} finally {
				if (null != out) {
					try {
						out.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	

	/** 
	* @Title: createSocialSheet 
	* @Description: 生成列表Excel 
	* @param @param wb
	* @param @param request
	* @return void    返回类型 
	* @throws 
	*/
	@SuppressWarnings({ "unchecked", "deprecation" })
	private void createSocialSheet(HSSFWorkbook wb, HttpServletRequest request, Map<String, Object> searchParameters, Map<String, Object> map) {
		//明细
		HSSFSheet sheet = wb.createSheet("模板(有效数据区域空行以下将无效)");//选项卡
		// 生成一个样式(用于单元格)
		HSSFDataFormat format = wb.createDataFormat();
		
		HSSFCellStyle style = wb.createCellStyle();
		style.setDataFormat(format.getFormat("@"));
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// 生成一个字体(用于单元格)
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints((short) 12);
		// 把字体应用到当前的样式
		style.setFont(font);
		
		//2 3 4 行样式
		HSSFCellStyle style4 = wb.createCellStyle();
		style4.setDataFormat(format.getFormat("@"));
		style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style4.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置背景色
		style4.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		// 生成一个字体(用于单元格)
		HSSFFont font4 = wb.createFont();
		font4.setFontHeightInPoints((short) 12);
		// 把字体应用到当前的样式
		style.setFont(font4);
		
		// 生成一个样式(用于标题)
		HSSFCellStyle style2 = wb.createCellStyle();
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		// 生成一个字体(用于标题)
		HSSFFont font2 = wb.createFont();
		font2.setFontHeightInPoints((short) 18);
		// 把字体应用到当前的样式
		style2.setFont(font2);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);//设置背景色
		style2.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		
		// 生成一个样式(用于标题)
//		HSSFCellStyle style3 = wb.createCellStyle();
//		style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
//		style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
//		style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
//		style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
//		style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
//		// 生成一个字体(用于标题)
//		HSSFFont font3 = wb.createFont();
//		font3.setColor(HSSFColor.RED.index);
//		font3.setFontHeightInPoints((short) 11);
//		// 把字体应用到当前的样式
//		style3.setFont(font3);
		//Excel列宽
		List<Map<String,Object>> columnList  = (List<Map<String,Object>>) map.get("titleList");
		StringBuffer columns = new StringBuffer() ;
		for (Map<String,Object> columnSys : columnList) {
			columns.append(columnSys.get("col_name")+",")	;
		}
		String[] column = columns.toString().split(",");
		int allColumnsNum = column.length;
		for (int i = 0; i < allColumnsNum; i++) {
			sheet.setColumnWidth(i, column[i].getBytes().length * 256);
		}
		
		 allColumnsNum = column.length>=1?column.length:8;
		// 在sheet里增加合并单元格
		// CellRangeAddress构造函数的4个参数：要合并的单元格的起始行号，终止行号，起始列号，终止列号，从0开始
		CellRangeAddress cra = new CellRangeAddress(0, 0, 0,allColumnsNum>8?allColumnsNum-1:7);   //第一行  模板名字
		sheet.addMergedRegion(cra);
		
		// 创建一行
		HSSFRow row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue((String)searchParameters.get("model_name"));
		cell.setCellStyle(style2);
		
		// 创建二行
		row = sheet.createRow(1);
		 
		cell = row.createCell(0);
		cell.setCellValue("信息类编目表名:");
		sheet.setColumnWidth(0,"信息类编目表名".getBytes().length * 256);
		cell.setCellStyle(style4);
		cell = row.createCell(1);
		cell.setCellValue((String)searchParameters.get("model_info_table_name"));
		sheet.setColumnWidth(1,"数据表名".getBytes().length * 256);
		cell.setCellStyle(style4);
		
		cell = row.createCell(2);
		cell.setCellValue("模版资源名称:");
		sheet.setColumnWidth(2,"模版资源名称".getBytes().length * 256);
		cell.setCellStyle(style4);
		cell = row.createCell(3);
		cell.setCellValue((String)searchParameters.get("model_zyname"));
		sheet.setColumnWidth(3,"模版资源名称：".getBytes().length * 256);
		cell.setCellStyle(style4);
		
		cell = row.createCell(4);
		cell.setCellValue("版本：");
		sheet.setColumnWidth(4,"版本".getBytes().length * 256);
		cell.setCellStyle(style4);
		cell = row.createCell(5);
		cell.setCellValue( searchParameters.get("model_version")+"");
		sheet.setColumnWidth(5,"版本".getBytes().length * 256);
		cell.setCellStyle(style4);	
		
		cell = row.createCell(6);
		cell.setCellValue("模板资源编号：");
		sheet.setColumnWidth(6,"模板资源编号".getBytes().length * 256);
		cell.setCellStyle(style4);
		cell = row.createCell(7);
		cell.setCellValue((String)searchParameters.get("model_zyid"));
		sheet.setColumnWidth(7,"模板资源编号".getBytes().length * 256);
		cell.setCellStyle(style4);		
			
		// 第三行
		row = sheet.createRow(2);
		for (int x=0; x< columnList.size() ;x++) {
			cell = row.createCell(x);
			cell.setCellValue( StringTool.null2Empty(columnList.get(x).get("col_name") ));
			cell.setCellStyle(style4);
		}
		
		// 第四行 
		row = sheet.createRow(3);
		for (int x=0; x< columnList.size() ;x++) {
			cell = row.createCell(x);
			
			String title =  StringTool.null2Empty(columnList.get(x).get("col_comment") );
			if(title == null || title.isEmpty()){
				title = StringTool.null2Empty(columnList.get(x).get("col_name") );
			}
			
			cell.setCellValue(  title  );
			cell.setCellStyle(style4);
		}
		
        // 给第 2 3 4行划线
		for (int i = 1; i <=3; i++) {
			row = HSSFCellUtil.getRow(i, sheet);
			for (int j = 0; j < allColumnsNum; j++) {
				cell = HSSFCellUtil.getCell(row, (short) j);
				cell.setCellStyle(style4);
			}
		}
		
		//其他
		for (int i = 4; i <=5; i++) {
			row = HSSFCellUtil.getRow(i, sheet);
			for (int j = 0; j < allColumnsNum; j++) {
				cell = HSSFCellUtil.getCell(row, (short) j);
				cell.setCellStyle(style);
			}
		}
		
	}
	
}
