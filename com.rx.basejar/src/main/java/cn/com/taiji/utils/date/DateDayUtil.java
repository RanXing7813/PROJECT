package cn.com.taiji.utils.date;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.text.ParseException;

/** 
* 日期 的日月年周天常用类
* @ClassName:  DateDayUtil
* @author ranxing
* @date 2018年11月6日 上午10:29:42
*/
public class DateDayUtil {
	private static final Logger logger = LoggerFactory.getLogger(DateDayUtil.class);

	
	// 用来全局控制 上一周，本周，下一周的周数变化
	private int weeks = 0;
	private int MaxDate;// 一月最大天数
	
	private int MaxYear;// 一年最大天数
	
	/**   默认日期转换格式  "yyyy-MM-dd"  */
	private static String deflatePattern = "yyyy-MM-dd";//transient
	/**   默认日期格式转换中文   */
	private static String [] chineseDateArray = new String [] {"零","一","二","三","四","五","六","七","八","九"}; 
	/**    默认日期格式转换繁体中文   */
	private static final String[] NUMBERS = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		DateDayUtil tt = new DateDayUtil();
//		 System.out.println("获取当天日期:" + tt.getNowTime(deflatePattern));
//		System.out.println("获得当前日期的后七天" + tt.getDateAfter(new Date(), 7));
//		System.out.println("获得当前日期的前七天" + tt.getDateBefore(new Date(), 7));
//		System.out.println("获取本周一日期:" + tt.getMondayOFWeek());
//		System.out.println("获取本周日的日期~:" + tt.getCurrentWeekday());
//		System.out.println("获取上周一日期:" + tt.getPreviousWeekday());
//		System.out.println("获取上周日日期:" + tt.getPreviousWeekSunday());
//		System.out.println("获取下周一日期:" + tt.getNextMonday());
//		System.out.println("获取下周日日期:" + tt.getNextSunday());
//		System.out.println("获得相应周的周六的日期:" + tt.getNowTime(deflatePattern));
//		System.out.println("获取本月第一天日期:" + tt.getFirstDayOfMonth());
//		System.out.println("获取本月最后一天日期:" + tt.getDefaultDay()); 
//		 System.out.println("获取上月第一天日期:" + tt.getPreviousMonthFirst());
//		System.out.println("获取上月最后一天的日期:" + tt.getPreviousMonthEnd());
//		System.out.println("获取下月第一天日期:" + tt.getNextMonthFirst());
//		System.out.println("获取下月最后一天日期:" + tt.getNextMonthEnd());
//		System.out.println("获取本年的第一天日期:" + tt.getCurrentYearFirst());
//		System.out.println("获取本年最后一天日期:" + tt.getCurrentYearEnd());
//		System.out.println("获取去年的第一天日期:" + tt.getPreviousYearFirst());
//		System.out.println("获取去年的最后一天日期:" + tt.getPreviousYearEnd());
//		System.out.println("获取明年第一天日期:" + tt.getNextYearFirst());
//		System.out.println("获取明年最后一天日期:" + tt.getNextYearEnd());
//		System.out.println("获取本季度第一天到最后一天:" + tt.getThisSeasonTime(11));
//		System.out.println("根据获取某年某月的最后一天:" + tt.getLastDayOfMonth(2009, 6));
//		System.out.println("获得某年某月某周星期几的日期 " + weekdatetodata(2009, 2, 2, 3));
//		System.out.println("获得日期的月份 " + getMonth("2008-04-01")); 
//		int year = 2018;
//		int month = 11;
		
		System.out.println("获取指定年月的第一天"+getFirstDayOfMonth(2018,11));
		System.out.println("获取指定年月的最后一天"+getLastDayOfMonth(2018,11));
		System.out.println("获取指定年周开始时间: " + getStartDayOfWeekNo(2018,44) );
		System.out.println("获取指定年周结束时间:" +  getEndDayOfWeekNo(2018,44) );  
		System.out.println("获取指定年月周星期时间:" + weekdatetodata(2018, 11, 0, 1));
		System.out.println("获取指定日期转成中文:" + strDate2Chinese("2018-11-11"));
		System.out.println("获取指定日期的周中文:" + getWeek("2018-11-12"));
		System.out.println("获取两个日期之间间隔天数3-1:" + getTwoDay("2018-12-30", "2019-1-1"));
		System.out.println("获取指定日期的周中文:" + new SimpleDateFormat("EEEE",Locale.CHINA).format(new Date()));
		
//		 Calendar cal = new GregorianCalendar();
//		
//        System.out.println(cal.getFirstDayOfWeek() ); 
//        // SUNDAY、 MONDAY、 TUESDAY、 WEDNESDAY、 THURSDAY、 FRIDAY 和 SATURDAY。
//        cal.set(Calendar.YEAR, year);
//		cal.set(Calendar.MONTH,month);
//	    cal.set(Calendar.WEEK_OF_MONTH, 1);
//	    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);   
//	        //格式化日期
//	        SimpleDateFormat sdf = new SimpleDateFormat(deflatePattern);
//	        System.out.println( sdf.format(cal.getTime()) ); 
//	        System.out.println( new SimpleDateFormat("EEEE",Locale.US).format(cal.getTime())); 
	        //2018年11月 第1周星期1 得到 2018-10-29
		

