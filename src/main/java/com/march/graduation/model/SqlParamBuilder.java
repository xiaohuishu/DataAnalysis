package com.march.graduation.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

//--------------------- Change Logs--------------------------
// <p>@author xiaohui.shu Initial Created at 16/3/16<p>
//-----------------------------------------------------------
public class SqlParamBuilder implements Serializable {

    private static final long serialVersionUID = -1L;

    private Map<String, Object> paramMap = new HashMap<String, Object>();

    private StringBuffer sqlConditionBuffer = new StringBuffer();

    private String sqlPrefix;

    private SqlParamBuilder() {

    }

    public static SqlParamBuilder newBuilder() {
        return new SqlParamBuilder();
    }

    /**
     * 构建查询语句的前半部分
     *
     * 例如 : SELECT COUNT(1) FROM TABLE_XXX WHERE 1 = 1
     *
     * @param sqlPrefix
     * @return
     */
    public SqlParamBuilder addSqlPrefix(String sqlPrefix) {
        this.sqlPrefix = sqlPrefix;
        return this;
    }

    /**
     * 构建查询语句带动态参数的前半部分
     *
     * 例如 : SELECT COUNT(1) FROM TABLE_XXX WHERE valid = :valid
     *
     * @param sqlPrefix
     * @param paramName
     * @param paramValue
     * @return
     */
    public SqlParamBuilder addSqlPrefix(String sqlPrefix, String paramName, Object paramValue) {
        this.sqlPrefix = sqlPrefix;
        this.paramMap.put(paramName, paramValue);
        return this;
    }

    /**
     * 构建带多个动态参数的SQL
     *
     * @param sqlPrefix
     * @param paramMap
     * @return
     */
    public SqlParamBuilder addSqlPrefix(String sqlPrefix, Map<String, Object> paramMap) {
        this.sqlPrefix = sqlPrefix;
        this.paramMap.putAll(paramMap);
        return this;
    }

    /**
     * 构建查询条件
     *
     * 例如 : AND valid = :valid
     *
     * @param sqlConditon
     * @param paramName
     * @param paramValue
     * @return
     */
    public SqlParamBuilder addSqlConditon(String sqlConditon, String paramName, Object paramValue) {
        this.sqlConditionBuffer.append(sqlConditon);
        this.paramMap.put(paramName, paramValue);
        return this;
    }

    /**
     *
     * @param sqlConditon
     * @param paramMap
     * @return
     */
    public SqlParamBuilder addSqlConditon(String sqlConditon, Map<String, Object> paramMap) {
        this.sqlConditionBuffer.append(sqlConditon);
        this.paramMap.putAll(paramMap);
        return this;
    }

    /**
     * 可以构建不带参数的条件 或者ORDER BY 或者 分页
     *
     * @param sqlConditon
     * @return
     */
    public SqlParamBuilder addSqlConditon(String sqlConditon) {
        this.sqlConditionBuffer.append(sqlConditon);
        return this;
    }

    public String getSql() {
        return this.sqlPrefix + sqlConditionBuffer.toString();
    }

    public Map<String, Object> getParamMap() {
        return this.paramMap;
    }
}

