package com.wumin.boot152.common.util;

/**
 * Created by zhujianbo on 2018/8/6.
 */

import org.apache.commons.lang3.StringUtils;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * ClassName: SignUtil
 * @Description: 接口签名工具类
 * 签名规则：
 * md5(appSecret+参数1+值1+参数2+值2+参数3+值3+…)
 * 参数按照字母升序排列
 * 生成的签名字符串转化为大写
 * @author jihongjin
 * @since JDK 1.7
 * @version 1.0
 * @date 2016年10月19日 下午1:19:50
 */
public class SignUtil {

    public static String sign(Map<String, Object> paramValues, List<String> ignoreParamNames, String secret) throws Exception {
        try {
            if (ignoreParamNames != null && ignoreParamNames.size() > 0) {
                for (String ignoreParamName : ignoreParamNames) {
                    paramValues.remove(ignoreParamName);
                }
            }
          return sign(paramValues,secret);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }


    public static String sign(Map<String, Object> paramValues, String secret) throws Exception {
        try {
            StringBuilder sb = new StringBuilder();
            List<String> paramNames = new ArrayList<String>(paramValues.size());
            paramNames.addAll(paramValues.keySet());
            Collections.sort(paramNames);
            sb.append(secret);
            for (String paramName : paramNames) {
                String value = String.valueOf(paramValues.get(paramName));
                if(StringUtils.isNotBlank(value)){
                    sb.append(paramName).append(value);
                }
            }
            byte[] sha1Digest = getMD5Digest(sb.toString());
            return byte2hex(sha1Digest);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public static String sign(String content,String secret) throws Exception {
        try {
            byte[] sha1Digest = getMD5Digest(content);
            return byte2hex(sha1Digest);
        }
        catch (Exception e) {
            throw new Exception(e);
        }
    }


    private static byte[] getMD5Digest(String data) throws Exception {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(data.getBytes("UTF-8"));
        } catch (GeneralSecurityException gse) {
            throw new Exception(gse);
        }
        return bytes;
    }

    private static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }
}
