package io.github.oraindrop.service.sql;

import io.github.oraindrop.exception.SqlRetrievalFailureException;
import io.github.oraindrop.exception.SqlUpdateFailureException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapSqlRegistry implements UpdatableSqlRegistry {

    private Map<String, String> sqlMap = new ConcurrentHashMap<>();

    @Override
    public void updateSql(String key, String sql) throws SqlUpdateFailureException {
        if (sqlMap.containsKey(key)) {
            sqlMap.put(key, sql);
            return;
        }

        throw new SqlUpdateFailureException(key + "is empty");
    }

    @Override
    public void updateSql(Map<String, String> sqlMap) throws SqlUpdateFailureException {
        for (Map.Entry<String, String> entry : sqlMap.entrySet()) {
            updateSql(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void registerSql(String key, String sql) {
        sqlMap.put(key, sql);
    }

    @Override
    public String findSql(String key) throws SqlRetrievalFailureException {
        if (sqlMap.containsKey(key)) {
            return sqlMap.get(key);
        }

        throw new SqlRetrievalFailureException(key + "is empty");
    }
}
