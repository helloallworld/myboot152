package com.wumin.boot152.common.entity.query;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 接收前端传入参数接收类
 */
public class QueryParams implements Serializable {

	private Map query = Maps.newHashMap();
	private Integer page = 1;
	private Integer pageSize = 10;
	private List<Order> orders = Lists.newArrayList();

	public Integer getPage() {
		return page;
	}

	public void put(Object key, Object value){
		this.getQuery().put(key, value);
	}

	public void setPage(Integer page) {
		this.page = page;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public void setQuery(Map query) {
		this.query = query;
	}

	public Map getQuery() {
		return query;
	}

	public Long getStarRow() {
		return Long.valueOf((page - 1) * pageSize);
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
}
