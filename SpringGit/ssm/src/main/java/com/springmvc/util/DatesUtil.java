package com.springmvc.util;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.text.SimpleDateFormat;
/*
 * 由于为了以后使用方便,所有方法的返回类型都设为了 java.util.Date 请在使用时根据自己的需要进行日期格式化处理,如:
 * 
 * import java.text.SimpleDateFormat;
 * SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 * String todayBegin = simpleDateFormat.format(DateUtils.getDayBegin());
 * System.out.println(todayBegin );
 * //输出结果为2017-10-26 00:00:00
/**
 * 日期工具类
 */
public class DatesUtil {
	
	// 获取本周的开始时间  String
	public static String getWeekBegin() {
		String weekBegin = "";
		Date date = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		weekBegin = simpleDateFormat.format(getDayStartTime(cal.getTime()));
		 
		return weekBegin;
	}
	
	// 获取本周的结束时间 String
	public static String getWeekEnd() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginDayOfWeek());
		cal.add(Calendar.DAY_OF_WEEK, 6);
		Date weekEndSta = cal.getTime();
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String weekEnd = simpleDateFormat.format(getDayEndTime(weekEndSta));
		
		return weekEnd;
	}
	
	// 获取本月的开始时间 String
	public static String getDayBeginOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dayBegin = simpleDateFormat.format(getDayStartTime(calendar.getTime()));
		
		return dayBegin;
	}

	// 获取本月的结束时间 String
	public static String getDayEndOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(getNowYear(), getNowMonth() - 1, day);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dayEnd = simpleDateFormat.format(getDayEndTime(calendar.getTime()));
		
		return dayEnd;
	}
	
	// 返回本月该季度的开始时间 String
	public static String getBeginSeasonDate() 
	{
		 Calendar cal = Calendar.getInstance();
	     int year = getNowYear();
		 int month = getNowMonth(); 
		 if(month >=1 && month <= 3 )
		 {
			 month = 1;
		 }
		 else if(month >=4 && month <= 6 )
		 {
			 month = 4;
		 }
		 else if(month >=7 && month <= 9 )
		 {
			 month = 7;
		 }
		 else if(month >=10 && month <= 12 )
		 {
			 month = 9;
		 }
		//设置年份
		cal.set(Calendar.YEAR,year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最小天数
		int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		//设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH, firstDay);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String seasonBegin = simpleDateFormat.format(getDayStartTime(cal.getTime()));
		
		return seasonBegin;
	}
	
	// 返回本月该季度的结束时间 String
	public static String getEndSeasonDate() 
	{
		 Calendar cal = Calendar.getInstance();
	     int year = getNowYear();
		 int month = getNowMonth(); 
		 if(month >=1 && month <= 3 )
		 {
			 month = 3;
		 }
		 else if(month >=4 && month <= 6 )
		 {
			 month = 6;
		 }
		 else if(month >=7 && month <= 9 )
		 {
			 month = 9;
		 }
		 else if(month >=10 && month <= 12 )
		 {
			 month = 12;
		 }
		//设置年份
	    cal.set(Calendar.YEAR,year);
	    //设置月份
	    cal.set(Calendar.MONTH, month-1);
	    //获取某月最大天数
	    int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    //设置日历中月份的最大天数
	    cal.set(Calendar.DAY_OF_MONTH, lastDay);

		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String seasonEnd = simpleDateFormat.format(getDayEndTime(cal.getTime()));
		 return seasonEnd; 
	}
	
	//获取半年的开始时间 String
	 public static String getBeginDayOfHalfYear()
	 {
	     Calendar cal = Calendar.getInstance();
	     int year = getNowYear();
		 int month = getNowMonth(); 
		 if(month >=1 && month <= 6 )
		 {
			 month = 1;
		 }
		 else
		 {
			 month = 7;
		 }
		//设置年份
		cal.set(Calendar.YEAR,year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最小天数
		int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
		//设置日历中月份的最小天数
		cal.set(Calendar.DAY_OF_MONTH, firstDay);

		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String halfYearBegin = simpleDateFormat.format(getDayStartTime(cal.getTime()));
		 return halfYearBegin; 
	}
	 
	//获取半年的结束时间 String
	 public static String getEndDayOfHalfYear()
	 {
	     Calendar cal = Calendar.getInstance();
	     int year = getNowYear();
		 int month = getNowMonth(); 
		 if(month >=1 && month <= 6 )
		 {
			 month = 6;
		 }
		 else
		 {
			 month = 12;
		 }
		//设置年份
	    cal.set(Calendar.YEAR,year);
	    //设置月份
	    cal.set(Calendar.MONTH, month-1);
	    //获取某月最大天数
	    int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	    //设置日历中月份的最大天数
	    cal.set(Calendar.DAY_OF_MONTH, lastDay);

		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String halfYearEnd = simpleDateFormat.format(getDayEndTime(cal.getTime()));
		 return halfYearEnd; 
	}
	
	// 获取本年的开始时间 String
	public static String getDayBeginOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String yearBegin = simpleDateFormat.format(getDayStartTime(cal.getTime()));
		
		return yearBegin;
	}

	// 获取本年的结束时间 String
	public static String getDayEndOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DATE, 31);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String yearEnd = simpleDateFormat.format(getDayEndTime(cal.getTime()));
		
		return yearEnd;
	}
	
	/** * 返回指定日期的月的第一天 
	* * @param year 
	* @param month 
	* @return
	 */ 
	 public static Date getFirstDayOfMonth2(Date date) { 
	     Calendar calendar = Calendar.getInstance();
		 calendar.setTime(date); 
		 calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1); 
		 return calendar.getTime();
	} 

	/** * 返回指定日期的月的最后一天
	*/
	 public static Date getLastDayOfMonth2(Date date) { 
	    Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date); 
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1); 
		calendar.roll(Calendar.DATE, -1); 
		return calendar.getTime(); 
	} 
	 
	/** * 返回指定年月的月的第一天 
	 * * @param year 
	 * @param month
	 * @return 
	 */ 
	 public static Date getFirstDayOfMonth2(Integer year, Integer month)
	 {
		 Calendar calendar = Calendar.getInstance(); 
		 if (year == null) { 
		   year = calendar.get(Calendar.YEAR); 
		 } 
		 if (month == null) { 
		     month = calendar.get(Calendar.MONTH); 
		 } 
		 calendar.set(year, month, 1); 
		 return calendar.getTime(); 
	}

	/** * 返回指定年月的月的最后一天 
	 * * * @param year 
	 * * @param month
	 *  * @return 
	 *  */ 
	 public static Date getLastDayOfMonth2(Integer year, Integer month)
	 {
		 Calendar calendar = Calendar.getInstance(); 
		 if (year == null) 
		 { 
			 year = calendar.get(Calendar.YEAR); 
	     } 
		 if (month == null)
		 { 
			 month = calendar.get(Calendar.MONTH);
		 } 
		 calendar.set(year, month, 1); 
		 calendar.roll(Calendar.DATE, -1); return calendar.getTime();
	} 

	/** * 返回指定日期的季度 * * 
	 * @param date 
	 * * @return 
	 * */ 
	 public static int getQuarterOfYear(Date date) 
	 { 
		 Calendar calendar = Calendar.getInstance(); 
		 calendar.setTime(date);
		 return calendar.get(Calendar.MONTH) / 3 + 1;
    } 

	/** * 返回指定日期的季的第一天
	*/ 
	public static Date getFirstDayOfQuarter(Date date) 
	{
		Calendar calendar = Calendar.getInstance(); 
		calendar.setTime(date); 
		return getFirstDayOfQuarter(calendar.get(Calendar.YEAR), getQuarterOfYear(date)); 
	} 
	
	/** 
	* 返回指定年季的季的第一天 
	* * @param year * 
	@param quarter 
	* @return 
	*/ 
	public static Date getFirstDayOfQuarter(Integer year, Integer quarter) 
	{
	    Calendar calendar = Calendar.getInstance();
	    Integer month = new Integer(0); 
		if (quarter == 1) { 
		   month = 1 - 1; 
		} 
		else if (quarter == 2)
		{ 
		   month = 4 - 1;
		} 
		else if (quarter == 3)
		{ 
		   month = 7 - 1;
	    } 
		else if (quarter == 4) 
		{ 
		   month = 10 - 1;
		} 
		else 
		{ 
		    month = calendar.get(Calendar.MONTH);
		} 
		return getFirstDayOfMonth2(year, month); 
   } 
		
		/** * 返回指定日期的季的最后一天 
		* * @param year
		* @return 
		*/
		public static Date getLastDayOfQuarter(Date date) { 
		    Calendar calendar = Calendar.getInstance(); 
			calendar.setTime(date); 
			return getLastDayOfQuarter(calendar.get(Calendar.YEAR), getQuarterOfYear(date)); 
		}
		/** * 返回指定年季的季的最后一天 
		* * @param year
		* @param quarter 
		* @return 
		*/ 
		public static Date getLastDayOfQuarter(Integer year, Integer quarter) { 
		     Calendar calendar = Calendar.getInstance(); 
			 Integer month = new Integer(0); 
			 if (quarter == 1)
			 {
			    month = 3 - 1;
			 } 
			 else if (quarter == 2)
			 { 
			    month = 6 - 1;
			 } 
			 else if (quarter == 3)
			 { 
			    month = 9 - 1;
			 } 
			 else if (quarter == 4) 
			 { 
			    month = 12 - 1;
			 } 
			 else 
			 {
	   		     month = calendar.get(Calendar.MONTH); 
		     } 
			 return getLastDayOfMonth2(year, month); 
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	// 获取本周的开始时间
	@SuppressWarnings("unused")
	public static Date getBeginDayOfWeek() {
		Date date = new Date();
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek);
		return getDayStartTime(cal.getTime());
	}

	// 获取本周的结束时间
	public static Date getEndDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginDayOfWeek());
		cal.add(Calendar.DAY_OF_WEEK, 6);
		Date weekEndSta = cal.getTime();
		return getDayEndTime(weekEndSta);
	}
	
	// 获取本月的开始时间
	public static Date getBeginDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		return getDayStartTime(calendar.getTime());
	}

	// 获取本月的结束时间
	public static Date getEndDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(getNowYear(), getNowMonth() - 1, day);
		return getDayEndTime(calendar.getTime());
	}
	
	// 获取本年的开始时间
	public static Date getBeginDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);
		return getDayStartTime(cal.getTime());
	}

	// 获取本年的结束时间
	public static Date getEndDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DATE, 31);
		return getDayEndTime(cal.getTime());
	}
	
	// 返回某月该季度的第一个月
	public static Date getFirstSeasonDate(Date date) {
		final int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int sean = SEASON[cal.get(Calendar.MONTH)];
		cal.set(Calendar.MONTH, sean * 3 - 3);
		return cal.getTime();
	}

	
	
	
	
	
	
	
	
	
	// 获取当天的开始时间
	public static Date getDayBegin() {
		Calendar cal = new GregorianCalendar(); 
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// 获取当天的结束时间
	public static Date getDayEnd() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	// 获取昨天的开始时间
	public static Date getBeginDayOfYesterday() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayBegin());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	// 获取昨天的结束时间
	public static Date getEndDayOfYesterDay() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayEnd());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	// 获取明天的开始时间
	public static Date getBeginDayOfTomorrow() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayBegin());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	// 获取明天的结束时间
	public static Date getEndDayOfTomorrow() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayEnd());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}


	// 获取上周的开始时间
	@SuppressWarnings("unused")
	public static Date getBeginDayOfLastWeek() {
		Date date = new Date();
		if (date == null) {
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek - 7);
		return getDayStartTime(cal.getTime());
	}

	// 获取上周的结束时间
	public static Date getEndDayOfLastWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginDayOfLastWeek());
		cal.add(Calendar.DAY_OF_WEEK, 6);
		Date weekEndSta = cal.getTime();
		return getDayEndTime(weekEndSta);
	}


	// 获取上月的开始时间
	public static Date getBeginDayOfLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 2, 1);
		return getDayStartTime(calendar.getTime());
	}

	// 获取上月的结束时间
	public static Date getEndDayOfLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 2, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(getNowYear(), getNowMonth() - 2, day);
		return getDayEndTime(calendar.getTime());
	}


	// 获取某个日期的开始时间
	public static Timestamp getDayStartTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	// 获取某个日期的结束时间
	public static Timestamp getDayEndTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTimeInMillis());
	}

	// 获取今年是哪一年
	public static Integer getNowYear() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return Integer.valueOf(gc.get(1));
	}

	// 获取本月是哪一月
	public static int getNowMonth() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return gc.get(2) + 1;
	}

	// 两个日期相减得到的天数
	public static int getDiffDays(Date beginDate, Date endDate) {
		if (beginDate == null || endDate == null) {
			throw new IllegalArgumentException("getDiffDays param is null!");
		}
		long diff = (endDate.getTime() - beginDate.getTime())
				/ (1000 * 60 * 60 * 24);
		int days = new Long(diff).intValue();
		return days;
	}

	// 两个日期相减得到的毫秒数
	public static long dateDiff(Date beginDate, Date endDate) {
		long date1ms = beginDate.getTime();
		long date2ms = endDate.getTime();
		return date2ms - date1ms;
	}

	// 获取两个日期中的最大日期
	public static Date max(Date beginDate, Date endDate) {
		if (beginDate == null) {
			return endDate;
		}
		if (endDate == null) {
			return beginDate;
		}
		if (beginDate.after(endDate)) {
			return beginDate;
		}
		return endDate;
	}

	// 获取两个日期中的最小日期
	public static Date min(Date beginDate, Date endDate) {
		if (beginDate == null) {
			return endDate;
		}
		if (endDate == null) {
			return beginDate;
		}
		if (beginDate.after(endDate)) {
			return endDate;
		}
		return beginDate;
	}


	// 返回某个日期下几天的日期
	public static Date getNextDay(Date date, int i) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
		return cal.getTime();
	}

	// 返回某个日期前几天的日期
	public static Date getFrontDay(Date date, int i) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
		return cal.getTime();
	}

	// 获取某年某月到某年某月按天的切片日期集合(间隔天数的集合)
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getTimeList(int beginYear, int beginMonth, int endYear,
			int endMonth, int k) {
		List list = new ArrayList();
		if (beginYear == endYear) {
			for (int j = beginMonth; j <= endMonth; j++) {
				list.add(getTimeList(beginYear, j, k));
			}
		} else {
			{
				for (int j = beginMonth; j < 12; j++) {
					list.add(getTimeList(beginYear, j, k));
				}
				for (int i = beginYear + 1; i < endYear; i++) {
					for (int j = 0; j < 12; j++) {
						list.add(getTimeList(i, j, k));
					}
				}
				for (int j = 0; j <= endMonth; j++) {
					list.add(getTimeList(endYear, j, k));
				}
			}
		}
		return list;
	}

	// 获取某年某月按天切片日期集合(某个月间隔多少天的日期集合)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getTimeList(int beginYear, int beginMonth, int k) {
		List list = new ArrayList();
		Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
		int max = begincal.getActualMaximum(Calendar.DATE);
		for (int i = 1; i < max; i = i + k) {
			list.add(begincal.getTime());
			begincal.add(Calendar.DATE, k);
		}
		begincal = new GregorianCalendar(beginYear, beginMonth, max);
		list.add(begincal.getTime());
		return list;
	}

}


 

