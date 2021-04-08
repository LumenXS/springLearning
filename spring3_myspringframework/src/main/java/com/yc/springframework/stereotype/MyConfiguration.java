package com.yc.springframework.stereotype;


import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface MyConfiguration {
/**
 *@program: testSpring
 *@description:
 *@author: Lumen
 *@create: 2021-04-05 15:54
 */
}
