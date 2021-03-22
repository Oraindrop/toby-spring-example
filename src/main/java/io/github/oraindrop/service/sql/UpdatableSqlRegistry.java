package io.github.oraindrop.service.sql;

import io.github.oraindrop.exception.SqlUpdateFailureException;

import java.util.Map;

public interface UpdatableSqlRegistry extends SqlRegistry {

    void updateSql(String key, String sql) throws SqlUpdateFailureException;

    void updateSql(Map<String ,String> sqlMap) throws SqlUpdateFailureException;
}
