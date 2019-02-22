package cn.com.taiji.util.tools.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 
 * @ClassName:  DateUtil   
 * @Description:TODO 关于获得日期以及相应转换方法的公用类
 * @author: zhongdd
 * @date:   2018年5月19日 上午8:31:18   
 *     
 *
 */
public class DateUtil {
	// 用来全局控制 上一周，本周，下一周的周数变化
	public static int weeks = 0;
	/**
	 * 一月最大天数
	 */
	public static int MaxDate;// 一月最大天数
	/**
	 * 一年最大天数
	 */
	public static int MaxYear;// 一年最大天数

	public final static String YYYY = "yyyy";
	public final static String YY = "yy";
	public final static String MM = "MM";
	public final static String DD = "dd";
	public final static String hh = "HH";
	public final static String mm = "mm";
	public final static String ss = "ss";

	public static String[] timeFormat = { YYYY, YY, MM, DD, hh, mm, ss };
	private static HashMap map;

	static {
		map = new HashMap();
		map.put(new Character('0'), "零");
		map.put(new Character('1'), "一");
		map.put(new Character('2'), "二");
		map.put(new Character('3'), "三");
		map.put(new Character('4'), "四");
		map.put(new Character('5'), "五");
		map.put(new Character('6'), "六");
		map.put(new Character('7'), "日");
	}

	// public static void main(String a[]) {
	/*
	 * Date d = getNowTime(); System.out.println(
	 * "-------------------------------------日期转换-----------------------------------------------------------------"
	 * ); Date d = getNowTime();
	 * System.out.println("当前日期转换成字符串类型（yyyy-MM-dd HH:mm:ss）："+getNowTime(1));
	 * System.out.println("当前日期转换成字符串类型（yyyy-MM-dd）："+getNowTime(2));
	 * System.out.println("当前日期转换成字符串类型（yyyyMMdd）："+getNowTime(3));
	 * System.out.println("当前日期转换成字符串类型（yyyyMMddHHmmss）："+getNowTime(4));
	 * System.out.println("当前日期根据格式转换："+getNowTime("yyyy-MM-dd"));
	 * System.out.println("日期转换成字符串形式（格式任意）："+Date2String(d,"[YY年MM月dd日]"));
	 * System.out.println("日期转换成字符串形式（格式任意）："+Date2String(d,1));
	 * System.out.println("日期转换成字符串形式（格式任意）："+Date2String(d,2));
	 * System.out.println("根据数字获得对应的字符用于日期："+getNumberString("2012年12月2日"));
	 * System.out.println("字符串转换成日期类型（YYYY-MM-dd）："+String2Date("2012-07-02"));
	 * System.out.println("字符串转换成日期类型(yyyy-MM-dd HH:mm:ss)："
	 * +String2Date("2012-07-02 14:00:00",1));
	 * System.out.println("字符串转换成日期类型（YYYY-MM-dd）："+String2Date("2012-07-02",2))
	 * ;
	 * System.out.println("字符串转换成日期类型Timestamp（yyyy-mm-dd hh:mm:ss.fffffffff）："
	 * +String2TimeStamp("2012-07-02"));
	 * 
	 * System.out.println(
	 * "--------------------------------------日期计算----------------------------------------------------------------"
	 * ); System.out.println("二个字符串日期相差天数计算"+getDaysBetween("2009-04-05",
	 * "2012-07-02"));
	 * System.out.println("二个日期相差天数计算"+getDaysBetween(getNowTime(),String2Date(
	 * "2009-04-05")));
	 * System.out.println("获得几天前的日期：给定日期的前几天的日期"+getDateBefore(getNowTime(),240)
	 * );
	 * System.out.println("获得几天前的日期：给定日期的前几天的日期"+getDateAfter(getNowTime(),240))
	 * ; String season[]=getThisSeason(11);
	 * System.out.println("根据月份获得季度"+season[0]+"~~"+season[1]);
	 * System.out.println("获得某年某月的最后一天"+getLastDay(2012,5)); String date[]=new
	 * String[2]; date[0]="2009-12-01"; date[1]="2009-10-01"; String
	 * day[]=groupDate(date);
	 * System.out.println("根据字符串日期数组有小到大排序"+day[0]+"<-"+day[1]);
	 * 
	 * System.out.println(
	 * "--------------------------------------获得日期----------------------------------------------------------------"
	 * );
	 * System.out.println("获得本周星期一的日期"+Date2String(getMonday(),"yyyy-MM-dd"));
	 * System.out.println("获得本周星期二的日期"+Date2String(getTuesday(),"yyyy-MM-dd"));
	 * System.out.println("获得本周星期三的日期"+Date2String(getWednesday(),"yyyy-MM-dd"))
	 * ;
	 * System.out.println("获得本周星期四的日期"+Date2String(getThursday(),"yyyy-MM-dd"));
	 * System.out.println("获得本周星期五的日期"+Date2String(getFriday(),"yyyy-MM-dd"));
	 * System.out.println("获得本周星期六的日期"+Date2String(getSaturday(),"yyyy-MM-dd"));
	 * System.out.println("获得本周星期日的日期"+Date2String(getSunday(),"yyyy-MM-dd")+
	 * "====================================================遗留问题");
	 * System.out.println("今天是"+getCurrentWeek());
	 * System.out.println("获得上周日"+Date2String(getPreviousWeekSunday(),2));
	 * System.out.println("获得上周一"+Date2String(getPreviousWeekMonday(),2));
	 * System.out.println("获得下周一"+Date2String(getNextWeekMonday(),2));
	 * System.out.println("获得下周日"+Date2String(getNextWeekSunday(),2));
	 * System.out.println("获得日期获得当前周全年的第几周："+getWeekOfYear(d));
	 * System.out.println("获得日期获得当前年有多少周："+getMaxWeekNumOfYear(2012));
	 * System.out.println("获得到某年某周的第一天 ："+getFirstDayOfWeek(2012,4));
	 * System.out.println("获得到某年某周的最后一天 ："+getLastDayOfWeek(2012,4));
	 * System.out.println("获得指定日期所在周的第一天："+getFirstDayOfWeek(d));
	 * System.out.println("获得指定日期所在周的最后一天"+getLastDayOfWeek(d));
	 * System.out.println("根据参数获得当前日期前几周的星期一的日期"+getPreviousFewWeekday(5));
	 * System.out.println("根据参数获得当前日期前几周的星期日的日期"+getPreviousFewWeekSunday(5));
	 * 
	 * System.out.println("获得本月第一天"+DateToDate(getCurrentMonthFirst(),
	 * "yyyy-mm-dd"));
	 * System.out.println("获得本月最后一天"+DateToDate(getCurrentMonthLast(),
	 * "yyyy-MM-dd"));
	 */
	// System.out.println(Date2String(String2Date("2016-09-30
	// 19:40:39.0",1),1));

