package cn.com.taiji.util.tools.lang;

import java.util.UUID;

import cn.com.taiji.util.tools.date.DateUtil;


/**
 * @author King_RX
 *  string type utils
 */
public class StringTool {
	
	 	private static final int INDEX_NOT_FOUND = -1;    
	    private static final String EMPTY = "";  
	    private static final int ZEROY = 0 ;  
	
    public static boolean isNull(String str) {  
        if (null == str || "".equals(str.trim()))  
            return true;  
        return false;  
    }
	
	public static String  null2Empty(Object obj){
		if( obj == null ){
			return EMPTY;
		}
		return obj.toString();
	}
	
	public static Object  obj2Empty(Object obj){
		if( obj == null ){
			return EMPTY;
		}
		return obj;
	}
	
	public static int  String2Int(Object obj){
		if( obj == null || obj.toString().isEmpty() ){
			return ZEROY;
		}
		double dd = Double.valueOf(obj.toString());
		return (int) dd ;
		//return Integer.parseInt(obj.toString());
	}
	
	public static int  String2MinusOne(Object obj){
		if( obj == null || obj.toString().isEmpty() ){
			return -1;
		}
		double dd = Double.valueOf(obj.toString());
		return (int) dd ;
		//return Integer.parseInt(obj.toString());
	}
	
	
	
	public static long String2long(Object obj) {
		if( obj == null || obj.toString().isEmpty() ){
			return ZEROY;
		}
		return Long.parseLong(obj.toString());
	}
	
	public static long longContrast(long one, long two){
		
		if(one > two){
			return one ;
		}
		
		return two ;
	}
	
	
	/**
	 * 
	* @Title: obj2TimestempLong 
	* @Description: TODO( String 2 long ) 
	* @param @param obj
	* @param @param formart if is null , default "yyyy-MM-dd HH:mm:ss"
	* @param @return    设定文件 
	* @return long    返回类型 
	* @throws
	 */
	public static long obj2TimestempLong(Object obj, String formart){
		if( obj == null || obj.toString().isEmpty() ){
			return ZEROY;
		}
		if(null==formart)
			formart = "yyyy-MM-dd HH:mm:ss" ;
		
		return DateUtil.String2Date( obj.toString() , formart ).getTime();
	}
	
	public static long longContrast(Object obj) {
		// TODO Auto-generated method stub
		if( obj == null || obj.toString().isEmpty() ){
			return ZEROY;
		}
		
		
		return 0;
	}
	
	
	public static String  getUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	
	
	
	
	public static void main(String[] args) {
		System.out.println(null2Empty("ss"));
		
	}

	

	
	
	

}
