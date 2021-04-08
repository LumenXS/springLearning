package com.yc.springframework.stereotype;

import javafx.scene.chart.ValueAxis;

import java.lang.annotation.*;

@Target(value = {ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyResourse {
   public String name() default "";
/**
 *@program: testSpring
 *@description:
 *@author: Lumen
 *@create: 2021-04-05 15:54
 */
}