	// System.out.println(getNowTime(1));
	/*
	 * Date date=String2Date("2012-10-01");
	 * System.out.println("计算当前日期，前几个月最后一天,返回字符串"+getDefaultDay(date,2));
	 * System.out.println("计算当前日期，前几个月第一天,返回字符串"+getFirstDay(date,2));
	 * 
	 * 
	 * 
	 * 
	 * GregorianCalendar gc = new GregorianCalendar();
	 * gc.add(GregorianCalendar.MONTH,1);
	 * gc.add(GregorianCalendar.DATE,-date.getDate()); DateFormat df =
	 * DateFormat.getDateInstance(DateFormat.SHORT); Date dateTemp =
	 * gc.getTime(); String s = df.format(dateTemp); System.out.println(s);
	 */

	// }
	/**************************************日期转换方法**************************************/
	/**
	 * 
	 * @Title: getNowTime   
	 * @Description: TODO 指定日期格式获取当前时间
	 * @param: @param pattern
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String getNowTime(String pattern) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);// 可以方便地修改日期格式
		String nowTime = dateFormat.format(now);
		return nowTime;
	}

	/**
	 * 
	 * @Title: getListTowDaysDates   
	 * @Description: TODO 获取两个日期之前的日期
	 * @param: @param sDate
	 * @param: @param eDate
	 * @param: @return
	 * @param: @throws ParseException      
	 * @return: List      
	 * @throws
	 */
	public static List getListTowDaysDates(String sDate, String eDate) throws ParseException {
		List dateList = new ArrayList();
		dateList.add(sDate);
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = df.parse(sDate);
		startCalendar.setTime(startDate);
		Date endDate = df.parse(eDate);
		endCalendar.setTime(endDate);
		while (true) {
			startCalendar.add(Calendar.DAY_OF_MONTH, 1);
			if (startCalendar.getTimeInMillis() < endCalendar.getTimeInMillis()) {// TODO
				dateList.add(df.format(startCalendar.getTime()));
			} else {
				break;
			}
		}
		dateList.add(eDate);
		return dateList;
	}

