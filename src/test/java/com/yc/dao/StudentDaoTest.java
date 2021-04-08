package com.yc.dao;

import com.yc.biz.StudentBizImpl;
import junit.framework.TestCase;

public class StudentDaoTest extends TestCase {

    private  StudentDao studentDao;
    private StudentBizImpl studentBiz;



    public void setUp() throws Exception {

        studentDao = new StudentDaoJpaImpl();

        studentBiz = new StudentBizImpl();
        studentBiz.setStudentDao(studentDao);


    }

    public void testAdd() {
        studentDao.add("张三");
    }

    public void testUpdate() {
        studentDao.update("张三");
    }


}