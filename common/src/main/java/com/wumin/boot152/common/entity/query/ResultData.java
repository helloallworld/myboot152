package com.wumin.boot152.common.entity.query;

import java.util.HashMap;

/**
 * 返回值封装类
 */
//@SuppressWarnings压制警告
@SuppressWarnings("rawtypes")
public class ResultData extends HashMap {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static ResultData ok() {
		return new ResultData();
	}

	public static ResultData WsOk() {
		return new ResultData("OK", 0l, 0);
	}

	public ResultData() {
		this.setStatus(200);
		this.setData("");
	}

	public ResultData(Object data) {
		this.setData(data);
		this.setStatus(200);
	}

	public ResultData(Object data, Long totalCount) {
		this.init(data, totalCount, 200);
	}

	public ResultData(Object data, Long totalCount, int status) {
		this.init(data, totalCount, status);
	}

	private void init(Object data, Long total, int status) {
		this.setData(data);
		this.setTotal(total);
		this.setStatus(status);
	}

	public void setData(Object data) {
		this.put("data", data);
	}
	public void setMessage(Object data) {
		this.put("message", data);
	}

	public void setTotal(Long total) {
		this.put("total", total);
	}

	public void setStatus(int status) {
		this.put("status", status);
	}

	public void setError(String error) {
		this.put("error", error);
	}

}