	/**
	 * 
	 * @Title: DateToDate   
	 * @Description: TODO 指定日期格式获取当前时间(日期转日期格式)
	 * @param: @param time
	 * @param: @param pattern
	 * @param: @return      
	 * @return: Date      
	 * @throws
	 */
	public static Date DateToDate(Date time, String pattern) {
		SimpleDateFormat dateFor = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
		String datetime = dateFor.format(time);
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);// 可以方便地修改日期格式
		try {
			time = dateFormat.parse(datetime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 
	 * @Title: getNowTime   
	 * @Description: TODO 获取当前的日期
	 * @param: @param option
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String getNowTime(int option) {

		String pattern = "yyyy-MM-dd HH:mm:ss";
		switch (option) {
		case 1:
			break;
		case 2: {
			pattern = "yyyy-MM-dd";
			break;
		}
		case 3: {
			pattern = "yyyyMMdd";
			break;
		}
		case 4: {
			pattern = "yyyyMMddHHmmss";
			break;
		}
		case 5: {
			pattern = "yyyy-MM-dd HH:mm:ss";
			break;
		}
		case 6: {
			pattern = "yyyy-MM-dd HH:mm:ss:ffffff";
			break;
		}
		case 7: {
			pattern = "yyyy/MM/dd";
			break;
		}
		}
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);// 可以方便地修改日期格式
		String nowTime = dateFormat.format(now);
		return nowTime;
	}

	/**
	 * 
	 * @Title: String2TimeStamp   
	 * @Description: TODO 字符转换时间戳类型
	 * @param: @param str
	 * @param: @return      
	 * @return: java.sql.Timestamp      
	 * @throws
	 */
	static public java.sql.Timestamp String2TimeStamp(String str) {
		if (str == null || "".equals(str.trim())) {
			return null;
		}
		try {
			return java.sql.Timestamp.valueOf(str);
		} catch (IllegalArgumentException iae) {
			str = str + " 00:00:00";
			return java.sql.Timestamp.valueOf(str);
		}
	}

	/**
	 * 
	 * @Title: String2Date   
	 * @Description: TODO 字符串转换成日期类型 格式为yyyy-MM-dd
	 * @param: @param szDate
	 * @param: @return      
	 * @return: Date      
	 * @throws
	 */
	public static Date String2Date(String szDate) {
		return String2Date(szDate, 2);
	}

	/**
	 * 
	 * @Title: String2Date   
	 * @Description: TODO 字符串转换成日期类型
	 * @param: @param szDate
	 * @param: @param option
	 * @param: @return      
	 * @return: Date      
	 * @throws
	 */
	public static Date String2Date(String szDate, int option) {
		try {
			String pattern = "yyyy-MM-dd HH:mm:ss";
			switch (option) {
			case 1:
				break;
			case 2: {
				pattern = "yyyy-MM-dd";
				break;
			}
			}
			SimpleDateFormat formater = new SimpleDateFormat(pattern);
			Date d = formater.parse(szDate);
			return d;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: getNumberString   
	 * @Description: TODO 根据数字获得对应的字符用于日期
	 * @param: @param str
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String getNumberString(String str) {
		char ch[] = str.toCharArray();
		StringBuffer buffer = new StringBuffer();
		Character character;
		for (int i = 0; i < ch.length; i++) {
			character = new Character(ch[i]);
			buffer.append(map.get(character) == null ? ch[i] : map.get(character));
		}
		return buffer.toString();
	}

	/**
	 * 
	 * @Title: getNowTime   
	 * @Description: TODO 获取当前时间
	 * @param: @return      
	 * @return: Date      
	 * @throws
	 */
	public static Date getNowTime() {
		Date now = new Date();
		return now;
	}

	/**
	 * 
	 * @Title: String2Date   
	 * @Description: TODO 根据格式将字符串转化成日期格式
	 * @param: @param date
	 * @param: @param format
	 * @param: @return      
	 * @return: Date      
	 * @throws
	 */
	public static Date String2Date(String date, String format) {
		try {
			SimpleDateFormat formater = new SimpleDateFormat(format);
			Date d = formater.parse(date);
			return d;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: Date2String   
	 * @Description: TODO 日期类型转换成字符串 格式为yyyy-MM-dd
	 * @param: @param date
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String Date2String(Date date) {
		return Date2String(date, 2);
	}

	/**
	 * 
	 * @Title: Date2String   
	 * @Description: TODO 指定格式日期类型转换成字符串
	 * @param: @param date
	 * @param: @param option
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String Date2String(Date date, int option) {
		try {
			String pattern = "yyyy-MM-dd HH:mm:ss";
			switch (option) {
			case 1:
				break;
			case 2: {
				pattern = "yyyy-MM-dd";
				break;
			}
			case 3: {
				pattern = "yyyyMMdd";
				break;
			}
			case 4: {
				pattern = "yyyyMMddHHmmss";
				break;
			}
			case 5: {
				pattern = "yyyy-MM-dd HH:mm:ss";
				break;
			}
			case 6: {
				pattern = "yyyy-MM-dd HH:mm:ss:ffffff";
				break;
			}
			}
			SimpleDateFormat formater = new SimpleDateFormat(pattern);
			String result = formater.format(date);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @Title: Date2String   
	 * @Description: TODO 获得标记时间的字符串
	 * @param: @param date
	 * @param: @param str
	 * @param: @return      
	 * @return: String   返回空值则表示输入错误或者转化错误    
	 * @throws
	 */
	public static String Date2String(Date date, String str) {
		StringBuffer tempBuffer = new StringBuffer();
		str = transString(str);
		if (str == null || str.equals("")) {
			return null;
		}
		String[] formatStr = getFormatString(str);
		if (formatStr != null && formatStr.length > 0) {
			for (int i = 0; i < formatStr.length; i++) {
				tempBuffer.append(formatStr[i] + "-");
			}
			String tempStr = tempBuffer.toString();
			tempStr = tempStr.substring(0, tempStr.length() - 1);
			SimpleDateFormat formater = new SimpleDateFormat(tempStr);
			String result = formater.format(date);
			if (result != null && result.length() > 0) {
				List divideList = divideString(str, formatStr);
				if (divideList != null && !divideList.isEmpty()) {
					String[] resultArray = result.split("-");
					boolean resultLength = resultArray.length == divideList.size() ? true : false;
					result = "";
					for (int i = 0; i < resultArray.length; i++) {
						result += (String) divideList.get(i) + resultArray[i];
					}
					if (!resultLength) {
						result += (String) divideList.get(divideList.size() - 1);
					}
					return result;
				} else {
					System.out.println("分割字符出错！");
					return null;
				}
			} else {
				System.out.println("转换时间出错！");
				return null;
			}
		} else {
			System.out.println("时间格式不正确！");
			return null;
		}
	}

	/**
	 * 
	 * @Title: transString   
	 * @Description: TODO 转化字符,基于时间的大小写转换
	 * @param: @param str
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	private static String transString(String str) {
		String[] trans1 = { "YY", "DD", "hh" };
		String[] trans2 = { "yy", "dd", "HH" };
		try {
			if (str != null && !str.equals("")) {
				for (int i = 0; i < trans1.length; i++) {
					str = str.replaceAll(trans1[i], trans2[i]);
				}
			} else {
				System.out.println("字符为空，不能转化");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("转化字符错误");
			return null;
		}
		return str;
	}

	/**
	 * 
	 * @Title: getFormatString   
	 * @Description: TODO 获取字符串中标示符的方法
	 * @param: @param format
	 * @param: @return       
	 * @return: String[]      
	 * @throws
	 */
	private static String[] getFormatString(String format) {
		Map map = new HashMap();
		for (int i = 0; i < timeFormat.length; i++) {
			String tempString = format;
			int startStr = 0;
			while (i >= 0) {
				int pos = tempString.indexOf(timeFormat[i]);
				if (pos >= 0) {
					if (timeFormat[i] == "yy") { // 对yy特殊处理
						String t1 = (String) map.get(new Integer(pos + startStr));
						String t2 = (String) map.get(new Integer(pos + startStr - 2));
						if ((t1 != null && t1.equals("yyyy")) || (t2 != null && t2.equals("yyyy"))) {
							int start = pos + timeFormat[i].length() > tempString.length() ? tempString.length()
									: (pos + timeFormat[i].length());
							tempString = tempString.substring(start, tempString.length());
							startStr += start;
							continue;
						}
					}
					map.put(new Integer(pos + startStr), timeFormat[i]);
					int start = pos + timeFormat[i].length() > tempString.length() ? tempString.length()
							: (pos + timeFormat[i].length());
					tempString = tempString.substring(start, tempString.length());
					startStr += start;
				} else {
					break;
				}
			}
		}
		Object[] tmp = map.keySet().toArray();
		Arrays.sort(tmp);
		String[] varNames = new String[tmp.length];
		for (int i = 0; i < varNames.length; i++) {
			varNames[i] = (String) map.get(tmp[i]);
		}
		return varNames;
	}

	/**
	 * 
	 * @Title: divideString   
	 * @Description: TODO 分割字符串
	 * @param: @param str
	 * @param: @param divideStr
	 * @param: @return       
	 * @return: List      
	 * @throws
	 */
	private static List divideString(String str, String[] divideStr) {
		List returnList = new ArrayList();
		int start = 0;
		int end = 0;
		if (str != null && str.length() > 0 && divideStr != null && divideStr.length > 0) {
			end = str.length();
			for (int i = 0; i < divideStr.length; i++) {
				int po = str.indexOf(divideStr[i]);
				if (po >= 0) {
					returnList.add(str.substring(0, po));
					start = po + divideStr[i].length();
					str = str.substring(start, str.length());
				}
			}
			if (str != null && str.length() > 0) {
				returnList.add(str);
			}
		} else {
			return null;
		}
		return returnList;
	}

	/**************************************日期转换方法**************************************/
	/**********************************************日期计算*********************************/
	/**
	 * 
	 * @Title: getDaysBetween   
	 * @Description: TODO 得到2日期间的相隔天数
	 * @param: @param beginDay
	 * @param: @param endDay
	 * @param: @return       
	 * @return: long      
	 * @throws
	 */
	public static long getDaysBetween(String beginDay, String endDay) {
		if (beginDay == null || beginDay.equals(""))
			return 0;
		if (endDay == null || endDay.equals(""))
			return 0;
		long day = 0;
		try {
			java.util.Date date = String2Date(beginDay, 2);
			java.util.Date mydate = String2Date(endDay, 2);

			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return 0;
		}
		return day;
	}

	/**
	 * 
	 * @Title: getDaysBetween   
	 * @Description: TODO 得到2日期间的相隔天数
	 * @param: @param beginDay
	 * @param: @param endDay
	 * @param: @return       
	 * @return: long      
	 * @throws
	 */
	public static long getDaysBetween(Date beginDay, Date endDay) {
		long day = (beginDay.getTime() - endDay.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	/**
	 * 
	 * @Title: getDateBeforeMM   
	 * @Description: TODO 获得几天前的日期：给定日期的前几分钟的日期
	 * @param: @param date
	 * @param: @param day
	 * @param: @param option
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public String getDateBefore(Date date, int day, int option) {

		String pattern = "yyyy-MM-dd HH:mm:ss";
		switch (option) {
		case 1:
			break;
		case 2: {
			pattern = "yyyy-MM-dd";
			break;
		}
		case 3: {
			pattern = "yyyyMMdd";
			break;
		}
		case 4: {
			pattern = "yyyyMMddHHmmss";
			break;
		}
		case 5: {
			pattern = "yyyy/MM/dd";
			break;
		}
		case 6: {
			pattern = "yyyy";
			break;
		}
		case 7: {
			pattern = "yyyyMM";
			break;
		}
		}

		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);// 可以方便地修改日期格式
		String nowTime = dateFormat.format(now.getTime());
		return nowTime;
	}

	/**
	 * 
	 * @Title: getDateBefore   
	 * @Description: TODO 获得几天前的日期：给定日期的前几天的日期
	 * @param: @param date
	 * @param: @param day
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getDateBefore(Date date, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	/**
	 * 
	 * @Title: getDateAfter   
	 * @Description: TODO 获得几天后的时间:给定日期的后几天的日期
	 * @param: @param date
	 * @param: @param day
	 * @param: @param option
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public String getDateAfter(Date date, int day, int option) {

		String pattern = "yyyy-MM-dd HH:mm:ss";
		switch (option) {
		case 1:
			break;
		case 2: {
			pattern = "yyyy-MM-dd";
			break;
		}
		case 3: {
			pattern = "yyyyMMdd";
			break;
		}
		case 4: {
			pattern = "yyyyMMddHHmmss";
			break;
		}
		case 5: {
			pattern = "yyyy/MM/dd";
			break;
		}
		case 6: {
			pattern = "yyyy";
			break;
		}
		case 7: {
			pattern = "yyyyMM";
			break;
		}
		}

		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);// 可以方便地修改日期格式
		String nowTime = dateFormat.format(now.getTime());
		return nowTime;
	}

	/**
	 * 
	 * @Title: getThisSeason   
	 * @Description: TODO 根据月份获得月份所在季度是从哪天到哪天
	 * @param: @param month
	 * @param: @return       
	 * @return: String[]      
	 * @throws
	 */
	public static String[] getThisSeason(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int start_days = 1;// years+"-"+String.valueOf(start_month)+"-1";//getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDay(years_value, end_month);
		String seasonDate[] = new String[2];
		seasonDate[0] = years_value + "-" + start_month + "-" + start_days;
		seasonDate[1] = years_value + "-" + end_month + "-" + end_days;
		return seasonDate;

	}

	/**
	 * 
	 * @Title: getLastDay   
	 * @Description: TODO 获取某年某月的最后一天
	 * @param: @param year
	 * @param: @param month
	 * @param: @return       
	 * @return: int      
	 * @throws
	 */
	public static int getLastDay(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 
	 * @Title: isLeapYear   
	 * @Description: TODO 是否闰年
	 * @param: @param year
	 * @param: @return       
	 * @return: boolean      
	 * @throws
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}

	/********************************获得日期*****************************************************/
	/**
	 * 
	 * @Title: getSunday   
	 * @Description: TODO 获得本周日
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getSunday() {
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getMonday   
	 * @Description: TODO 获得本周一
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getMonday() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getTuesday   
	 * @Description: TODO 获得本周二
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getTuesday() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getWednesday   
	 * @Description: TODO 获得本周三
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getWednesday() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getThursday   
	 * @Description: TODO 获得本周四
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getThursday() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getFriday   
	 * @Description: TODO 获得本周五
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getFriday() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getSaturday   
	 * @Description: TODO 获得本周六
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getSaturday() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getCurrentWeek   
	 * @Description: TODO 获取当前时间是星期几 
	 * @param: @return       
	 * @return: String      
	 * @throws
	 */
	public static String getCurrentWeek() {
		Date today = new Date(System.currentTimeMillis());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E");
		return simpleDateFormat.format(today);
	}

	/**
	 * 
	 * @Title: getPreviousWeekSunday   
	 * @Description: TODO 获得上周星期日的日期
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getPreviousWeekSunday() {
		weeks = 0;
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar preSundayDate = new GregorianCalendar();
		preSundayDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date date = preSundayDate.getTime();
		// String preSunday = DateTimeUtil.Date2String(date,2);
		return date;
	}

	/**
	 * 
	 * @Title: getPreviousWeekMonday   
	 * @Description: TODO 获得上周星期一的日期
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getPreviousWeekMonday() {
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date date = currentDate.getTime();
		// String preMonday = DateTimeUtil.Date2String(date,2);
		return date;
	}

	/** 
	 * 
	 * @Title: getNextWeekMonday   
	 * @Description: TODO 获得下周星期一的日期
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getNextWeekMonday() {
		weeks++;
		int mondayPlus = getMondayPlus();
		GregorianCalendar mondayDate = new GregorianCalendar();
		mondayDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date date = mondayDate.getTime();
		// String nextMonday = DateTimeUtil.Date2String(date,2);
		return date;
	}

	/**
	 * 
	 * @Title: getNextWeekSunday   
	 * @Description: TODO 获得下周星期日的日期
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getNextWeekSunday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar sundayDate = new GregorianCalendar();
		sundayDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date date = sundayDate.getTime();
		// String nextSunday = DateTimeUtil.Date2String(date,2);
		return date;
	}

	/**  
	 * 
	 * @Title: getWeekOfYear   
	 * @Description: TODO 取得当前日期是多少周 
	 * @param: @param date
	 * @param: @return       
	 * @return: int      
	 * @throws
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);

		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**  
	 * 
	 * @Title: getMaxWeekNumOfYear   
	 * @Description: TODO 得到某一年周的总数 
	 * @param: @param year
	 * @param: @return       
	 * @return: int      
	 * @throws
	 */
	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);

		return getWeekOfYear(c.getTime());
	}

	/**  
	 * 
	 * @Title: getFirstDayOfWeek   
	 * @Description: TODO 得到某年某周的第一天 
	 * @param: @param year
	 * @param: @param week
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getFirstDayOfWeek(cal.getTime());
	}

	/** 
	 * 
	 * @Title: getLastDayOfWeek   
	 * @Description: TODO 得到某年某周的最后一天 
	 * @param: @param year
	 * @param: @param week
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);

		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);

		return getLastDayOfWeek(cal.getTime());
	}

	/** 
	 * 
	 * @Title: getFirstDayOfWeek   
	 * @Description: TODO 取得指定日期所在周的第一天 
	 * @param: @param date
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c.getTime();
	}

	/** 
	 * 
	 * @Title: getLastDayOfWeek   
	 * @Description: TODO 取得指定日期所在周的最后一天 
	 * @param: @param date
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	/**
	 * 
	 * @Title: getMondayPlus   
	 * @Description: TODO 获得今天本周第几天
	 * @param: @return       
	 * @return: int      
	 * @throws
	 */
	public static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	/** 
	 * 
	 * @Title: getPreviousMonthFirst   
	 * @Description: TODO 获得上月第一天的日期
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getPreviousMonthFirst() {
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(Calendar.DATE, 1);// 设为当前月的1号
		firstDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		// String str = DateTimeUtil.Date2String(lastDate.getTime(),2);
		return firstDate.getTime();
	}

	/**
	 * 
	 * @Title: getPreviousMonthLast   
	 * @Description: TODO 获得上月最后一天的日期
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getPreviousMonthLast() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		// String str = DateTimeUtil.Date2String(lastDate.getTime(),2);
		return lastDate.getTime();
	}

	/**
	 * 
	 * @Title: getCurrentMonthLast   
	 * @Description: TODO 计算当月最后一天,返回字符串
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getCurrentMonthLast() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		// String str = DateTimeUtil.Date2String(lastDate.getTime(),2);
		return lastDate.getTime();
	}

	/**
	 * 
	 * @Title: getCurrentMonthFirst   
	 * @Description: TODO 获取当月第一天
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getCurrentMonthFirst() {
		Calendar firstDate = Calendar.getInstance();
		firstDate.set(Calendar.DATE, 1);// 设为当前月的1号
		// String str = DateTimeUtil.Date2String(lastDate.getTime(),2);
		return firstDate.getTime();
	}

	/** 
	 * 
	 * @Title: getNextMonthFirst   
	 * @Description: TODO 获得下个月第一天的日期
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getNextMonthFirst() {
		String str = "";
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		// String str = DateTimeUtil.Date2String(lastDate.getTime(),2);
		return lastDate.getTime();
	}

	/**
	 * 
	 * @Title: getPreviousFewWeekday   
	 * @Description: TODO 根据参数获得当前日期前几周的星期一的日期
	 * @param: @param num
	 * @param: @return       
	 * @return: String      
	 * @throws
	 */
	public static String getPreviousFewWeekday(int num) {
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + num * 7 * weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 
	 * @Title: getPreviousFewWeekSunday   
	 * @Description: TODO 根据参数获得当前日期前几周的星期日的日期
	 * @param: @param num
	 * @param: @return       
	 * @return: String      
	 * @throws
	 */
	public static String getPreviousFewWeekSunday(int num) {
		weeks--;
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + num * weeks);
		Date monday = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(monday);
		return preMonday;
	}

	/**
	 * 
	 * @Title: getNextMonthLast   
	 * @Description: TODO 获得下个月最后一天的日期
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getNextMonthLast() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		// String str = DateTimeUtil.Date2String(lastDate.getTime(),2);
		return lastDate.getTime();
	}

	/** 
	 * 
	 * @Title: getDefaultDay   
	 * @Description: TODO 计算当前日期，前几个月最后一天,返回字符串
	 * @param: @param date
	 * @param: @param n
	 * @param: @return       
	 * @return: String      
	 * @throws
	 */
	public static String getDefaultDay(Date date, int n) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();

		// lastDate.set(Calendar.DATE,1);//设为当前月的1号
		if (n != 0) {
			c.add(Calendar.MONTH, n);// 加一个月，变为下月的1号
		}
		c.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		// c.setTime(date);

		c.add(Calendar.MONTH, -1);// 减一个月
		c.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		c.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天

		str = sdf.format(c.getTime());

		return str;
	}

	/**
	 * 
	 * @Title: getFirstDay   
	 * @Description: TODO 计算当前日期，前几个月第一天,返回字符串
	 * @param: @param date
	 * @param: @param amount
	 * @param: @return       
	 * @return: String      
	 * @throws
	 */
	public static String getFirstDay(Date date, int amount) {

		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, amount);
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		// lastDate.setTime(date);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/** 
	 * 
	 * @Title: getPreviousYearFirst   
	 * @Description: TODO 获得上年第一天的日期
	 * @param: @return       
	 * @return: String      
	 * @throws
	 */
	public static String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	/**
	 * 
	 * @Title: getPreviousYearLast   
	 * @Description: TODO 获得上年最后一天的日期
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getPreviousYearLast() {
		weeks--;
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks + (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		// String str = DateTimeUtil.Date2String(yearDay,2);
		return yearDay;
	}

	/**
	 * 
	 * @Title: getCurrentYearNumber   
	 * @Description: TODO 获得本年有多少天
	 * @param: @return       
	 * @return: int      
	 * @throws
	 */
	public static int getCurrentYearNumber() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	public static int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);//
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	/**
	 * 
	 * @Title: getCurrentYearFirst   
	 * @Description: TODO 获得本年第一天的日期
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getCurrentYearFirst() {
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		return yearDay;
	}

	/**
	 * 
	 * @Title: getCurrentYearLast   
	 * @Description: TODO 获得本年最后一天的日期
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getCurrentYearLast() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 0);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		// String str = DateTimeUtil.Date2String(lastDate.getTime(),2);
		return lastDate.getTime();
	}

	/**
	 * 
	 * @Title: getNextYearFirst   
	 * @Description: TODO  获得明年第一天的日期
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getNextYearFirst() {
		weeks--;
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR); // 获得本年有多少天
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, MaxYear - (yearPlus * weeks) + 2);
		Date yearDay = currentDate.getTime();
		// String str = DateTimeUtil.Date2String(yearDay,2);
		return yearDay;

	}

	/**
	 * 
	 * @Title: getNextYearLast   
	 * @Description: TODO 获得明年最后一天的日期
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getNextYearLast() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		// String str = DateTimeUtil.Date2String(lastDate.getTime(),2);
		return lastDate.getTime();
	}

	/**
	 * 
	 * @Title: getDate   
	 * @Description: TODO 获得某年某月某周星期几的日期
	 * @param: @param year
	 * @param: @param month
	 * @param: @param weekOfMonth
	 * @param: @param dayOfWeek
	 * @param: @return       
	 * @return: Date      
	 * @throws
	 */
	public static Date getDate(int year, int month, int weekOfMonth, int dayOfWeek) {
		Calendar c = Calendar.getInstance();
		// 计算出 x年 y月 1号 是星期几
		c.set(year, month - 1, 1);

		// 如果i_week_day =1 的话 实际上是周日
		int i_week_day = c.get(Calendar.DAY_OF_WEEK);

		int sumDay = 0;
		// dayOfWeek+1 就是星期几（星期日 为 1）
		if (i_week_day == 1) {
			sumDay = (weekOfMonth - 1) * 7 + dayOfWeek + 1;
		} else {
			sumDay = 7 - i_week_day + 1 + (weekOfMonth - 1) * 7 + dayOfWeek + 1;
		}
		// 在1号的基础上加上相应的天数
		c.set(Calendar.DATE, sumDay);
		// String str = DateTimeUtil.Date2String(c.getTime(),2);
		return c.getTime();
	}

	/**  
	 * 
	 * @Title: weekdatetodata   
	 * @Description: TODO  获得某年某月某周星期几的日期 
	 * @param: @param year
	 * @param: @param month
	 * @param: @param weekOfMonth
	 * @param: @param dayOfWeek
	 * @param: @return       
	 * @return: String      
	 * @throws
	 */
	public static String weekdatetodata(int year, int month, int weekOfMonth, int dayOfWeek) {
		Calendar c = Calendar.getInstance();
		// 计算出 x年 y月 1号 是星期几
		c.set(year, month - 1, 1);

		// 如果i_week_day =1 的话 实际上是周日
		int i_week_day = c.get(Calendar.DAY_OF_WEEK);

		int sumDay = 0;
		// dayOfWeek+1 就是星期几（星期日 为 1）
		if (i_week_day == 1) {
			sumDay = (weekOfMonth - 1) * 7 + dayOfWeek + 1;
		} else {
			sumDay = 7 - i_week_day + 1 + (weekOfMonth - 1) * 7 + dayOfWeek + 1;
		}
		// 在1号的基础上加上相应的天数
		c.set(Calendar.DATE, sumDay);
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
		return sf2.format(c.getTime());
	}

	/** 
	 * 
	 * @Title: getLastDayOfMonth   
	 * @Description: TODO 获取某年某月的最后一天
	 * @param: @param year
	 * @param: @param month
	 * @param: @return       
	 * @return: int      
	 * @throws
	 */
	private static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * @author ranxing
	 * 获取指定日期字符串的   指定加减日期  的返回字符串
	 * @param strDate   传入日期字符串:20170201
	 * @param strDateFormat	对应传入字符串的format:"yyyyMMdd"  
	 * @param returnDateFormat	返回字符串的format:"yyyyMMddHHmmss"
	 * @param numA		GregorianCalendar的编号 1 2 3 5<br>
	 * gc.add(numA,numB);<br>
	 * gc.add(1,-1)表示年份减一. <br>
	 * gc.add(2,-1)表示月份减一.<br>
	 * gc.add(3.-1)表示周减一.<br>
	 * gc.add(5,-1)表示天减一.<br>
	 * @param numB		GregorianCalendar的加减数字
	 * @return   "20170125000000"
	 *
	 */
	public static String strAdd2Str(String strDate, String strDateFormat, String returnDateFormat, int numA, int numB) {

		try {
			SimpleDateFormat formater = new SimpleDateFormat(strDateFormat);
			Date d = formater.parse(strDate);
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(d);
			gc.add(numA, numB);
			String dayAfter = new SimpleDateFormat(returnDateFormat).format(gc.getTime());
			return dayAfter;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String localeEnglishToDefualt(String object) {
		// s = "Tue Jul 12 00:00:00 GMT+08:00 2016";
		SimpleDateFormat sf1 = new SimpleDateFormat("EEE MMM dd hh:mm:ss z yyyy", Locale.ENGLISH);
		Date date = new Date();
		;
		try {

			date = sf1.parse(object);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sf2.format(date);
	}

	/**
	 * 
	 * @Description: TODO 比较两个日志之差
	 * @author: zhongdd
	 * @param: @param paramString1
	 * @param: @param paramString2
	 * @param: @return       
	 * @return: int      
	 * @throws
	 */
	public static int compare_date(String paramString1, String paramString2) {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Object localObject = null;
			localObject = localSimpleDateFormat.parse(paramString1);
			Date localDate = localSimpleDateFormat.parse(paramString2);
			if (((Date) localObject).getTime() > localDate.getTime())
				return 1;
			if (((Date) localObject).getTime() < localDate.getTime())
				return -1;
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void main(String[] args2) {
		System.out.println(11111);
	}

}
