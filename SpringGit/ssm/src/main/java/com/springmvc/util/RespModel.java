package com.springmvc.util;

public class RespModel {
	
	private boolean success;
	
	private String msg;
		
	private Object content;
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}
	
	public static RespModel newInstance(boolean success, String msg, Object content){
		RespModel rm = new RespModel();
		rm.setSuccess(success);
		rm.setMsg(msg);
		rm.setContent(content);
		return rm;
	}

}
