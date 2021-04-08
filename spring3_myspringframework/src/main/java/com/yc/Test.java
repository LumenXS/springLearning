package com.yc;

import com.yc.bean.HelloWorld;
import com.yc.springframework.context.MyAnnotationConfigApplicationContext;
import com.yc.springframework.context.MyApplicationContext;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Test {
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InvocationTargetException, IOException, ClassNotFoundException {
        MyApplicationContext ac = new MyAnnotationConfigApplicationContext(MyAppconfig.class);

        //HelloWorld hw = (HelloWorld) ac.getBean("hw");
        //hw.show();

    }

}
