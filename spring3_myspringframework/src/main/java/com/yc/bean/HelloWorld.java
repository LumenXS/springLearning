package com.yc.bean;

import com.yc.springframework.stereotype.MyComponent;
import com.yc.springframework.stereotype.MyPostConstruct;
import com.yc.springframework.stereotype.MyPreDestroy;



@MyComponent
public class HelloWorld {
    @MyPostConstruct
    public void  setup(){

        System.out.println("容器构造后的setup");
    }

    @MyPreDestroy
    public void destroy(){

        System.out.println("容器销hui前");
    }

    public  HelloWorld(){
        System.out.println(" hello world 构造");
    }

    public void  show(){
        System.out.println("show");
    }

}
