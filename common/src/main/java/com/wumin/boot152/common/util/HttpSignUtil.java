package com.wumin.boot152.common.util;

import com.aliyun.openservices.shade.com.alibaba.fastjson.JSONObject;
import com.wumin.boot152.common.enums.EnumAppKey;
import org.apache.commons.lang3.BooleanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class HttpSignUtil {


    /**
     * post通过消息体请求
     *
     * @param paramsBody
     * @param url
     * @return
     */
    public static String post(String paramsBody, String url, Boolean isContentTypeJson) {
        Map<String, String> contentParams = JSONObject.parseObject(paramsBody,Map.class);
        Long timestamp = DateUtils.getCurrentTimeMillis();

        Map<String, Object> signParams = new HashMap<String, Object>();
        if(isContentTypeJson){                       //如果是JSON则，则设置requestBody为key的正文
            signParams.put("requestBody",paramsBody);
        }
        else{
            signParams.putAll(contentParams);
        }
        signParams.put("appKey", EnumAppKey.ERP.getKey());
        signParams.put("timestamp", String.valueOf(timestamp));

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("appKey", EnumAppKey.ERP.getKey());
        headerMap.put("timestamp", String.valueOf(timestamp));
        if (BooleanUtils.isTrue(isContentTypeJson)) {
            headerMap.put("Content-type", "application/json; charset=UTF-8");
        }
        headerMap.put("Accept", "application/json");
        String sign = null;
        try {
            sign = SignUtil.sign(signParams, EnumAppKey.findByKey(EnumAppKey.ERP.getKey()).getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        headerMap.put("sign", sign);
        String response = null;
        if(isContentTypeJson) {                       //如果是JSON则，则设置requestBody为key的正文
            response = HttpUtil.post(url, paramsBody, headerMap, "UTF-8");
        }
        else{
            response = HttpUtil.post(url, contentParams, headerMap, "UTF-8");
        }
        return response;

    }


    /**
     * get请求
     *
     * @param contentParams
     * @param
     * @param url
     * @return
     */
    public static String    get(Map<String, String> contentParams, String url) {

        Long timestamp = DateUtils.getCurrentTimeMillis();

        Map<String, Object> signParams = new HashMap<String, Object>();
        if(contentParams!=null) {
            signParams.putAll(contentParams);
        }
        signParams.put("appKey", EnumAppKey.ERP.getKey());
        signParams.put("timestamp", String.valueOf(timestamp));

        Map<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("appKey", EnumAppKey.ERP.getKey());
        headerMap.put("timestamp", String.valueOf(timestamp));
        String sign = null;
        try {
            sign = SignUtil.sign(signParams, EnumAppKey.findByKey(EnumAppKey.ERP.getKey()).getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        headerMap.put("sign", sign);

        String response = HttpUtil.get(url, contentParams, headerMap, "UTF-8");

        return response;

    }

}
