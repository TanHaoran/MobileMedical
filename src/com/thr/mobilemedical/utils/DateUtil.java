package com.thr.mobilemedical.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SuppressLint("SimpleDateFormat")
public class DateUtil {

	/**
	 * 取得当前年月日
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getYMD() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * 获取昨天年月日
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getYesterdayYMD() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		String yesterday = new SimpleDateFormat("yyyy-MM-dd").format(cal
				.getTime());
		return yesterday;
	}

	/**
	 * 取得当前年月日时分秒
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getYMDHMS() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 取得当前时分
	 * 
	 * @return HH:mm
	 */
	public static String getHM() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(date);
	}

	/**
	 * 判断日期是否是今天
	 * 
	 * @param inStr
	 * @return
	 */
	public static boolean isToday(String inStr) {
		boolean isToday = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date todayDate = new Date();
		try {
			Date inDate = sdf.parse(inStr);
			Calendar todayCalendar = Calendar.getInstance();
			todayCalendar.setTime(todayDate);
			Calendar inCalendar = Calendar.getInstance();
			inCalendar.setTime(inDate);
			if (todayCalendar.get(Calendar.YEAR) == inCalendar
					.get(Calendar.YEAR)
					&& todayCalendar.get(Calendar.MONTH) == inCalendar
							.get(Calendar.MONTH)
					&& todayCalendar.get(Calendar.DAY_OF_MONTH) == inCalendar
							.get(Calendar.DAY_OF_MONTH)) {
				isToday = true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return isToday;
	}

	/**
	 * 字符串转换到时间格式
	 * 
	 * @param dateStr
	 *            需要转换的字符串
	 * @param formatStr
	 *            需要格式的目标字符串 举例 yyyy-MM-dd
	 * @return Date 返回转换后的时间
	 * @throws ParseException
	 *             转换异常
	 */
	public static Date StringToDate(String dateStr, String formatStr) {
		DateFormat sdf = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			dateStr = dateStr.replace("/", "-");
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 根据生日算出年龄
	 * 
	 * @param dateOfBirth
	 * @return
	 */
	public static int getAge(Date dateOfBirth) {
		int age = 0;
		Calendar born = Calendar.getInstance();
		Calendar now = Calendar.getInstance();
		if (dateOfBirth != null) {
			now.setTime(new Date());
			born.setTime(dateOfBirth);
			if (born.after(now)) {
				throw new IllegalArgumentException(
						"Can't be born in the future");
			}
			age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
			if (now.get(Calendar.DAY_OF_YEAR) < born.get(Calendar.DAY_OF_YEAR)) {
				age -= 1;
			}
		}
		return age;
	}

	/**
	 * 根据字符串计算年龄
	 * 
	 * @param dateStr
	 * @return
	 */
	public static int getAge(String dateStr) {
		Date date = StringToDate(dateStr, "yyyy-MM-dd");
		return getAge(date);
	}

	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @param smdate
	 *            较小的时间
	 * @param bdate
	 *            较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 获取当前小时（24小时）
	 * 
	 * @return
	 */
	public static int getCurrentHour() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取当前分钟
	 * 
	 * @return
	 */
	public static int getCurrentMinute() {
		Calendar c = new GregorianCalendar();
		return c.get(Calendar.MINUTE);
	}
	
	/**
	 * 转换成yyyy/MM/dd HH:mm格式
	 * 
	 * @param string
	 * @return
	 */
	public static String formatDate(String string) {
		string = string.replace("-", "/");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date date = null;
		try {
			date = sdf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(date);
	}
	
	/**
	 * 转换成yyyy/MM/dd格式
	 * 
	 * @param string
	 * @return
	 */
	public static String formatDateOrder(String string) {
		string = string.replace("-", "/");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = sdf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(date);
	}

	/**
	 * 转换成HH:mm格式
	 * 
	 * @param string
	 * @return
	 */
	public static String formatTime(String string) {
		string = string.replace("-", "/");
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		Date date = null;
		try {
			date = sdf.parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(date);
	}

	/**
	 * 比较两个日期的大小
	 *
	 * @param date1
	 * @param date2
	 * @return 1表示第一个日期在第二个日期之后，-1表示第一个日期在第二个日期之前，0表示相等
	 */
	public static int compareDate(String date1, String date2) {

		int result = -2;

		date1 = date1.replace("/", "-");
		date2 = date2.replace("/", "-");

		DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			Date dt1 = df1.parse(date1);
			Date dt2 = df2.parse(date2);
			if (dt1.getTime() > dt2.getTime()) {
				result = 1;
			} else if (dt1.getTime() < dt2.getTime()) {
				result = -1;
			} else {
				result = 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return result;
	}
	

}
