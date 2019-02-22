package cn.com.taiji.platform.web.model;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
//import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.taiji.util.tools.date.DateUtil;
import cn.com.taiji.util.tools.lang.StringTool;

/** 
* @ClassName: ParityController 
* @Description: 模板上传  写入   校验 
* @author ranxing
* @date 2017年12月8日 上午11:20:03 
*  
*/
public class ParityController {
	
	//mysql字段名称映射
		public final static String  
			columnType [] = {
						"varchar",   //	varchar(50)
						"char",		//	char(255)
						"text",
						"longtext",
						
						//无默认值
						"longblob",	 //	LongBlob 最大 4G	
						"blob",		 //	Blob 最大 65K		
						"tinyblob",	 //	TinyBlob 最大 255
						"mediumblob",//	MediumBlob 最大 16M
						
						"time",  	 //time(6) 18:30:57.000000  //time  18:30:57  
						"date",		 //date(6) 2019-01-15.000000  //date  2019-01-15 
						"datetime",  //datetime(6) 2019-01-15 18:33:06  //datetime 2019-01-15 18:33:06
						"timestamp", //	timestamp(6) 2019-01-15 18:30:59.000000 //timestamp  2019-01-15 18:30:59
						
						//
						"int",		 //不设置或0,默认11位   //判断时候使用括号筛选   //	int(8)
						"float",	 //不设置或0,默认12位  //判断时候使用括号筛选   //  float(2,0)  //float
						"double",	 //不设置或0,默认22位  //判断时候使用括号筛选   //  double(2,0)  //double
						"bigint"	 //不设置或0,默认20位  //判断时候使用括号筛选  //	bigint(16)
							};
		public static void main(String args[]) {
			String COLUMN_TYPE = "time(1)";//'字段类型'
			int leftNum = 0;
			int rightNum = 0 ;
			if(COLUMN_TYPE.indexOf("(")!=-1){
				System.out.println(COLUMN_TYPE.indexOf("("));
				System.out.println(COLUMN_TYPE.substring(0));
				COLUMN_TYPE =	COLUMN_TYPE.substring( COLUMN_TYPE.indexOf("(")+1 , COLUMN_TYPE.indexOf(")") );
				
				String[] intArray = COLUMN_TYPE.split(",");
				if(intArray.length>0 && intArray.length == 1)  leftNum = StringTool.String2Int(intArray[0]);
				if(intArray.length>0 && intArray.length == 2)  rightNum = StringTool.String2Int(intArray[1]);
				 
			}
			
			
			System.out.println( new BigDecimal("12345678901").compareTo(new BigDecimal("2147483647"))  ); //1
			System.out.println( new BigDecimal("2147483647").compareTo(new BigDecimal("2147483647"))  ); //1
			System.out.println( new BigDecimal("-2147483647").compareTo(new BigDecimal("2147483647"))  ); //1
		}
	/**
	 * @param j 列编号从0开始
	 * @param cellValue  当前列值
	 * @param columOriginalMap  当前列的原始规则
	 * @return methods
	 */
	public static String parityColum(int  j , String cellValue, Map<Object, Object> columOriginalMap) {
		
		j = j+1;
		String IS_NULLABLE = StringTool.null2Empty(columOriginalMap.get("IS_NULLABLE"));//字段是否可以是NULL
		String DATA_TYPE =   StringTool.null2Empty(columOriginalMap.get("DATA_TYPE"));//字段数据类型
		String COLUMN_TYPE = StringTool.null2Empty(columOriginalMap.get("COLUMN_TYPE"));//'字段类型'
		String COLUMN_TYPE_ys = COLUMN_TYPE;
		
		int CHARACTER_MAXIMUM_LENGTH = StringTool.String2Int(columOriginalMap.get("CHARACTER_MAXIMUM_LENGTH"));//字段的最大字符数
		
		//数字精度
		String precisionStr =  StringTool.null2Empty( columOriginalMap.get("NUMERIC_PRECISION"));
		String scaleStr     =  StringTool.null2Empty( columOriginalMap.get("NUMERIC_SCALE")) ;
		
		int leftNum = -1;
		int rightNum = -1 ;
		if(COLUMN_TYPE.indexOf("(")!=-1){
			COLUMN_TYPE =	COLUMN_TYPE.substring( COLUMN_TYPE.indexOf("(")+1 , COLUMN_TYPE.indexOf(")") );
			String[] intArray = COLUMN_TYPE.split(",");
			if(intArray.length>0 && intArray.length == 1)  leftNum = StringTool.String2Int(intArray[0]);
			if(intArray.length>0 && intArray.length == 2)  rightNum = StringTool.String2Int(intArray[1]);
		}
		
		
		if("NO".equals(IS_NULLABLE) ){
			if(cellValue==null || cellValue.toString().isEmpty()){
				return  j + "列不能为空 ";
			}
		}else{
			//可以为空统一处理
			if(cellValue==null || cellValue.toString().isEmpty()){
				return   "";
			} 
		}
		
		String errorStrLengt2long =  j + "列长度过长应该小于"+CHARACTER_MAXIMUM_LENGTH+"个字符数 \t \t  |" ;
		Boolean flag = false ; 
		try {
		 switch (DATA_TYPE) {
			case "varchar": 
				    int ULen = cellValue.getBytes("utf-8").length;
				    return   ULen <= CHARACTER_MAXIMUM_LENGTH?"": errorStrLengt2long;
			case "char": 
				 return   cellValue.length() <= CHARACTER_MAXIMUM_LENGTH?"": errorStrLengt2long;
			case "text": 
				 return   cellValue.length() <= CHARACTER_MAXIMUM_LENGTH?"": errorStrLengt2long;
			case "longtext": 
				 return   cellValue.length() <= CHARACTER_MAXIMUM_LENGTH?"": errorStrLengt2long;
				 
			case "longblob":	 //	LongBlob 最大 4G	
			case "blob":		 //	Blob 最大 65K		
			case "tinyblob":	 //	TinyBlob 最大 255
			case "mediumblob"://	MediumBlob 最大 16M
				return j+"列 暂不开放Blob类型字段  的 文档写入操作, 大文本类型请更换为longtext字段,"+COLUMN_TYPE_ys+" \t \t |";
				
			case "time":  	 //time(6) 18:30:57.000000  //time  18:30:57  
			case "datetime":  //datetime(6) 2019-01-15 18:33:06  //datetime 2019-01-15 18:33:06
			case "timestamp": //	timestamp(6) 2019-01-15 18:30:59.000000 //timestamp  2019-01-15 18:30:59
				int pointRightLength = 0;
				 String pattern_datetime = "^\\d{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}( \\d{1,2}:\\d{1,2}:\\d{1,2}){0,1}$";  
				//存在毫秒数
				if(leftNum>0){
					if(cellValue !=null && cellValue.indexOf(".")!=-1)
					pointRightLength = cellValue.length() - (cellValue.indexOf(".")+1);
					pattern_datetime = "^\\d{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}( \\d{1,2}:\\d{1,2}:\\d{1,2}){0,1}[.]{0,1}\\d{0,"+leftNum+"}$";
					if(pointRightLength > leftNum){
						return j+"列日期格式毫秒数过长,应为"+leftNum+",实际为"+pointRightLength+","+COLUMN_TYPE_ys+" \t \t |";
					}
				} 
				   flag = Pattern.compile(pattern_datetime).matcher(cellValue).matches();
				   if(!flag){
	       		    	return j +"列日期格式不对,"+COLUMN_TYPE_ys+" \t \t |";
	       		    }
				   return "";
			case "date":	 //date  2019-01-15 
       		    String pattern_date = "\\d{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}$";
       		    flag = Pattern.compile(pattern_date).matcher(cellValue).matches();
       		    if(!flag){
       		    	return j +"列日期格式不对 \t \t |";
       		    }
				return "";			
				 
			case "int":	 //不设置或0,默认11位   //判断时候使用括号筛选   //	int(8)  带符号的范围是-2147483648到2147483647。无符号的范围是0到4294967295。
			    String pattern_int = "^-?[0-9]\\d*$";
       		    flag = Pattern.compile(pattern_int).matcher(cellValue).matches();
       		    if(!flag){
       		    	return j +"列 int类型数字格式不对 \t \t |";
       		    }
       		    if(new BigDecimal(cellValue).compareTo(new BigDecimal("2147483647")) == 1   ){
       		    	return j +"列 int类型数字格式不对,应小于2147483647 \t \t |";
       		    }
				return "";		
			case "bigint":	 //不设置或0,默认20位  //判断时候使用括号筛选  //	bigint(16)
			case "float":	 //不设置或0,默认12位  //判断时候使用括号筛选   //  float(2,0)  //float  12345700000
			case "double":	 //不设置或0,默认22位  //判断时候使用括号筛选   //  double(2,0)  //double
//				  leftNum = 0;
//				  rightNum = 0 ;
 
				//NUMERIC_PRECISION  NUMERIC_SCALE
				    String	pattern  = "";
			        if( !precisionStr.isEmpty() &&  scaleStr.isEmpty() ){
			    	    pattern = "-?\\d{0,"+Integer.parseInt(precisionStr)+"}";
			        	return   Pattern.compile(pattern).matcher(cellValue).matches()?"":j+"列非有效数字类型或长度过长,"+COLUMN_TYPE_ys+" \t \t |";
			        }
			
			        if("0".equals(scaleStr) ){
			            pattern = "-?\\d{0,"+Integer.parseInt(precisionStr)+"}";
			        	return   Pattern.compile(pattern).matcher(cellValue).matches()?"": j+"列非有效数字类型或长度过长,"+COLUMN_TYPE_ys+" \t \t |";
			        }else{
			        	   flag = false ; 
			        	 
			        	 int precisionInt = Integer.parseInt(precisionStr);
			        	 int scaleInt = Integer.parseInt(scaleStr);
			        	 
			        	 if(precisionInt>scaleInt){
			        		 //-?(0+\d{0,4}[.](\d){0,2})|(0+\d{0,4})
			        		 pattern = "-?(0*\\d{0,"+(precisionInt-scaleInt)+"}[.](\\d){0,"+(scaleInt)+"})|(0*\\d{0,"+(precisionInt-scaleInt)+"})";
				        	 flag = Pattern.compile(pattern).matcher(cellValue).matches();
				        	// return flag?"":j+"列非有效数字类型或长度过长";
			        	 }else if(precisionInt==scaleInt){
			        		 //-?0+.?\d{0,3}
			        		 pattern = "-?0+.?(\\d){0,"+(scaleInt)+"}";
				        	 flag = Pattern.compile(pattern).matcher(cellValue).matches();
			        	 }else{
			        		//-?(0+.?[0]{3}(\d){0,2})|(0+.?0+)|0
			        		 pattern = "-?(0+.?[0]{"+(scaleInt - precisionInt)+"}(\\d){0,"+(precisionInt)+"})|(0+.?0+)|0";
				        	 flag = Pattern.compile(pattern).matcher(cellValue).matches();
			        	 }
			        	 
						return flag?"":j+"列非有效数字类型或长度过长,"+COLUMN_TYPE_ys+" \t \t |";
			        }
			default:
				 int ULen2=0;
					try {
						ULen2 = cellValue.getBytes("utf-8").length;
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
			    return   ULen2 <= CHARACTER_MAXIMUM_LENGTH?"":j+"列长度过长,"+COLUMN_TYPE_ys+" \t \t |";
				
		 }
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		return null;
	}
	
	
	/**
	 * 错误验证
	 * @param j   当前列数
	 * @param cellValue   当前单元格值
	 * @param lengthNum		字段长度
	 * @param nullableStr   是否为空state
	 * @param datatypeStr   字段类型state
	 * @param precisionStr  精度
	 * @param scaleStr		长度
	 * @return
	 */
	public static String parityLenth(int j, String cellValue, int lengthNum, String nullableStr, String datatypeStr, String precisionStr, String scaleStr){
		
		String pattern ;
		String datatypeStrFormat = datatypeStr;
		if(datatypeStr.startsWith("TIMESTAMP")){
			datatypeStrFormat = "TIMESTAMP";
		   }
		
		
		if("N".equals(nullableStr) ){
			//不能为空
			if(cellValue==null || cellValue.toString().isEmpty()){
				return  (j+1)+"列不能为空 ";
			}
			
		}else{
			//可以为空统一处理
			if(cellValue==null || cellValue.toString().isEmpty()){
				return   "";
			} 
		}
		 switch (datatypeStrFormat) {
			case "VARCHAR2": 
			case "NVARCHAR2":
				 int ULen=0;
				 if(j==170){
					 System.out.println(j);
				 }
					try {
						ULen = cellValue.getBytes("utf-8").length;
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
					return   ULen <= lengthNum?"":(j+1)+"列长度过长 ";
			case "CLOB": 		
				return   "";
			case "NUMBER":	
				    if(precisionStr.isEmpty()){
				    	pattern = "-?\\d{0,"+(lengthNum)+"}";
				        return   Pattern.compile(pattern).matcher(cellValue).matches()?"":(j+1)+"列非有效数字类型或长度过长 ";
				    }
				
			        if( !precisionStr.isEmpty() &&  scaleStr.isEmpty() ){
			    	    pattern = "-?\\d{0,"+Integer.parseInt(precisionStr)+"}";
			        	return   Pattern.compile(pattern).matcher(cellValue).matches()?"":(j+1)+"列非有效数字类型或长度过长 ";
			        }
			
			        if("0".equals(scaleStr) ){
			            pattern = "-?\\d{0,"+Integer.parseInt(precisionStr)+"}";
			        	return   Pattern.compile(pattern).matcher(cellValue).matches()?"":(j+1)+"列非有效数字类型或长度过长 ";
			        }else{
			        	 Boolean flag = false ; 
			        	 
			        	 int precisionInt = Integer.parseInt(precisionStr);
			        	 int scaleInt = Integer.parseInt(scaleStr);
			        	 
			        	 if(precisionInt>scaleInt){
			        		 //-?(0+\d{0,4}[.](\d){0,2})|(0+\d{0,4})
			        		 pattern = "-?(0*\\d{0,"+(precisionInt-scaleInt)+"}[.](\\d){0,"+(scaleInt)+"})|(0*\\d{0,"+(precisionInt-scaleInt)+"})";
				        	 flag = Pattern.compile(pattern).matcher(cellValue).matches();
				        	// return flag?"":(j+1)+"列非有效数字类型或长度过长";
			        	 }else if(precisionInt==scaleInt){
			        		 //-?0+.?\d{0,3}
			        		 pattern = "-?0+.?(\\d){0,"+(scaleInt)+"}";
				        	 flag = Pattern.compile(pattern).matcher(cellValue).matches();
			        	 }else{
			        		//-?(0+.?[0]{3}(\d){0,2})|(0+.?0+)|0
			        		 pattern = "-?(0+.?[0]{"+(scaleInt - precisionInt)+"}(\\d){0,"+(precisionInt)+"})|(0+.?0+)|0";
				        	 flag = Pattern.compile(pattern).matcher(cellValue).matches();
			        	 }
			        	 
						return flag?"":(j+1)+"列非有效数字类型或长度过长";
			        }
				        
			case "DATE":
				String  strs = cellValue+"";
				Boolean flag = false ; 
				String pasStr = strs.trim();
       		    pattern = "[\\d]{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}[^x]*";
       		    flag = Pattern.compile(pattern).matcher(pasStr).matches();
       		    if(!flag){
       		    	return (j+1)+"列日期格式不对 ";
       		    }
				
				
				String [] mh = cellValue.split(":");
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
				if(mh.length==3){
					sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				}
				try {
					sf.setLenient(false);
					sf.parse(cellValue.replaceAll("/", "-"));
				} catch (ParseException e) {
					return (j+1)+"列日期格式不对 ";
				}
				
					return "";			
			case "TIMESTAMP":
				strs = cellValue+"";
				
			    flag = false ; 
			    pasStr = strs.trim();
       		    pattern = "[\\d]{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}[^x]*";
       		    flag = Pattern.compile(pattern).matcher(pasStr).matches();
       		    if(!flag){
       		    	return (j+1)+"列日期格式不对 ";
       		    }
       		 
				mh = strs.split(":");
				int  x = strs.lastIndexOf(":");
				if(mh.length==4 || (strs.indexOf(".")!=-1)  ){
					if(mh.length==4 ){
						strs  =	strs.substring(0, x)+strs.substring(x, strs.length()).replace(":", ".");
					}
					int y = datatypeStr.indexOf("(");
					int yy = strs.indexOf(".");
					int z = 0 ;
					int zz = 0 ;
					if(y>-1){
						 z = Integer.parseInt(datatypeStr.substring(y+1,y+2));
						 zz =  strs.substring(yy+1, strs.length()).length();
						 if(z<zz)
						 return (j+1)+"列日期格式不对 ";
					}
					try {
						 Timestamp.valueOf(strs);  
						return "";
					} catch (Exception e) {
						e.printStackTrace();
						return (j+1)+"列日期格式不对 ";
						
					}
				} 
				
				 sf = new SimpleDateFormat("yyyy-MM-dd");
				if(x==-1){
					try {
						sf.setLenient(false);
						sf.parse(cellValue.replace("/", "-"));
						return "";
					} catch (ParseException e) {
						e.printStackTrace();
						return (j+1)+"列日期格式不对 ";
					}
				}
				
				sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					sf.setLenient(false);
					sf.parse(cellValue.replace("/", "-"));
					return "";
				} catch (ParseException e) {
					e.printStackTrace();
					return (j+1)+"列日期格式不对 ";
				}
				
			default:
				 if(j==170){
					 System.out.println(j);
				 }
				 int ULen2=0;
					try {
						ULen2 = cellValue.getBytes("utf-8").length;
					} catch (UnsupportedEncodingException e1) {
						e1.printStackTrace();
					}
			    return   ULen2 <= lengthNum?"":(j+1)+"列长度过长 ";
				//return cellValue.length() < lengthNum?"":(j+1)+"列长度过长 ";
		}
		
	}
	

	
	/**
	 * @param j
	 * @param cellValue
	 * @param map
	 * @return methods
	 */
	public static Object parityColumInsert(int j, String cellValue, Map columOriginalMap) {
		String DATA_TYPE = StringTool.null2Empty(columOriginalMap.get("DATA_TYPE"));//字段数据类型
		String COLUMN_TYPE = StringTool.null2Empty(columOriginalMap.get("COLUMN_TYPE"));//'字段类型'
		
		int leftNum = -1;
		if(COLUMN_TYPE.indexOf("(")!=-1){
			COLUMN_TYPE =	COLUMN_TYPE.substring( COLUMN_TYPE.indexOf("(")+1 , COLUMN_TYPE.indexOf(")") );
			String[] intArray = COLUMN_TYPE.split(",");
			if(intArray.length>0 && intArray.length == 1)  leftNum = StringTool.String2Int(intArray[0]);
		}
		
		try {
		 switch (DATA_TYPE) {
			case "varchar": 
				 return  cellValue;
			case "char": 
				 return   cellValue;
			case "text": 
				 return   cellValue;
			case "longtext": 
				 return   cellValue;
			case "longblob":	 //	LongBlob 最大 4G	
			case "blob":		 //	Blob 最大 65K		
			case "tinyblob":	 //	TinyBlob 最大 255
			case "mediumblob"://	MediumBlob 最大 16M
				return "";
				
			case "time":  	 //time(6) 18:30:57.000000  //time  18:30:57  
			case "datetime":  //datetime(6) 2019-01-15 18:33:06  //datetime 2019-01-15 18:33:06
			case "timestamp": //	timestamp(6) 2019-01-15 18:30:59.000000 //timestamp  2019-01-15 18:30:59
				int pointRightLength = 0;
				//存在毫秒数
				if(leftNum>0){
					if(cellValue !=null && cellValue.indexOf(".")!=-1)
					pointRightLength = cellValue.length() - (cellValue.indexOf(".")+1);
					if(pointRightLength > leftNum){
						Timestamp ts = Timestamp.valueOf(cellValue);  
						return ts;
					}
				} 
				if(cellValue.indexOf(":")!=-1){
					return DateUtil.String2Date(cellValue.replaceAll("/", "-"), "yyyy-MM-dd HH:mm:ss");
				}else{
					return DateUtil.String2Date(cellValue.replaceAll("/", "-"), "yyyy-MM-dd");
				}
			case "date":	 //date  2019-01-15 
       			return DateUtil.String2Date(cellValue.replaceAll("/", "-"), "yyyy-MM-dd");	
			case "int":	 //不设置或0,默认11位   //判断时候使用括号筛选   //	int(8)  带符号的范围是-2147483648到2147483647。无符号的范围是0到4294967295。
				return Integer.valueOf(cellValue);		
			case "bigint":	 //不设置或0,默认20位  //判断时候使用括号筛选  //	bigint(16)
				return new BigDecimal(cellValue);
			case "float":	 //不设置或0,默认12位  //判断时候使用括号筛选   //  float(2,0)  //float  12345700000
				 return Float.valueOf( cellValue);
			case "double":	 //不设置或0,默认22位  //判断时候使用括号筛选   //  double(2,0)  //double
				 return Double.valueOf( cellValue);
			default:
				 return cellValue;
				
		 }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return cellValue;
	}
	
	
	
	/**
	 * 插入数据时候对数据处理
	 * @param j
	 * @param cellValue
	 * @param lengthNum
	 * @param nullableStr
	 * @param datatypeStr
	 * @param precisionStr
	 * @param scaleStr
	 * @return
	 */
	public static Object parityData(int j, Object cellValue, int lengthNum, String nullableStr, String datatypeStr, String precisionStr, String scaleStr){
		
		//针对Timestamp(*)类型进行处理
		String datatypeStrFormat = datatypeStr;
		if(datatypeStr.startsWith("TIMESTAMP")){
			datatypeStrFormat = "TIMESTAMP";
		   }
		
		if("N".equals(nullableStr) ){//不能为空
			if(cellValue==null || cellValue.toString().isEmpty()){
				try {
					throw new NullPointerException(  "  第"+(j+1)+"列字段不能为空");
				} catch (Exception e) {
					e.printStackTrace();
					return null;
				}	
			}
		}else {
			if(cellValue==null || cellValue.toString().isEmpty()){
					return null;
			}
		}
		
		 switch (datatypeStrFormat) {
			case "VARCHAR2":
			case "NVARCHAR2":
				return   cellValue;
			case "CLOB": 	
					return   cellValue;
			case "NUMBER":	
				return   cellValue;
			case "DATE":
				String [] mh = cellValue.toString().split(":");
				if(mh.length==3){
					return	DateUtil.String2Date(cellValue.toString().replaceAll("/", "-"), "yyyy-MM-dd HH:mm:ss");
				}
				return	DateUtil.String2Date(cellValue.toString().replaceAll("/", "-"), "yyyy-MM-dd");
			case "TIMESTAMP":
				String  strs = cellValue+"";
				  mh = strs.split(":");
				int  x = strs.lastIndexOf(":");
				if(mh.length==4 || (strs.indexOf(".")!=-1)  ){
					if(mh.length==4 ){
						strs  =	strs.substring(0, x)+strs.substring(x, strs.length()).replace(":", ".");
					}
					try {
						Timestamp ts = Timestamp.valueOf(strs);  
						return ts;
					} catch (Exception e) {
						e.printStackTrace();
					}
				} 
				if(x==-1){
				return DateUtil.String2Date(cellValue.toString().replaceAll("/", "-"), "yyyy-MM-dd");
				}
				return DateUtil.String2Date(cellValue.toString().replaceAll("/", "-"), "yyyy-MM-dd HH:mm:ss");
			default:
				return cellValue;
			}
		
	}
	
	
	
	
	
	/**
	 *  分组  错误校验
	 * @param j  行号
	 * @param cellValue  当前值
	 * @param fzjyMap  
	 * @return
	 */
	public static String  parityPaserColumFZJY(int j, String cellValue, Map<String, Object> fzjyMap){
		if(fzjyMap.containsKey(cellValue)){
			return "";
		}else{
			return (j+1)+"列无此值列表 ";
		}
	}
	
	/**
	 *  分组   获取转换值
	 * @param j  行号
	 * @param cellValue  当前值
	 * @param fzjyMap  分组校验值列表
	 * @return
	 */
	public static String  parityPaserColum(int j, String cellValue, Map<String, Object> fzjyMap){
		if(fzjyMap.containsKey(cellValue)){
			return  fzjyMap.get(cellValue).toString();
		}else{
			return null ;
		}
	}
	
}
