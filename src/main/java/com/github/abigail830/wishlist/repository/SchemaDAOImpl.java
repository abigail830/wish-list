package com.github.abigail830.wishlist.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class SchemaDAOImpl {
    private static final Logger logger = LoggerFactory.getLogger(SchemaDAOImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;


    public boolean isTableExist(String tableName) {
        logger.info("Query table exist " + tableName);
        List list = jdbcTemplate.queryForList("select * from information_schema.TABLES where table_name = '" + tableName + "'");
        logger.info("Is table exist " + list.size());
        return list.size() == 1;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateSchema(String sql) {
        jdbcTemplate.execute(sql);
    }
}
