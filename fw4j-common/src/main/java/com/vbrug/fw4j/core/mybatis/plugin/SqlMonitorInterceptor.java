package com.vbrug.fw4j.core.mybatis.plugin;

import com.vbrug.fw4j.common.util.StringUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * SQL监控
 *
 * @author vbrug
 * @since 1.0.0
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
public class SqlMonitorInterceptor implements Interceptor {

    private static final Logger logger = LoggerFactory.getLogger(SqlMonitorInterceptor.class);

    private long warnMillis;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long beginTimeMillis = System.currentTimeMillis();
        try {
            return invocation.proceed();
        } finally {
            long endTimeMillis = System.currentTimeMillis();
            this.logSQL(invocation);
            logger.info("SQL执行耗时：{}", endTimeMillis - beginTimeMillis);
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String warnMillis = properties.getProperty("warnMillis");
        this.warnMillis = warnMillis == null ? 10000L : Long.parseLong(warnMillis);
    }

    private void logSQL(Invocation invocation){
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Configuration configuration = mappedStatement.getConfiguration();
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        String sql = boundSql.getSql().replaceAll("\\s+", " ");
        logger.info("SQL -> {}", sql);
        MetaObject metaObject = configuration.newMetaObject(boundSql.getParameterObject());
        if (metaObject.isCollection()) {
            String parameters = "";
            for (ParameterMapping x : boundSql.getParameterMappings()) {
                parameters += ", " + metaObject.getValue(x.getProperty());
            }
            if (StringUtils.hasText(parameters))
                logger.info("Parameter -> [{}]", parameters.substring(2));
        }
    }
}
