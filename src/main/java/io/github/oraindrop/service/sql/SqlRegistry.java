package io.github.oraindrop.service.sql;

import io.github.oraindrop.exception.SqlRetrievalFailureException;

public interface SqlRegistry {

    void registerSql(String key, String sql);

    String findSql(String key) throws SqlRetrievalFailureException;
}
