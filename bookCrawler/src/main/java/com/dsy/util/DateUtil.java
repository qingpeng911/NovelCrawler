package com.dsy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 日期相关工具类
 */
public class DateUtil {
	private static final Log log = LogFactory.getLog(DateUtil.class);

	/**
     * 通过时间秒毫秒数判断两个时间的间隔
     */
    public static int diffDays(Date date1,Date date2) {
        return (int) (Math.abs(date2.getTime() - date1.getTime()) / (1000*3600*24));
    }
	
	/**
	 * 根据出生日期获取生肖
	 */
	public static String getShenxiaoByBirth(Date birth) {
		int year = getYear(birth);
		if (year < 1900) {
			return "未知";
		}
		Integer start = 1900;
		String[] years = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪" };
		return years[(year - start) % years.length];
	}

	/**
	 * 根据日期获取年份
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}
	
	/**
	 * 根据日期获取日期字符串
	 */
	public static String getDateStr(String regx, Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(regx);
		return format.format(date);
	}

	/**
	 * 根据年龄反推生日
	 */
	public static Date getBirthByAge(Integer age) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -age);
		return c.getTime();
	}

	/**
	 * 判断两个日期是否为同一天
	 */
	public static boolean isSameDay(Date time1, Date time2) {
		Calendar date1 = Calendar.getInstance();
		date1.setTime(time1);
		Calendar date2 = Calendar.getInstance();
		date2.setTime(time2);

		boolean isSameYear = date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth && date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH);

		return isSameDate;
	}
	
	/**
	 * 判断指定日期是否是今天
	 */
	public static boolean isToday(Date time) {
		Calendar date1 = Calendar.getInstance();
		Calendar date2 = Calendar.getInstance();
		date2.setTime(time);
		
		boolean isSameYear = date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR);
		boolean isSameMonth = isSameYear && date1.get(Calendar.MONTH) == date2.get(Calendar.MONTH);
		boolean isSameDate = isSameMonth && date1.get(Calendar.DAY_OF_MONTH) == date2.get(Calendar.DAY_OF_MONTH);
		
		return isSameDate;
	}

	/**
	 * 获取当天的下一天
	 */
	public static Date getNextDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +1);// +1今天的时间加一天
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 获取当天的前一天
	 */
	public static Date getPrevDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);// +1今天的时间加一天
		date = calendar.getTime();
		return date;
	}

	/**
	 * 计算两个日期之间相差的天数
	 */
	public static int daysBetween(Date from, Date to) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(from);
		long time1 = cal.getTimeInMillis();
		cal.setTime(to);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 获取指定时间的下一个小时
	 */
	public static Date getNextHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, +1);// +1 今天的时间加一小时
		date = calendar.getTime();
		return date;
	}
	
	/**
	 * 获取指定时间的上一个小时
	 */
	public static Date getPrevHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, -1);// 后退一个小时
		date = calendar.getTime();
		return date;
	}
	
	
	/**
	 * 获取当前时间的整点小时
	 */
	public static Date getNowWholeHour() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 获取今天与当前同时刻的整点日期
	 */
	public static Date getJTDate() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 获取当前系统时间的指定某小时的整点日期
	 */
	public static Date getJTDateByHour(int hour) {
		return getJTDateByHour(new Date(), hour);
	}
	
	/**
	 * 获取指定日期当天的指定某小时的整点日期
	 */
	public static Date getJTDateByHour(Date dateTime, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(dateTime);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 获取昨天与当前同时刻的整点日期
	 */
	public static Date getZTDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 获取当前时间的昨天指定某小时的整点日期
	 */
	public static Date getZTDateByHour(int hour) {
		return getZTDateByHour(new Date(), hour);
	}
	
	/**
	 * 获取指定日期的昨天指定某小时的整点日期
	 */
	public static Date getZTDateByHour(Date dateTime, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(dateTime);
		c.add(Calendar.DAY_OF_MONTH, -1);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 获取前天与当前同时刻的整点日期
	 */
	public static Date getQTDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -2);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	
	/**
	 * 获取当前时间前天指定某小时的整点日期
	 */
	public static Date getQTDateByHour(int hour) {
		return getQTDateByHour(new Date(), hour);
	}
	
	/**
	 * 获取指定时间的前天指定某小时的整点日期
	 */
	public static Date getQTDateByHour(Date dateTime, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(dateTime);
		c.add(Calendar.DAY_OF_MONTH, -2);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 获取指定时间的上一个小时
	 */
	public static Date getPrevWholeHour() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, -1);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	

	/**
	 * 根据字符串获取对应的Date对象
	 * @throws ParseException
	 */
	public static Date getDateByStr(String dateStr, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(dateStr);
	}
	
	/**
	 * 获取指定日期的指定小时整点
	 * hour:0-23
	 */
	public static Date getWholeHourDate(Date date,int hour){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, hour); 
		c.set(Calendar.SECOND, 0); 
		c.set(Calendar.MINUTE, 0); 
		c.set(Calendar.MILLISECOND, 0); 
		return c.getTime();
	}

	/**
	 * 获取当天的日期不带时分秒
	 * @throws ParseException
	 */
	public static Date getToday() {
		return getZeroPointDate(new Date());
	}

	/**
	 * 获取当前小时(24小时制)
	 */
	public static int getNowHour() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获取指定日期的零点时刻
	 */
	public static Date getZeroPointDate(Date date){
		return getWholeHourDate(date,0);
	}
	