		//deflateChinesePattern
//        System.out.println( new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z",Locale.CHINA).format(new Date())); 
		
		
		
	}
	
	



	    /**
	     * 通过 yyyy-MM-dd 得到中文大写格式 yyyy年MM月dd日 日期
	     * @param strDate  2018-01-01
	     * @param unit
	     * @return 二零一八年一月一日
	     */
	    public static String strDate2Chinese(String strDate ) {
	    	SimpleDateFormat sf = new SimpleDateFormat(deflatePattern, Locale.CHINA);
	    	StringBuffer strBuffer = new StringBuffer();
			try {
				Calendar calendar = Calendar.getInstance();
				Date dt = sf.parse(strDate);//"2018-01-01"
				calendar.setTime(dt);
				int year_ = calendar.get(Calendar.YEAR);
//				int month_ = (calendar.get(Calendar.MONTH) + 1);
				int day_ = calendar.get(Calendar.DAY_OF_MONTH);
				 
				 String year =   String .valueOf(year_);
				 for (int i = 0; i < chineseDateArray.length; i++) {
					 year = year.replace(i+"", chineseDateArray[i]);
			     }
				 strBuffer.append(year).append("年");
				 strBuffer.append(new SimpleDateFormat("MMM",Locale.CHINA).format(dt));
				 
				String day =  String .valueOf(day_);
				day =  day_ <10 ? chineseDateArray[day_] : (day.substring(0,1)+"十"+chineseDateArray[ Integer.valueOf(day.substring(1)) ]);
				day = day.replace("零", "").replace("1", "").replace("2", "二").replace("3", "三");
				strBuffer.append(day).append("日");
				
				return strBuffer.toString();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
//			  System.out.println(calendar.get(Calendar.YEAR) + "-" + new SimpleDateFormat("MMM",Locale.CHINA).format(dt) + "-" +
//            calendar.get(Calendar.DAY_OF_MONTH)); 
	    }

	    /** 转换数字为大写 */

	    /** 判断是否是零或正整数 */
	    public static boolean isNumeric(String str) {
	        Pattern pattern = Pattern.compile("[0-9]*");
	        Matcher isNum = pattern.matcher(str);
	        if (!isNum.matches()) {
	            return false;
	        }
	        return true;
	    }
	/**
	* 获取指定 年份  月份 第几周  星期几的日期
	* @param year 年份 eg:2018
	* @param month 月份 eg:11 
	* @param weekOfMonth 这个月的第几周 0-4,其余值会日期进行运算 eg:0
	* @param dayOfWeek 星期几 1-7, 星期一为1,eg:1
	* @return 2018-10-29
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
		logger.info("i_week_day:{},sumDay:{} "+ new SimpleDateFormat("EEEE",Locale.CHINA).format(c.getTime()),i_week_day,sumDay);

		// 在1号的基础上加上相应的天数
		c.set(Calendar.DATE, sumDay);
		SimpleDateFormat sf2 = new SimpleDateFormat(deflatePattern);
		return sf2.format(c.getTime());
	}
	   
//    /**
//     * get first date of given month and year
//     * @param year
//     * @param month
//     * @return
//     */
//    public static String getFirstDayOfMonth(int year,int month){
//        String monthStr = month < 10 ? "0" + month : String.valueOf(month);
//        return year + "-"+monthStr+"-" +"01";
//    }
//    
//    /**
//     * get the last date of given month and year
//     * @param year
//     * @param month
//     * @return
//     */
//    public static String getLastDayOfMonth(int year,int month){
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.YEAR , year);
//        calendar.set(Calendar.MONTH , month - 1);
//        calendar.set(Calendar.DATE , 1);
//        calendar.add(Calendar.MONTH, 1);
//        calendar.add(Calendar.DAY_OF_YEAR , -1);
//        return calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" +
//               calendar.get(Calendar.DAY_OF_MONTH);
//    }
    
    /**
     * get Calendar of given year
     * @param year
     * @return
     */
    private static Calendar getCalendarFormYear(int year){
        Calendar cal = Calendar.getInstance();
        // SUNDAY、 MONDAY、 TUESDAY、 WEDNESDAY、 THURSDAY、 FRIDAY 和 SATURDAY。
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);      
        cal.set(Calendar.YEAR, year);
        return cal;
    }
    
    /**
     * get start date of given week no of a year
     * <br/>获取一年中给定的第weekNo周的开始日期,
     * <br/>(已设置cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY))
     * @param year
     * @param weekNo
     * @return
     */
    public static String getStartDayOfWeekNo(int year,int weekNo){
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat(deflatePattern);
        return sdf.format(cal.getTime()); 
    }
    
    /**
     * get the end day of given week no of a year.
     * <br/>获取一年中给定的第weekNo周的结束日期,
     * <br/>(已设置cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY))
     * @param year
     * @param weekNo
     * @return
     */
    public static String getEndDayOfWeekNo(int year,int weekNo){
        Calendar cal = getCalendarFormYear(year);
        cal.set(Calendar.WEEK_OF_YEAR, weekNo);
        cal.add(Calendar.DAY_OF_WEEK, 6);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat(deflatePattern);
        return sdf.format(cal.getTime());    
    }
	
	/**
     * 获取指定年月的第一天
     * @param year eg:2016
     * @param month eg:2  月份0为上一年  13为下一年
     * @return 2016-02-01
     */
    public static String getFirstDayOfMonth(int year, int month) {     
        Calendar cal = Calendar.getInstance();   
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份 
        cal.set(Calendar.MONTH, month-1); 
        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数 
        cal.set(Calendar.DAY_OF_MONTH,firstDay);  
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat(deflatePattern);
        return sdf.format(cal.getTime());  
    }
    /**
     * 获取指定年月的最后一天
     * @param year eg:2016
     * @param month eg:2  月份0为上一年  13为下一年
     * @return 2016-02-29
     */
     public static String getLastDayOfMonth(int year, int month) {     
         Calendar cal = Calendar.getInstance();     
         //设置年份  
         cal.set(Calendar.YEAR, year);  
         //设置月份  
         cal.set(Calendar.MONTH, month-1); 
         //获取某月最大天数
         int lastDay = cal.getActualMaximum(Calendar.DATE);
         //设置日历中月份的最大天数  
         cal.set(Calendar.DAY_OF_MONTH, lastDay);  
         //格式化日期
         SimpleDateFormat sdf = new SimpleDateFormat(deflatePattern);  
         return sdf.format(cal.getTime());
     }
    

	@SuppressWarnings("static-access")
	public static String getFolderName() {
		DateDayUtil tt = new DateDayUtil();
		String Path = "/wafplatform/staticsmanage/datahtml/";
		Path += tt.getNowTime("yyyyMMdd") + "/";
		return Path;

	}

	
	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat(deflatePattern);
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeek(String sdate) {
		// 再转换为时间
		Date date = DateDayUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE",Locale.CHINA).format(c.getTime());
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeekByDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat(deflatePattern);
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat(deflatePattern);
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	// 计算当月最后一天,返回字符串
	public String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(deflatePattern);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 上月第一天
	public static String getPreviousMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(deflatePattern);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
		// lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天

		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获取当月第一天
	public String getFirstDayOfMonth() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(deflatePattern);

		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得本周星期日的日期
	public String getCurrentWeekday() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();

		SimpleDateFormat df = new SimpleDateFormat(deflatePattern);
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获取当天时间
	public static String getNowTime(String dateformat) {
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
		String hehe = dateFormat.format(now);

		return hehe;
	}

	// 获得几天前的时间
	public Date getDateBefore(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}

	// 获得几天后的时间
	public static Date getDateAfter(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	// 获得当前日期与本周日相差的天数
	private int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1

		if (dayOfWeek == 0) { // 如果是周日
			dayOfWeek = 7;
		}
		//System.out.println("相差天数:" + dayOfWeek);
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}

	// 获得本周一的日期
	public String getMondayOFWeek() {
		weeks = 0;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();

		SimpleDateFormat df = new SimpleDateFormat(deflatePattern);
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得相应周的周六的日期
	public String getSaturday() {
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks + 6);
		Date monday = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat(deflatePattern);
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得上周星期日的日期
	public String getPreviousWeekSunday() {
		weeks = 0;
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
		Date monday = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat(deflatePattern);
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得上周星期一的日期
	public String getPreviousWeekday() {
		weeks--;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
		Date monday = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat(deflatePattern);
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得下周星期一的日期
	public String getNextMonday() {
		weeks++;
		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7);
		Date monday = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat(deflatePattern);
		String preMonday = df.format(monday);
		return preMonday;
	}

	// 获得下周星期日的日期
	public String getNextSunday() {

		int mondayPlus = this.getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + 6);
		Date monday = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat(deflatePattern);
		String preMonday = df.format(monday);
		return preMonday;
	}

	@SuppressWarnings("unused")
	private int getMonthPlus() {
		Calendar cd = Calendar.getInstance();
		int monthOfNumber = cd.get(Calendar.DAY_OF_MONTH);
		cd.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		cd.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		MaxDate = cd.get(Calendar.DATE);
		if (monthOfNumber == 1) {
			return -MaxDate;
		} else {
			return 1 - monthOfNumber;
		}
	}

	// 获得上月最后一天的日期
	public static String getPreviousMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(deflatePattern);

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, -1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月第一天的日期
	public String getNextMonthFirst() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(deflatePattern);

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得下个月最后一天的日期
	public String getNextMonthEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(deflatePattern);

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.MONTH, 1);// 加一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年最后一天的日期
	public String getNextYearEnd() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat(deflatePattern);

		Calendar lastDate = Calendar.getInstance();
		lastDate.add(Calendar.YEAR, 1);// 加一个年
		lastDate.set(Calendar.DAY_OF_YEAR, 1);
		lastDate.roll(Calendar.DAY_OF_YEAR, -1);
		str = sdf.format(lastDate.getTime());
		return str;
	}

	// 获得明年第一天的日期
	public String getNextYearFirst() {
		weeks--;
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR); // 获得本年有多少天
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, MaxYear - (yearPlus * weeks) + 2);
		Date yearDay = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat(deflatePattern);
		String preYearDay = df.format(yearDay);
		return preYearDay;

	}

	// 获得本年有多少天
	@SuppressWarnings("unused")
	private int getMaxYear() {
		Calendar cd = Calendar.getInstance();
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		return MaxYear;
	}

	private int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}

	// 获得本年第一天的日期
	public String getCurrentYearFirst() {
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat(deflatePattern);
		String preYearDay = df.format(yearDay);
		String[] arr_time = preYearDay.toString().split("-");
		if (arr_time[1].length() == 1) {
			arr_time[1] = "0" + arr_time[1];
		}
		if (arr_time[2].length() == 1) {
			arr_time[2] = "0" + arr_time[2];
		}
		preYearDay = arr_time[0] + "-" + arr_time[1] + "-" + arr_time[2];
		return preYearDay;
	}

	// 获得本年最后一天的日期 *
	public String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	// 获得上年第一天的日期 *
	public String getPreviousYearFirst() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		years_value--;
		return years_value + "-1-1";
	}

	// 获得上年最后一天的日期
	public String getPreviousYearEnd() {
		weeks--;
		int yearPlus = this.getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus + MaxYear * weeks + (MaxYear - 1));
		Date yearDay = currentDate.getTime();
		SimpleDateFormat df = new SimpleDateFormat(deflatePattern);
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}


	/**
	 * 日期转换成字符串
	 * 
	 * @param date
	 * @return str
	 */
	public static String DateToStr(Date date, String type) {

		SimpleDateFormat format = new SimpleDateFormat(type);
		String str = format.format(date);
		return str;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str, String type) {

		SimpleDateFormat format = new SimpleDateFormat(type);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String[] groupDate(String[] date) {

		DateFormat df = new SimpleDateFormat(deflatePattern);
		for (int f = 0; f < date.length; f++) {
			for (int i = 0; i < date.length; i++) {
				Date temp1;
				try {
					temp1 = df.parse(date[i]);
					int j = i + 1;
					if (j == date.length)
						j = j - 1;
					Date temp2 = df.parse(date[j]);
					if (temp1.getTime() > temp2.getTime()) {
						String t = date[i];
						date[i] = date[j];
						date[j] = t;
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}

		return date;
	}
	
	public static String getMonth(String sdate) {
		// 再转换为时间
		Date date = DateDayUtil.strToDate(sdate);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		String m = new SimpleDateFormat("MM").format(c.getTime());
		return Integer.parseInt(m)+"月";
	}

	
	
	
}