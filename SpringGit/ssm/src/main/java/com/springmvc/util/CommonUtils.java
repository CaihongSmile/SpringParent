package com.springmvc.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class CommonUtils {
	/**
	 * 恢复数据库字段名称
	 * 
	 * @param str
	 * @return
	 */
	public static String recoverDbColumnName(String str) {
		if (str == null || "".equals(str.trim())) {
			return null;
		}
		int index = 0;
		int len = str.length();
		ArrayList<String> rs = new ArrayList<String>();
		for (int i = 1; i < len; i++) {
			if (Character.isUpperCase(str.charAt(i))) {
				rs.add(str.substring(index, i).toUpperCase());
				index = i;
			}
		}
		rs.add(str.substring(index, len).toUpperCase());
		return StringUtils.join(rs, "_");
	}
	
	/**
	 * 获取文件扩展名
	 * 
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}
	
	
	/**
	 * 取得当前日期的字符串 yyyy-MM-dd 
	 * 
	 * @return
	 */
	public static String getNowDateStr() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String reStr = df.format(new Date());
		return reStr;
	}
	
	/**
	 * 取得当前日期+1天的字符串 yyyy-MM-dd 
	 * 
	 * @return
	 */
	public static String getNowAfterOneDayDateStr() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		String nowDate = sf.format(c.getTime());
		c.add(Calendar.DAY_OF_MONTH, 1);
		String nowAfterOneDayDate = sf.format(c.getTime());
		return nowAfterOneDayDate;
	}
	
	/**
	 * 取得当前时间的字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getNowDateTimeStr() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String reStr = df.format(new Date());
		return reStr;
	}
	
	/**
	 * 将Bean转化成Map<String,Object>,如果bean内的属性对应值为null则不加入Map
	 * 
	 * @param obj
	 * @param allowBlank
	 *            是否允许[""]加入Map
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> convertBeanToMap(Object obj, boolean allowBlank) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();

		BeanInfo beanInfo;
		try {
			beanInfo = Introspector.getBeanInfo(obj.getClass());

			PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor pd : descriptors) {
				String key = pd.getName();
				if (!key.equals("class")) {
					Method getter = pd.getReadMethod();
					Object value = getter.invoke(obj);
					if (value == null) {
						continue;
					} else {
						if ("".equals(String.valueOf(value).trim())) {
							if (allowBlank) {
								map.put(key, "");
							} else {
								continue;
							}
						} else {
							map.put(key, value);
						}
					}
				}
			}

		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		return map;
	}

	//2013年11月第3周
	public static String getDayByWeek(String weekNo) {
		//System.out.println(weekNo);
		try {
			Pattern p = Pattern.compile("(.*)年(.*)月.*第(.*)周");  
	    	Matcher m = p.matcher(weekNo);
	    	m.find();
	    	String year = m.group(1);
	    	
			String month = m.group(2);
			if (month.length() == 1) {
				month = "0" + month;
			}
			String date =  m.group(3);
			String day = String.valueOf((Integer.parseInt(date) - 1) * 7 + 1);
			if (day.length() == 1) {
				day = "0" + day;
			}
			return year +"-" + month + "-" + day;
		} catch (Exception e) {
			e.printStackTrace(); 
			return null;
		}
	}
	
	public static String isConstainPoint(String param){
		String result = param;
		if(result.contains(".")){
			result = result.substring(0, result.indexOf('.'));
		} 
		return result;
	}
	
	public static String DgDateFormat(String Date){
		if(Date==null || Date.equals("")){
			return "";
		}
		Date.trim();
		StringBuffer sb = new StringBuffer();
		if(Date.length() == 8) {
			String year = Date.substring(0,4);
			String month = Date.substring(4,6);
			String day = Date.substring(6,8);
			sb.append(year);
			sb.append("年");
			sb.append(month);
			sb.append("月");
			sb.append(day);
			sb.append("日");
			return sb.toString();
		}
		if(Date.contains("-")) {
			String[] Time = Date.split("-");
			if(Time.length<3){
				return Date;
			}
			sb.append(Time[0]);
			sb.append("年");
			sb.append(Time[1]);
			sb.append("月");
			sb.append(Time[2]);
			sb.append("日");
		}
		
		return sb.toString();
	}
	
	public static String DsDateFormat(String Date) {
		if(Date==null || Date.equals("")){
			return "";
		}
		Date.trim();
		if(Date.length() == 8) {
			return Date;
		}
		StringBuffer sb = new StringBuffer();
		if(Date.contains("-")) {
			String[] Time = Date.split("-");
			if(Time.length<3){
				return Date;
			}
			
			sb.append(Time[0]);
			if(Time[1].length()<2){
				sb.append("0" + Time[1]);
			} else {
				sb.append(Time[1]);
			}
			if(Time[2].length()<2){
				sb.append("0" + Time[2]);
			} else {
				sb.append(Time[2]);
			}
		}
		
		return sb.toString();
	}
	
	public static String DateUpload(String Date) {
		if(Date==null || Date.equals("")){
			return "";
		}
		Date.trim();
		StringBuffer sb = new StringBuffer();
		String[] dateArray;
		if(Date.contains("-")){
			dateArray = Date.split("-");
			if(dateArray.length >= 3) {
				String year = dateArray[0];
				String month = dateArray[1];
				String day = dateArray[2];
				sb.append(year);
				if(month.length() == 1){
					month = "0" + month;
				}
				sb.append("-");
				sb.append(month);
				if(day.length() == 1) {
					day = "0" + day;
				}
				day = day.substring(0,2);
				sb.append("-");
				sb.append(day);
				return sb.toString();
			}
		}
		if(Date.length() == 8) {
			String year = Date.substring(0,4);
			String month = Date.substring(4,6);
			String day = Date.substring(6,8);
			sb.append(year);
			sb.append("-");
			sb.append(month);
			sb.append("-");
			sb.append(day);
			return sb.toString();
		}
		return Date;
	}
	
	
	
	
}
