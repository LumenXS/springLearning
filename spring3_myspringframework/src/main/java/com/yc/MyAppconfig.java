package com.yc;


import com.yc.bean.HelloWorld;
import com.yc.springframework.stereotype.MyBean;
import com.yc.springframework.stereotype.MyComponentScan;
import com.yc.springframework.stereotype.MyConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@MyConfiguration //表示当前类是一个配置类
@MyComponentScan(basePackages={"com.yc.bean","com.yc.biz"}) //将来要托管的bean要扫描的包及子包
public class MyAppconfig {

    @MyBean
    public HelloWorld hw(){
        return  new HelloWorld();
    }

}
