package com.example.uplibrary.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class TestStart {

    public static void main(String[] args) {

        //静态调用
        SuperStart superStart = new SuperStart();
        SuperStartManager manager = new SuperStartManager(superStart);
        manager.doWork();

        //动态代理,不需要提前创建代理类,可以是自己或manager或其他人
        final IStartAction startAction = new SuperStart();

        IStartAction proxy = (IStartAction) Proxy.newProxyInstance(startAction.getClass().getClassLoader(),
                startAction.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        return method.invoke(startAction,args);
                    }
                });

        proxy.doWork();
    }
}