//	public static void main(String[] args) throws ParseException {
////		Calendar c = Calendar.getInstance();
////		c.set(Calendar.HOUR_OF_DAY,0);
////		c.add(Calendar.HOUR_OF_DAY, -1);
////		c.set(Calendar.MINUTE, 0);
////		c.set(Calendar.SECOND, 0);
////		c.set(Calendar.MILLISECOND, 0);
////		System.out.println(getDateStr("yyyy-MM-dd HH:mm:ss", c.getTime()));
//		//System.out.println(getNowHour());
//	}

	/**
	 * 获取明天凌晨的时间点
	 */
	public static Date getMTZeroPointDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH,1);
		c.set(Calendar.HOUR_OF_DAY, 0); 
		c.set(Calendar.SECOND, 0); 
		c.set(Calendar.MINUTE, 0); 
		c.set(Calendar.MILLISECOND, 0); 
		return c.getTime();
	}

	/**
	 * 获取今天天凌晨的时间点
	 */
	public static Date getJTZeroPointDate() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0); 
		c.set(Calendar.SECOND, 0); 
		c.set(Calendar.MINUTE, 0); 
		c.set(Calendar.MILLISECOND, 0); 
		return c.getTime();
	}

	/**
	 * 获取昨天凌晨的时间点
	 */
	public static Date getZTZeroPointDate() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH,-1);
		c.set(Calendar.HOUR_OF_DAY, 0); 
		c.set(Calendar.SECOND, 0); 
		c.set(Calendar.MINUTE, 0); 
		c.set(Calendar.MILLISECOND, 0); 
		return c.getTime();
	}
	
	public static Date getZeroPointDate(Date dateTime,int addDay) {
		Calendar c = Calendar.getInstance();
		c.setTime(dateTime);
		c.add(Calendar.DAY_OF_MONTH,addDay);
		c.set(Calendar.HOUR_OF_DAY, 0); 
		c.set(Calendar.SECOND, 0); 
		c.set(Calendar.MINUTE, 0); 
		c.set(Calendar.MILLISECOND, 0); 
		return c.getTime();
	}

	/**
	 * 获取指定日期的昨天凌晨的时间点
	 */
	public static Date getZTZeroPointDate(Date dateTime) {
		if (dateTime == null) {
			return null;
		}
		return getZeroPointDate(dateTime, -1);
	}

	/**
	 * 获取指定日期的今天凌晨的时间点
	 */
	public static Date getJTZeroPointDate(Date dateTime) {
		return getZeroPointDate(dateTime, 0);
	}

	/**
	 * 获取指定日期的明天凌晨的时间点
	 */
	public static Date getMTZeroPointDate(Date dateTime) {
		return getZeroPointDate(dateTime, 1);
	}

	/**
	 * 获取当前时间的上一整点小时
	 */
	public static Date getPrevHourByNow() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR_OF_DAY, -1);// 后退一个小时
		c.set(Calendar.SECOND, 0); 
		c.set(Calendar.MINUTE, 0); 
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	/**
	 * 获取当前整点小时
	 */
	public static Date getNowHourByNow() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.SECOND, 0); 
		c.set(Calendar.MINUTE, 0); 
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取指定年月日小时的时间
	 */
	public static Date getDateByYearAndMonAndDayAndHour(int year, int month, int day, int hour) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month-1);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.SECOND, 0); 
		c.set(Calendar.MINUTE, 0); 
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取指定日期前addDay天的hour整点小时的日期
	 */
	public static Date getBeforeDate(Date date,int addDay, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, addDay);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.SECOND, 0); 
		c.set(Calendar.MINUTE, 0); 
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 获取指定日期的当前整点小时时刻
	 */
	public static Date getTimelyHour(Date date) {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.SECOND, 0); 
		c.set(Calendar.MINUTE, 0); 
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	/**
	 * 获取指定日期范围内的日期集合
	 */
	public static List<Date> getDateListInclude(Date from, Date to) {
		List<Date> dates = new ArrayList<>();
		if (from != null && to != null && (from.getTime() <= to.getTime())) {
			Date nextday = from;
			int num = 0;
			while (nextday.getTime() <= to.getTime()) {
				num++;
				dates.add(nextday);
				nextday = DateUtil.getNextDay(nextday);
				if (num >= 60) {
					break;
				}
			}
		}
		return dates;
	}
	
	/**
	 * 获取指定日期范围内的日期集合(前闭后开)
	 */
	public static List<Date> getDateListNotEqTo(Date from, Date to) {
		List<Date> dates = new ArrayList<>();
		if (from != null && to != null && (from.getTime() < to.getTime())) {
			Date nextday = from;
			int num = 0;
			while (nextday.getTime() < to.getTime()) {
				num++;
				dates.add(nextday);
				nextday = DateUtil.getNextDay(nextday);
				if (num >= 60) {
					break;
				}
			}
		}
		return dates;
	}

	/**
	 * 获取指定日期的指定小时整点的时间
	 */
	public static Date getDateByHour(Date date, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.SECOND, 0); 
		c.set(Calendar.MINUTE, 0); 
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}

	/**
	 * 将指定日期按10分钟分割成数组
	 */
	public static List<Date> splitDayBy10min(Date date) {
		List<Date> dates = new ArrayList<>();
		if (date != null) {
			Date next = getNext10Min(date);
			while (next.getTime() <= getNextDay(date).getTime()) {
				dates.add(next);
				next = DateUtil.getNext10Min(next);
			}
		}
		return dates;
	}

	/**
	 * 获取指定日期的下10分钟的时刻
	 */
	public static Date getNext10Min(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, 10);// 增加10分钟
		date = calendar.getTime();
		return date;
	}
	

	public static void main(String[] args) {
//		System.out.println(getDateStr("yyyy-MM-dd HH:mm:ss",DateUtil.getDateByYearAndMonAndDayAndHour(2016,10,11,2)));
//		System.out.println(getDateStr("yyyy-MM-dd HH:mm:ss",DateUtil.getBeforeDate(new Date(),-12,1)));
//		System.out.println(DateUtil.getZeroPointDate(new Date(), -2));
//		System.out.println(DateUtil.getZeroPointDate(new Date(), -1));
//		System.out.println(getTimelyHour(new Date()));
//		System.out.println(splitDayBy10min(getJTZeroPointDate()));
		System.out.println(getYesterday(new Date()));
	}

	/**
	 * 获取昨天的日期
	 */
	public static Date getYesterday(Date date) {
		return getBeforeDate(date, -1, 0);
	}

	/**
	 * 获取指定日期的小时（24小时制）
	 */
	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取指定日期的天数
	 */
	public static int getDay(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取指定日期的月份
	 */
	public static int getMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MONTH)+1;
	}
	
    /** 
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
