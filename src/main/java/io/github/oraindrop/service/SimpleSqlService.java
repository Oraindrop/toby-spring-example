package io.github.oraindrop.service;

import io.github.oraindrop.exception.SqlRetrievalFailureException;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class SimpleSqlService implements SqlService {

    private Map<String, String> map;

    public SimpleSqlService() {
        this.map = new HashMap<>();
    }

    @PostConstruct
    public void loadSql() {
        this.map.put("userAdd", "insert into user (id, name, password, level, login, recommend, email) values (?,?,?,?,?,?,?)");
        this.map.put("userGet", "select * from user where id = ?");
        this.map.put("userGetAll", "select * from user");
        this.map.put("userDeleteAll", "delete from user");
        this.map.put("userGetCount", "select count(*) from user");
        this.map.put("userUpdate", "update user "
                + "set name = ?, password = ?, level = ?, login = ?, recommend = ?, email = ?"
                + "where id = ?");
    }

    @Override
    public String getSql(String key) throws SqlRetrievalFailureException {
        if (map.containsKey(key)) {
            return map.get(key);
        }

        throw new SqlRetrievalFailureException(key + "is empty");
    }
}
