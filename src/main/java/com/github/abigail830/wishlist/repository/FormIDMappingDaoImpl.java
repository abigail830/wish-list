package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.entity.FormIDMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.List;

public class FormIDMappingDaoImpl {

    private static final Logger logger = LoggerFactory.getLogger(FormIDMappingDaoImpl.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<FormIDMapping> rowMapper = new BeanPropertyRowMapper<>(FormIDMapping.class);

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean createFormIDMapping(String openID, String formID, Date createTime, Date dueTime) {
        logger.info("Going to create form id mapping: openID={}, formID={}, createTime={}, dueTime={}",
                openID,
                formID,
                createTime,
                dueTime);
        try {
            int result = jdbcTemplate.update(
                    "INSERT INTO form_id_map_tbl (open_id, form_id, create_time, due_time) VALUES (?,?,?,?)",
                    openID,
                    formID,
                    createTime,
                    dueTime);
            return result == 1;
        } catch (Exception ex) {
            logger.error("Failed to insert form id mapping. ", ex);
            return false;
        }


    }

    public boolean deleteFormIDMapping(String formID) {
        logger.info("Going to delete form id mapping: formID={}", formID);
        try {
            int result = jdbcTemplate.update(
                    "DELETE FROM form_id_map_tbl WHERE form_id=?",
                    formID);
            return result == 1;
        } catch (Exception ex) {
            logger.error("Failed to insert form id mapping. ", ex);
            return false;
        }

    }

    public List<FormIDMapping> queryByOpenID(String openID) {
        logger.info("Going to query form id mapping by open id: openID={}", openID);
        return jdbcTemplate.query("SELECT * FROM form_id_map_tbl WHERE open_id=?", rowMapper, openID);
    }

    public List<FormIDMapping> queryByFormID(String formID) {
        logger.info("Going to query form id mapping by form id: formID={}", formID);
        return jdbcTemplate.query("SELECT * FROM form_id_map_tbl WHERE form_id=?", rowMapper, formID);
    }

    public List<FormIDMapping> queryAllFormID() {
        logger.info("Going to query all form id mapping");
        return jdbcTemplate.query("SELECT * FROM form_id_map_tbl", rowMapper);
    }
}