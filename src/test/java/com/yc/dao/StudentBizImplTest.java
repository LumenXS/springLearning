package com.yc.dao;

import com.yc.Appconfig;
import com.yc.biz.StudentBizImpl;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class StudentBizImplTest extends TestCase {


    ApplicationContext ac;
    private StudentBizImpl studentBiz;
    @Before
    public void setUp() throws Exception {
        ac = new AnnotationConfigApplicationContext(Appconfig.class);
        studentBiz=(StudentBizImpl) ac.getBean("studentBizImpl");
    }

    @Test
    public void testAdd() {
        studentBiz.add("李四");
    }

    public void testUpdate() {
        studentBiz.update("李四");
    }
}