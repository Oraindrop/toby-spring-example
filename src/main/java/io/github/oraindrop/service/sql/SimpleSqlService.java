package io.github.oraindrop.service.sql;

import io.github.oraindrop.exception.SqlRetrievalFailureException;

import javax.annotation.PostConstruct;

public class SimpleSqlService implements SqlService {

    private SqlReader sqlReader;

    private SqlRegistry sqlRegistry;

    public SimpleSqlService() {
    }

    public SimpleSqlService(SqlReader sqlReader, SqlRegistry sqlRegistry) {
        this.sqlReader = sqlReader;
        this.sqlRegistry = sqlRegistry;
    }

    @PostConstruct
    public void loadSql() {
        this.sqlReader.read(this.sqlRegistry);
    }

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        return this.sqlRegistry.findSql(key);
    }
}
