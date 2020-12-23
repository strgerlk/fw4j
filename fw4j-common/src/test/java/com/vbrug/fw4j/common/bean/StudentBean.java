package com.vbrug.fw4j.common.bean;

import com.vbrug.fw4j.common.util.BeanUtils;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class StudentBean {

    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }


    public static void main(String[] args) {
        StudentBean studentBean1 = new StudentBean();
        StudentBean studentBean2 = new StudentBean();
        studentBean1.setAge("18");
        studentBean1.setName("李白");
        BeanUtils.copyProperties(studentBean1, studentBean2);
    }
}
