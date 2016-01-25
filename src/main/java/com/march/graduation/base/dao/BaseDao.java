package com.march.graduation.base.dao;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

// ********************************************
// * @author: xiaohui.shu
// * @version: 日期: 16-1-22 时间: 下午12:59
// ********************************************
public class BaseDao extends NamedParameterJdbcDaoSupport {

    private static final String DEFAULT_KEY_NAME = "id";

    public <T> long insertAndReturnKeyImpl(T obj, String sql) {
        return this.insertAndReturnKeyImpl(obj, sql, DEFAULT_KEY_NAME);
    }

    public <T> long insertAndReturnKeyImpl(T obj, String sql, String key) {
        KeyHolder holder = new GeneratedKeyHolder();
        this.getNamedParameterJdbcTemplate().update(sql, new BeanPropertySqlParameterSource(obj), holder,
                new String[] { key });

        if (holder.getKey() != null) {
            return holder.getKey().longValue();
        }
        return 0;
    }

    public long insertAndReturnKeyImpl(SqlParameterSource parameterSource, String sql, String key) {
        KeyHolder holder = new GeneratedKeyHolder();

        this.getNamedParameterJdbcTemplate().update(sql, parameterSource, holder, new String[] { key });

        if (holder.getKey() != null) {
            return holder.getKey().longValue();
        }
        return 0;
    }

    public <T> int[] batchInsertImpl(List<T> list, String sql) {
        List<SqlParameterSource> params = Lists.transform(list, new Function<T, SqlParameterSource>() {
            @Override
            public SqlParameterSource apply(T input) {
                return new BeanPropertySqlParameterSource(input);
            }
        });

        return this.getNamedParameterJdbcTemplate().batchUpdate(sql,
                params.toArray(new SqlParameterSource[params.size()]));
    }
}
