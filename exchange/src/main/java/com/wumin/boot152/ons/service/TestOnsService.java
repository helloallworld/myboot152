package com.wumin.boot152.ons.service;

/**
 * ons调用时tag为testTag时的业务
 */
public interface TestOnsService {
    Object doTest(Integer id);
    Object doMapTest(Integer id,String type);
}
