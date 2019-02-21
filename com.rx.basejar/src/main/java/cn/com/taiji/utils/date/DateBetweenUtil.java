package cn.com.taiji.utils.date;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
* 比较两个日期的间隔类
* @ClassName:  DateBetweenUtil
* @author ranxing
* @date 2018年11月6日 上午10:28:04
*/
public class DateBetweenUtil {
	
	Log log = LogFactory.getLog(DateBetweenUtil.class); 

	public final int YYYY_MM_dd_hh_mm_ss = 1;
	public final int YYYY_MM_dd = 2;

	public final String YYYY = "yyyy";
	public final String YY = "yy";
	public final String MM = "MM";
	public final String DD = "dd";
	public final String hh = "HH";
	public final String mm = "mm";
	public final String ss = "ss";

	public String[] timeFormat = { YYYY, YY, MM, DD, hh, mm, ss };
	
	public static void main(String[] args) {
		System.out.println(daysBetween(new java.util.Date("2017/1/5"),new java.util.Date("2017/1/3")));
	}
	

	/**
	 * 比较2个日期 yyyy-MM-dd  相隔天数
	 * @param smdate
	 * @param bdate
	 * @return 正整数
	 */
	public static int daysBetween(Date smdate,Date bdate) { 
		  return (int) Math.abs(((smdate.getTime() / 86400000L) - (bdate.getTime() / 86400000L))); 
	} 
	
	
	
	

	/**
	 * @author tj
	 * @version 1.0
	 * @see 获取当前时间
	 * @param option
	 *            日期格式 ：这里日期为标准的JAVA日期格式yyyy->年.MM->月dd->日 HH->时mm->分:ss->秒
	 *            1代表yyyy-MM-dd HH:mm:ss 2代表yyyy-MM-dd 3代表yyyyMMdd
	 *            4代表yyyyMMddHHmmss 5代表yyyy-MM-dd HH:mm:ss:ffffff
	 */
	public String getNowTime(int option) {

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
		}
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);// 可以方便地修改日期格式
		String nowTime = dateFormat.format(now);
		return nowTime;
	}

	/**
	 * @author tj
	 * @version 1.0
	 * @see 获取当前时间
	 */
	public Date getNowTime() {
		Date now = new Date();
		return now;
	}

	/**
	 * @author tj
	 * @version 1.0
	 * @see 获得几天前的日期：给定日期的前几天的日期
	 * @param date
	 *            日期
	 * @param day
	 *            数值类型
	 */
	public Date getDateBefore(Date date, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
		return now.getTime();
	}
	
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
	 * @author tj
	 * @version 1.0
	 * @see 获得几天后的时间:给定日期的后几天的日期
	 * @param date
	 *            日期
	 * @param day
	 *            数值类型
	 */
	public Date getDateAfter(Date date, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 
	 * @名称：判断时间是上午还是下午
	 * @作者：钟代冬
	 * @创建时间： Aug 19, 2014 2:18:52 PM
	 * @说明：//结果为“0”是上午 结果为“1”是下午
	 */
	public int getDateAm_Pm(Date date) {
		GregorianCalendar ca = new GregorianCalendar();
		ca.setTime(date);
		return ca.get(GregorianCalendar.AM_PM);
	}

	public static int compare_date(String paramString1, String paramString2) {
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
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
}
