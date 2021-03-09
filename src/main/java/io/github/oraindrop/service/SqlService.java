package io.github.oraindrop.service;

import io.github.oraindrop.exception.SqlRetrievalFailureException;

public interface SqlService {

    String getSql(String key) throws SqlRetrievalFailureException;
}
