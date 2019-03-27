package com.zlu;

import api.HelloApi;
import com.zlu.rpc.RPCClient;

import java.net.InetSocketAddress;

/**
 * Hello world!
 *
 */
public class AppClient
{
    public static void main( String[] args )
    {

        //这段代码一般被封装
        HelloApi proxy = RPCClient.getRemoreProxy(HelloApi.class, new InetSocketAddress("localhost", 12345));

        //调用远端服务器的方法并返回结果
        System.out.println(proxy.hello("zlu"));

    }
}
