package com.zlu;

import api.HelloApi;
import com.zlu.RPCSsrvice.RPCSsrvice;
import org.zlu.demo.HelloService;

/**
 * Hello world!
 *
 */
public class AppServer
{
    public static void main( String[] args )
    {
        RPCSsrvice ssrvice = new RPCSsrvice();
        //发布服务
        ssrvice.publicService(HelloApi.class, new HelloService());
        //启动服务
        ssrvice.start(12345);
    }
}
