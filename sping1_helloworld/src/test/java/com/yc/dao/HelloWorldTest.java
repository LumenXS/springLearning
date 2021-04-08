package com.yc.dao;

import com.yc.Appconfig;
import com.yc.biz.HelloWorld;
import junit.framework.TestCase;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HelloWorldTest extends TestCase {//测试用例

    private ApplicationContext ac;// spring 容器

    @Override
    @Before
    public void setUp() {
        //ApplicationContext acx = new AnnotationConfigApplicationContext（Appconfig.class);
        //AnnotationConfigApplicationContext  基于注解的配置容器
       ac= new AnnotationConfigApplicationContext(Appconfig.class);
       //读取 Appconfig.calss  -> baskPackages ="com.yc"  得到要扫描的路径
        //检查这些包中是否有@Conponet注解，如果有，则实例化
        //  存到一个map<String,Object> ==ac

    }

    public void testHello() {
        HelloWorld hw = (HelloWorld) ac.getBean("helloWorld");
        hw.hello();
        // spring容器是单例模式
    }
}