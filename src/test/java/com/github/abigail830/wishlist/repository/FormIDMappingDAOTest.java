package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.util.Toggle;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FormIDMappingDAOTest {
    private static JdbcTemplate jdbcTemplate;
    private static JdbcDataSource ds;
    private static Flyway flyway;

    @BeforeClass
    public static void setup() {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:FormIDMappingDAOTest;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
        jdbcTemplate = new JdbcTemplate(ds);

        Toggle.TEST_MODE.setStatus(true);
    }

    @After
    public void tearDown() throws Exception {
        jdbcTemplate.update("DELETE FROM form_id_map_tbl");
    }

    @Test
    public void testCanCreateMapping() throws Exception {
        FormIDMappingDaoImpl formIDMappingDao = new FormIDMappingDaoImpl();
        formIDMappingDao.setJdbcTemplate(jdbcTemplate);
        assertThat(formIDMappingDao.createFormIDMapping("test-open-id-1","test-form_id-1", new Date(), new Date()), is(true));
        assertThat(formIDMappingDao.createFormIDMapping("test-open-id-1","test-form_id-2", new Date(), new Date()), is(true));
        assertThat(jdbcTemplate.queryForList("select * from form_id_map_tbl ").size(), is(2));
    }

    @Test
    public void testCanNotCreateDuplicateMapping() throws Exception {
        FormIDMappingDaoImpl formIDMappingDao = new FormIDMappingDaoImpl();
        formIDMappingDao.setJdbcTemplate(jdbcTemplate);
        assertThat(formIDMappingDao.createFormIDMapping("test-open-id-1","test-form_id-1", new Date(), new Date()), is(true));
        assertThat(formIDMappingDao.createFormIDMapping("test-open-id-2","test-form_id-1", new Date(), new Date()), is(false));
        assertThat(jdbcTemplate.queryForList("select * from form_id_map_tbl ").size(), is(1));
    }

    @Test
    public void testCanDeleteMapping() throws Exception {
        FormIDMappingDaoImpl formIDMappingDao = new FormIDMappingDaoImpl();
        formIDMappingDao.setJdbcTemplate(jdbcTemplate);
        assertThat(formIDMappingDao.createFormIDMapping("test-open-id-1","test-form_id-1", new Date(), new Date()), is(true));
        assertThat(formIDMappingDao.deleteFormIDMapping("test-form_id-1"), is(true));
        assertThat(jdbcTemplate.queryForList("select * from form_id_map_tbl ").size(), is(0));
    }

    @Test
    public void testCanQueryMappingByOpenID() throws Exception {
        FormIDMappingDaoImpl formIDMappingDao = new FormIDMappingDaoImpl();
        formIDMappingDao.setJdbcTemplate(jdbcTemplate);
        assertThat(formIDMappingDao.createFormIDMapping("test-open-id-1","test-form_id-1", new Date(), new Date()), is(true));
        assertThat(formIDMappingDao.createFormIDMapping("test-open-id-1","test-form_id-2", new Date(), new Date()), is(true));
        assertThat(formIDMappingDao.queryByOpenID("test-open-id-1").size(), is(2));
    }

    @Test
    public void testCanQueryMappingByFormID() throws Exception {
        FormIDMappingDaoImpl formIDMappingDao = new FormIDMappingDaoImpl();
        formIDMappingDao.setJdbcTemplate(jdbcTemplate);
        assertThat(formIDMappingDao.createFormIDMapping("test-open-id-1","test-form_id-1", new Date(), new Date()), is(true));
        assertThat(formIDMappingDao.createFormIDMapping("test-open-id-1","test-form_id-2", new Date(), new Date()), is(true));
        assertThat(formIDMappingDao.queryByFormID("test-form_id-2").size(), is(1));
    }

    @Test
    public void testCanQueryAllFormID() throws Exception {
        FormIDMappingDaoImpl formIDMappingDao = new FormIDMappingDaoImpl();
        formIDMappingDao.setJdbcTemplate(jdbcTemplate);
        assertThat(formIDMappingDao.createFormIDMapping("test-open-id-1","test-form_id-1", new Date(), new Date()), is(true));
        assertThat(formIDMappingDao.createFormIDMapping("test-open-id-1","test-form_id-2", new Date(), new Date()), is(true));
        assertThat(formIDMappingDao.queryAllFormID().size(), is(2));
    }
}
