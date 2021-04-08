package com;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration //表示当前类是一个配置类
@ComponentScan(basePackages = {"com.huwei","com.mimi"}) //将来要托管的bean要扫描的包及子包
public class Appconfig {
    @Bean
    public Random r(){

        return  new Random();
    }


}
