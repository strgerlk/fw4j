package com.vbrug.fw4j.core.env;

/**
 * 应用上下文
 *
 * @author vbrug
 * @since 1.0.0
 */
public class ApplicationContext {

    // 系统环境
    private SystemEnvironment systemEnvironment = new SystemEnvironment();

    // 项目路径
    private String applicationPath;

    // 单例
    private static ApplicationContext context;

    // 私有化构造方法，单例模式
    private ApplicationContext(){}


    /**
     * 初始化应用上下文环境
     *
     * @param applicationPath 应用路径
     * @param systemEnvironment 系统环境
     */
    public static synchronized void init(String applicationPath, SystemEnvironment systemEnvironment){
        if (context != null)
            return;
        context = new ApplicationContext();
        context.applicationPath = applicationPath;
        context.systemEnvironment = systemEnvironment;
    }

    /**
     * 获取上下文单例
     *
     * @return 上下文单例
     */
    public static ApplicationContext getInstance(){
        return context;
    }

    /**
     * 获取应用路径
     *
     * @return 应用路径
     */
    public static String getApplicationPath() {
        return context.applicationPath;
    }

    /**
     * 系统环境
     *
     * @return 系统环境
     */
    public static SystemEnvironment getSystemEnvironment(){
        return context.systemEnvironment;
    }
}
