package com.wumin.boot152.configuration.queryparams;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import com.wumin.boot152.common.entity.query.Order;
import com.wumin.boot152.common.entity.query.QueryParams;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *对前端传过来的数据进行加工组装
 */
public class QueryParamsArgumentResolver implements WebArgumentResolver {
	private Type type = new TypeToken<ArrayList<Order>>() {
	}.getType();

	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
		if (!methodParameter.getParameterType().equals(QueryParams.class)) {
			//接收参数的数据类型不是QueryParams时不走下面
			return UNRESOLVED;
		}
		Map<String, String[]> parameters = webRequest.getParameterMap();
		Map<String, Object> map = new HashMap<>();
		for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
			String[] values = entry.getValue();
			if (!"t".equals(entry.getKey())) {
				if (ArrayUtils.getLength(values) == 1) {
					map.put(entry.getKey(), values[0]);
				} else {
					map.put(entry.getKey(), values);
				}
			}

		}

		QueryParams queryParams = new QueryParams();
		parsePage(map, queryParams);
		parseOrders(map, queryParams);

		queryParams.setQuery(map);
		return queryParams;
	}

	private void parseOrders(Map<String, Object> map, QueryParams queryParams) {
		String orders = (map.get("orders") == null ? null : map.get("orders").toString());
		if (StringUtils.isNotBlank(orders)) {
			Gson gson = new Gson();
			queryParams.setOrders(gson.<List<Order>> fromJson(orders, type));
		}
		map.remove("orders");
	}

	private void parsePage(Map<String, Object> map, QueryParams queryParams) {
		int pageSize = parseInt(map.get("pageSize") == null ? null : map.get("pageSize").toString(), 10);
		int currentPage = parseInt(map.get("page") == null ? null : map.get("page").toString(), 1);
		queryParams.setPageSize(pageSize);
		queryParams.setPage(currentPage);
		map.remove("pageSize");
		map.remove("page");
	}

	private int parseInt(String value, int defaultValue) {
		try {
			return Integer.valueOf(value);
		} catch (Throwable e) {
			return defaultValue;
		}
	}

}