package io.github.oraindrop.service.sql;

import io.github.oraindrop.exception.SqlRetrievalFailureException;

import java.util.HashMap;
import java.util.Map;

public class HashMapSqlRegistry implements SqlRegistry {

    private Map<String, String> map;

    public HashMapSqlRegistry() {
        this.map = new HashMap<>();
    }

    @Override
    public void registerSql(String key, String sql) {
        this.map.put(key, sql);
    }

    @Override
    public String findSql(String key) throws SqlRetrievalFailureException {
        if (map.containsKey(key)) {
            return map.get(key);
        }

        throw new SqlRetrievalFailureException(key + "is empty");
    }
}
