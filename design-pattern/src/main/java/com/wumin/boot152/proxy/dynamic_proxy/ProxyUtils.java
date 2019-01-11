package com.wumin.boot152.proxy.dynamic_proxy;

public class ProxyUtils {
    public  static void before(){
        System.out.println("————————————");
        System.out.println("before real method run");
        System.out.println("————————————");
    }
    public static void after(){
        System.out.println("————————————");
        System.out.println("after real method run");
        System.out.println("————————————");
    }
}
