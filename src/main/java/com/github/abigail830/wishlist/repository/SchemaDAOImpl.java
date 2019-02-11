package com.github.abigail830.wishlist.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class SchemaDAOImpl {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public boolean isTableExist(String tableName) {
        log.info("Query table exist " + tableName);
        List list = jdbcTemplate.queryForList("select * from information_schema.TABLES where table_name = '" + tableName + "'");
        log.info("Is table exist " + list.size());
        return list.size() == 1;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateSchema(String sql) {
        jdbcTemplate.execute(sql);
    }
}
