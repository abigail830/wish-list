package com.github.abigail830.wishlist.service;

import com.github.abigail830.wishlist.entity.FormIDMapping;
import com.github.abigail830.wishlist.repository.FormIDMappingDaoImpl;
import com.github.abigail830.wishlist.util.Toggle;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class FormIDMappingServiceTest {
    private static JdbcTemplate jdbcTemplate;
    private static JdbcDataSource ds;
    private static Flyway flyway;
    private static FormIDMappingService formIDMappingService;
    private static FormIDMappingDaoImpl formIDMappingDao;

    @BeforeClass
    public static void setup() {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:FormIDMappingServiceTest;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
        jdbcTemplate = new JdbcTemplate(ds);
        formIDMappingDao = new FormIDMappingDaoImpl();
        formIDMappingDao.setJdbcTemplate(jdbcTemplate);
        formIDMappingService = new FormIDMappingService();
        formIDMappingService.setFormIDMappingDao(formIDMappingDao);
        Toggle.TEST_MODE.setStatus(true);
    }

    @After
    public void tearDown() throws Exception {
        jdbcTemplate.update("DELETE FROM form_id_map_tbl");
    }


    @Test
    public void testCanAddAndTakeFormID() throws Exception {
        formIDMappingService.contributeFormID("openID1", "formID1");
        List<FormIDMapping> formIDs = formIDMappingService.getFormIDs("openID1");
        assertThat(formIDs.size(), is(1));
        FormIDMapping formIDMapping = formIDMappingService.takeFormID("openID1");
        assertThat(formIDMapping.getFormId(), is("formID1"));
        formIDs = formIDMappingService.getFormIDs("openID1");
        assertThat(formIDs.size(), is(0));
    }
}