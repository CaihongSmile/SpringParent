package com.springmvc.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static final String RETURN_CODE = "return_code";
	public static final String RETURN_CODE_ERROR = "error";
	public static final String RETURN_MSG = "return_msg";
	public static final String BODY = "body";
	public static final String SUCCESS = "success";
	
	public static final String APPID = "branch_sh021_user6";
	public static final String APPKEY = "41f9e4b4-5f41-4ea9-9cca-1c27b1d4815d";
	public static final String GATEWAYHOST = "http://s3gw.cmbchina.com";
	public static final String BUCKETNAME = "branch_sh021_images.61";

	@SuppressWarnings("rawtypes")
	public static final Map<String, Map> paramMap = new HashMap<String, Map>();

	static {
		Map<String, String> isTrueOrFalseMap = new HashMap<String, String>();
		isTrueOrFalseMap.put("1", "是");
		isTrueOrFalseMap.put("2", "否");

	}

	@SuppressWarnings("unchecked")
	public static final String getValueFromMap(String type, String key) {
		Map<String, String> map = paramMap.get(type);
		if (map.get(key) != null) {
			return map.get(key);
		}
		return key;
	}

}
