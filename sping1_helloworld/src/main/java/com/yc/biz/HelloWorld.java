package com.yc.biz;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component       //只要加了这个注解，则这个类可以被spring容器托管
@Lazy
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public  class HelloWorld {  //创建这个类的对象

    public HelloWorld() {
        System.out.println("无参数构造方法");
    }

    public void hello(){

        System.out.println("hello world");
    }
}
