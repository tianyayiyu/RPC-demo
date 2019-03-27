package com.zlu.RPCSsrvice;

import data.RequestData;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 启动服务端
 */
public class RPCSsrvice {
    private Map<String,Object> serviceMap=new HashMap<String, Object>(32);

    //创建线程池
    private ThreadPoolExecutor executor=new
            ThreadPoolExecutor(8,20,200, TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(10));
    /**
     * 暴露服务接口的方法
     * @param interfaceClass
     * @param instance
     */
    public void publicService(Class<?> interfaceClass,Object instance){
        this.serviceMap.put(interfaceClass.getName(),instance);
    }

    /**
     * 启动服务端
     * @param port
     */
    public void start(int port){
        try {
            //监听客户端发送数据
            ServerSocket socket = new ServerSocket();
            socket.bind(new InetSocketAddress(port));

            System.out.println("server is starring...........");
            while(true){
                executor.execute(new Task(socket.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Task implements Runnable{

        private Socket client;

        public Task(Socket client){
            this.client=client;
        }
        public void run() {
            try {
                //反序列话流
                ObjectInputStream deSerialer=new ObjectInputStream(client.getInputStream());
                //序列化流
                ObjectOutputStream serialer=new ObjectOutputStream(client.getOutputStream());

                //通过网络连接获取客户端发送的网络数据
                RequestData requestData= (RequestData) deSerialer.readObject();

                //通过暴露服务的列表获取接口对应的实例
                Object instance = serviceMap.get(requestData.getInterfaceName());
               //反射操作
                Method method = instance.getClass().getDeclaredMethod(requestData.getMethodName(),requestData.getParameterTypes());

                Object result = method.invoke(instance, requestData.getParameter());
                //序列化到网络 返回到客户端
                serialer.writeObject(result);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
