package org.zlu.demo;

import api.HelloApi;

/**
 * 具体的暴露接服务的实现类
 */
public class HelloService implements HelloApi {
    public String hello(String name) {
       return  "Hello ,"+name;
    }
}
