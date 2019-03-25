package com.wumin.boot152.nginxTest.services;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Properties;

@Service
public class AddPropertiesService {
    @Value("${shaobinyun.url}")
    String url;
    private String filePath="/application.properties";




    public void addProperties(String url) {
            Properties props = new Properties();
            try {

                InputStream in = this.getClass().getResourceAsStream(filePath);
                props.load(in);
                // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
                // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
                String profilepath=this.getClass().getResource("/").getPath()+filePath;
                OutputStream fos = new FileOutputStream(profilepath);
                props.setProperty("shaobinyun.url", url);
                // 以适合使用 load 方法加载到 Properties 表中的格式，
                // 将此 Properties 表中的属性列表（键和元素对）写入输出流
                props.store(fos, "Update '" + "shaobinyun.url" + "' value");
            } catch (IOException e) {
                System.err.println("属性文件更新错误");
            }
    }
    public String showUrl(){
        if (StringUtils.isBlank(url)){
            System.out.println("无url");
        }
        return url;
    }

    public String showUrl2() {
        Properties props = new Properties();
        try {
            InputStream in = this.getClass().getResourceAsStream("application.properties");
            props.load(in);
            String value = props.getProperty("shaobinyun.url");
            System.out.println("shaobinyun.url" +"键的值是："+ value);
            return  value;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
