package cn.com.taiji.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/** 
* 非默认中国时间的转换类
* @ClassName:  DateLocalUtil
* @author ranxing
* @date 2018年11月6日 上午10:27:04
*/
public class DateLocalUtil {
	
	private static final String  deafultSimpleDateFormat = "yyyy-MM-dd HH:mm:ss";
	private static final String EMPTY = ""; 
	
	
	/**
	 * 默认时间转 strDate   eg：Tue Jul 12 00:00:00 GMT+08:00 2016  to  2016-07-12 00:00:00
	 * @param strDate 日期字符串
	 * @param num 日期格式编号
	 * 
	 * @return String ： yyyy-MM-dd HH:mm:ss
	 */
	public static String localeEnglishToDefualt(Object strDate ){
		
		 if(strDate==null || strDate.toString().isEmpty() ) 
			 return EMPTY;
		
	     SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH); //  s = "Tue Jul 12 00:00:00 GMT+08:00 2016";
	     Date date = new Date();
		try {
			
			date = sf1.parse(strDate.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	     SimpleDateFormat sf2 = new SimpleDateFormat(deafultSimpleDateFormat);
		return sf2.format(date);
	}
	
	/**
	 * 默认时间转 strDate   eg：Tue Jul 12 00:00:00 GMT+08:00 2016
	 * @param strDate 日期字符串
	 * @param num 日期格式编号
	 * <br>	   	switch (num) {
<br>		case 1:
<br>			return "yyyy-MM-dd HH:mm:ss";
<br>		case 11:
<br>			return "yyyy/MM/dd HH/mm/ss";
<br>		case 2:
<br>			return "yyyy-MM-dd";
<br>		case 22:
<br>			return "yyyy/MM/dd";
<br>
<br>		default:
<br>			return "yyyy-MM-dd HH:mm:ss";
<br>			
<br>		}
	 * @return
	 */
	public static String localeEnglishToFormat(String strDate , int num){
		
		 if(strDate==null || strDate.toString().isEmpty() ) 
			 return EMPTY;
		 
	     SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH); //  s = "Tue Jul 12 00:00:00 GMT+08:00 2016";
	     Date date = new Date();
		try {
			date = sf1.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
	     SimpleDateFormat sf2 = new SimpleDateFormat(formatType(num));
		return sf2.format(date);
	}
	
	public static void main(String[] args) {
		
		//System.out.println(localeEnglishToDefualt(new TIMESTAMP()));
		//System.out.println(localeEnglishToDefualt(dateTypeOf(new TIMESTAMP())));
		//1504843369544
	}
	
	
	
	
	public static String dateTypeOf(Object obj){
		return "" ;
	}
	
	/**
	 * format类型
	 * @param num
	 * @return string
	 * case 1:
			return "yyyy-MM-dd HH:mm:ss";
		case 11:
			return "yyyy/MM/dd HH/mm/ss";
		case 2:
			return "yyyy-MM-dd";
		case 22:
			return "yyyy/MM/dd";
	 */
	public static String formatType(int num){
		switch (num) {
		case 1:
			return "yyyy-MM-dd HH:mm:ss";
		case 11:
			return "yyyy/MM/dd HH/mm/ss";
		case 2:
			return "yyyy-MM-dd";
		case 22:
			return "yyyy/MM/dd";

		default:
			return deafultSimpleDateFormat;
			
		}
	}
	
	
	

}
