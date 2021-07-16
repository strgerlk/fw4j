package com.vbrug.fw4j.core.spring;

import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author LK
 * @since 1.0
 */
public class SpringHelp {

    private static ApplicationContext context;

    private static OriginTrackedMapPropertySource ymlProperties;


    /**
     * 获取spring容器中的bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 获取spring容器中指定名称的Bean
     *
     * @param name
     * @param requiredType
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return getApplicationContext().getBean(name, requiredType);
    }

    /**
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static OriginTrackedMapPropertySource getYMLProperties(){
        return ymlProperties;
    }

    public static void setApplicationContext(ConfigurableApplicationContext context){
        SpringHelp.context = context;
        SpringHelp.ymlProperties = (OriginTrackedMapPropertySource) context.getEnvironment()
                .getPropertySources().get("applicationConfig: [classpath:/application.yml]");
    }

}
