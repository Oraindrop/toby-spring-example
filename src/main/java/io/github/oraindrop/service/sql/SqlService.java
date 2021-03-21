package io.github.oraindrop.service.sql;

import io.github.oraindrop.exception.SqlRetrievalFailureException;

public interface SqlService {
    String getSql(String key) throws SqlRetrievalFailureException;
}
