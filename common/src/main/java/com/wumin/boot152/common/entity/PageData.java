package com.wumin.boot152.common.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class PageData<T> implements Serializable {
	/**
	 * 创建一个新的实例 PageData.
	 *
	 * @param page
	 *            当前页
	 * @param pageSize
	 *            页面展示数量
	 */
	public PageData(Integer page, Integer pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}

	// 数据集合
	List<T> list = new ArrayList<T>();
	/**
	 * 总页数
	 */
	private Integer pagesCount = 1;
	/**
	 * 总数量
	 */
	private Integer totalCount = 1;
	/**
	 * 当前页
	 */
	private Integer page = 1;
	/**
	 * 页面展示数量
	 */
	private Integer pageSize = 10;

	public Integer getPage() {
		return page;
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

	public Integer getPagesCount() {
		this.pagesCount = ((this.getTotalCount() - 1) / pageSize) + 1;
		return pagesCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
