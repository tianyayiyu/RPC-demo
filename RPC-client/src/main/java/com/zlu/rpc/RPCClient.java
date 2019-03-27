package com.zlu.rpc;

import data.RequestData;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RPCClient {

    public static  <T> T getRemoreProxy(final Class<T>  interfaceClass, final InetSocketAddress address){
        //使用JDK的动态代理
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //使用BIO 同步阻塞IO
                try {
                    //连接服务器
                    Socket client = new Socket();
                    client.connect(address);
                    //使用网络的输入输出流，将数据序列化 通过网络传到服务端
                    try {
                        //序列化流
                        ObjectOutputStream serialer=new ObjectOutputStream(client.getOutputStream());
                        //反序列话流
                        ObjectInputStream deSerialer=new ObjectInputStream(client.getInputStream());

                        //包装参数 发送数据包
                        RequestData requestData = new RequestData();
                        requestData.setInterfaceName(interfaceClass.getName());
                        requestData.setMethodName(method.getName());
                        requestData.setParameterTypes(method.getParameterTypes());
                        requestData.setParameter(args);
                        //序列化到网络
                        serialer.writeObject(requestData);

                        //获取调用的返回结果
                        return deSerialer.readObject();

                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
}
