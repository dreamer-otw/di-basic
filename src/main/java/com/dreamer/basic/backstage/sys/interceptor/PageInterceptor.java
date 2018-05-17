package com.dreamer.basic.backstage.sys.interceptor;


import com.dreamer.basic.backstage.sys.utils.Constant;
import com.dreamer.basic.backstage.sys.utils.Page;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * > mybatis分页处理拦截器
 * author : dreamer-otw
 * email : dreamers_otw@163.com
 * date : 2018/5/17 13:44
 */
@Intercepts({@Signature(type =StatementHandler.class, method = "prepare", args ={Connection.class}),
        @Signature(method = "query", type = Executor.class,
                args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class })})
public class PageInterceptor implements Interceptor {

    private static final Logger log = LoggerFactory.getLogger(PageInterceptor.class);

    private String databaseType;

    @SuppressWarnings("rawtypes")
    private ThreadLocal<Page> pageThreadLocal = new ThreadLocal<>();


    public String getDatabaseType() {
        return databaseType;
    }

    void setDatabaseType(String databaseType) {
        if (!databaseType.equalsIgnoreCase(Constant.MYSQL) && !databaseType.equalsIgnoreCase(Constant.ORACLE)) {
            throw new PageNotSupportException("Page not support for the type of database, database type [" + databaseType + "]");
        }
        this.databaseType = databaseType;
    }

    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof StatementHandler) { // 控制SQL和查询总数的地方
            Page page = pageThreadLocal.get();
            if (page == null) { //不是分页查询
                return invocation.proceed();
            }

            RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
            StatementHandler delegate = (StatementHandler) ReflectUtil.getFieldValue(handler, "delegate");
            BoundSql boundSql = delegate.getBoundSql();

            Connection connection = (Connection) invocation.getArgs()[0];
            prepareAndCheckDatabaseType(connection); // 准备数据库类型

            if (page.getTotalPage() > -1) {
                if (log.isTraceEnabled()) {
                    log.trace("已经设置了总页数, 不需要再查询总数.");
                }
            } else {
                Object parameterObj = boundSql.getParameterObject();
                MappedStatement mappedStatement = (MappedStatement) ReflectUtil.getFieldValue(delegate, "mappedStatement");
                queryTotalRecord(page, parameterObj, mappedStatement, connection);
            }

            String sql = boundSql.getSql();
            String pageSql = buildPageSql(page, sql);
            if (log.isDebugEnabled()) {
                log.debug("分页时, 生成分页pageSql: " + pageSql);
            }
            ReflectUtil.setFieldValue(boundSql, "sql", pageSql);

            return invocation.proceed();
        } else { // 查询结果的地方
            // 获取是否有分页Page对象
            Page<?> page = findPageObject(invocation.getArgs()[1]);
            if (page == null) {
                if (log.isTraceEnabled()) {
                    log.trace("没有Page对象作为参数, 不是分页查询.");
                }
                return invocation.proceed();
            } else {
                if (log.isTraceEnabled()) {
                    log.trace("检测到分页Page对象, 使用分页查询.");
                }
            }
            //设置真正的parameterObj
            invocation.getArgs()[1] = extractRealParameterObject(invocation.getArgs()[1]);

            pageThreadLocal.set(page);
            try {
                Object resultObj = invocation.proceed(); // Executor.query(..)
                if (resultObj instanceof List) {
                    /* @SuppressWarnings({ "unchecked", "rawtypes" }) */
                    page.setResults((List) resultObj);
                }
                return resultObj;
            } finally {
                pageThreadLocal.remove();
            }
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        String databaseType = properties.getProperty("databaseType");
        if (databaseType != null) {
            setDatabaseType(databaseType);
        }
    }

    private Page<?> findPageObject(Object parameterObj) {
        if (parameterObj instanceof Page<?>) {
            return (Page<?>) parameterObj;
        } else if (parameterObj instanceof Map) {
            for (Object val : ((Map<?, ?>) parameterObj).values()) {
                if (val instanceof Page<?>) {
                    return (Page<?>) val;
                }
            }
        }
        return null;
    }

    private Object extractRealParameterObject(Object parameterObj) {
        if (parameterObj instanceof Map<?, ?>) {
            Map<?, ?> parameterMap = (Map<?, ?>) parameterObj;
            if (parameterMap.size() == 2) {
                boolean springMapWithNoParamName = true;
                for (Object key : parameterMap.keySet()) {
                    if (!(key instanceof String)) {
                        springMapWithNoParamName = false;
                        break;
                    }
                    String keyStr = (String) key;
                    if (!"0".equals(keyStr) && !"1".equals(keyStr)) {
                        springMapWithNoParamName = false;
                        break;
                    }
                }
                if (springMapWithNoParamName) {
                    for (Object value : parameterMap.values()) {
                        if (!(value instanceof Page<?>)) {
                            return value;
                        }
                    }
                }
            }
        }
        return parameterObj;
    }

    private void prepareAndCheckDatabaseType(Connection connection) throws SQLException {
        if (databaseType == null) {
            String productName = connection.getMetaData().getDatabaseProductName();
            if (log.isTraceEnabled()) {
                log.trace("Database productName: " + productName);
            }
            productName = productName.toLowerCase();
            if (productName.indexOf(Constant.MYSQL) != -1) {
                databaseType = Constant.MYSQL;
            } else if (productName.indexOf(Constant.ORACLE) != -1) {
                databaseType = Constant.ORACLE;
            } else {
                throw new PageNotSupportException("Page not support for the type of database, database product name [" + productName + "]");
            }
            if (log.isInfoEnabled()) {
                log.info("自动检测到的数据库类型为: " + databaseType);
            }
        }
    }

    /**
     * <pre>
     * 生成分页SQL
     * </pre>
     *
     * @param page
     * @param sql
     * @return
     */
    private String buildPageSql(Page<?> page, String sql) {
        if (Constant.MYSQL.equalsIgnoreCase(databaseType)) {
            return buildMysqlPageSql(page, sql);
        } else if (Constant.ORACLE.equalsIgnoreCase(databaseType)) {
            return buildOraclePageSql(page, sql);
        }
        return sql;
    }

    /**
     * <pre>
     * 生成Mysql分页查询SQL
     * </pre>
     *
     * @param page
     * @param sql
     * @return
     */
    private String buildMysqlPageSql(Page<?> page, String sql) {
        // 计算第一条记录的位置，Mysql中记录的位置是从0开始的。
        int offset = (page.getPageNo() - 1) * page.getPageSize();
        return new StringBuilder(sql).append(" limit ").append(offset).append(",").append(page.getPageSize()).toString();
    }

    /**
     * <pre>
     * 生成Oracle分页查询SQL
     * </pre>
     *
     * @param page
     * @param sql
     */
    private String buildOraclePageSql(Page<?> page, String sql) {
        // 计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
        int offset = (page.getPageNo() - 1) * page.getPageSize() + 1;
        StringBuilder sb = new StringBuilder(sql);
        sb.insert(0, "select u.*, rownum r from (").append(") u where rownum < ").append(offset + page.getPageSize());
        sb.insert(0, "select * from (").append(") where r >= ").append(offset);
        return sb.toString();
    }

    /**
     * <pre>
     * 查询总数
     * </pre>
     *
     * @param page
     * @param parameterObject
     * @param mappedStatement
     * @param connection
     * @throws SQLException
     */
    private void queryTotalRecord(Page<?> page, Object parameterObject, MappedStatement mappedStatement, Connection connection) throws SQLException {
        BoundSql boundSql = mappedStatement.getBoundSql(page);
        String sql = boundSql.getSql();
        String countSql = this.buildCountSql(sql);
        if (log.isDebugEnabled()) {
            log.debug("分页时, 生成countSql: " + countSql);
        }

        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, parameterObject);
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBoundSql);
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = connection.prepareStatement(countSql);
            parameterHandler.setParameters(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                long totalRecord = rs.getLong(1);
                page.setTotalRecord(totalRecord);
            }
        } finally {
            if (rs != null)
                try {
                    rs.close();
                } catch (Exception e) {
                    if (log.isWarnEnabled()) {
                        log.warn("关闭ResultSet时异常.", e);
                    }
                }
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception e) {
                    if (log.isWarnEnabled()) {
                        log.warn("关闭PreparedStatement时异常.", e);
                    }
                }
        }
    }

    /**
     * 根据原Sql语句获取对应的查询总记录数的Sql语句
     *
     * @param sql
     * @return
     */
    private String buildCountSql(String sql) {
        int index = sql.indexOf("from");
        return "select count(*) " + sql.substring(index);
    }

    /**
     * 利用反射进行操作的一个工具类
     *
     */
    private static class ReflectUtil {
        /**
         * 利用反射获取指定对象的指定属性
         *
         * @param obj 目标对象
         * @param fieldName 目标属性
         * @return 目标属性的值
         */
        public static Object getFieldValue(Object obj, String fieldName) {
            Object result = null;
            Field field = ReflectUtil.getField(obj, fieldName);
            if (field != null) {
                field.setAccessible(true);
                try {
                    result = field.get(obj);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return result;
        }

        /**
         * 利用反射获取指定对象里面的指定属性
         *
         * @param obj 目标对象
         * @param fieldName 目标属性
         * @return 目标字段
         */
        private static Field getField(Object obj, String fieldName) {
            Field field = null;
            for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
                try {
                    field = clazz.getDeclaredField(fieldName);
                    break;
                } catch (NoSuchFieldException e) {
                    // 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
                }
            }
            return field;
        }

        /**
         * 利用反射设置指定对象的指定属性为指定的值
         *
         * @param obj 目标对象
         * @param fieldName 目标属性
         * @param fieldValue 目标值
         */
        public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
            Field field = ReflectUtil.getField(obj, fieldName);
            if (field != null) {
                try {
                    field.setAccessible(true);
                    field.set(obj, fieldValue);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static class PageNotSupportException extends RuntimeException {

        /** serialVersionUID*/
        private static final long serialVersionUID = 1L;

        public PageNotSupportException() {
            super();
        }

        public PageNotSupportException(String message, Throwable cause) {
            super(message, cause);
        }

        public PageNotSupportException(String message) {
            super(message);
        }

        public PageNotSupportException(Throwable cause) {
            super(cause);
        }
    }
}
