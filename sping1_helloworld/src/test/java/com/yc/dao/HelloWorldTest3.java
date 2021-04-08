package com.yc.dao;

import com.yc.Appconfig;
import com.yc.biz.HelloWorld;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)//使用junit4进行测试
@ContextConfiguration(classes = {Appconfig.class})//加载配置文件
@DependsOn("helloworld")

public class HelloWorldTest3 {//测试用例

    @Autowired
    private  HelloWorld hw;

    @Autowired
    private  HelloWorld hw2;

    @Test
    public void testHello() {
        System.out.println("aaa");
        hw.hello();
        System.out.println(hw.hashCode()+"\t"+hw2.hashCode());
    }
}