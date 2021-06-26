package com.vbrug.fw4j.core.spring;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author vbrug
 * @since 1.0.0
 */
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        ctx = applicationContext;
    }


    public static ApplicationContext getApplicationContext() {
        return ctx;
    }


    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName) {
        return (T) ctx.getBean(beanName);
    }


    public static <T> T getBean(Class<T> clazz) {
        return ctx.getBean(clazz);
    }

    /**
     * 设置Mybatis自动提交事务
     * @return org.apache.ibatis.session.SqlSession
     */
    public static SqlSession getSqlSession() {
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) ctx.getBean("sqlSessionFactory");
        return sqlSessionFactory.openSession(true);
    }
}
