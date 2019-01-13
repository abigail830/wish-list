package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.util.Toggle;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;


public class SchemaDAOImplTest {
    private static JdbcTemplate jdbcTemplate;
    private static JdbcDataSource ds;
    private static Flyway flyway;

    @BeforeClass
    public static void setup() {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:SchemaDAOImplTest;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
        jdbcTemplate = new JdbcTemplate(ds);

        Toggle.TEST_MODE.setStatus(true);
    }

    @Test
    public void testCanQuerySchema(){
        SchemaDAOImpl schemaDAO = new SchemaDAOImpl();
        schemaDAO.setJdbcTemplate(jdbcTemplate);
        assertThat(schemaDAO.isTableExist("form_id_map_tbl"), is(true));
        assertThat(schemaDAO.isTableExist("form_id_map_test_tbl"), is(false));
    }

    @Test
    public void testCanAddNewTable() throws Exception {
        SchemaDAOImpl schemaDAO = new SchemaDAOImpl();
        schemaDAO.setJdbcTemplate(jdbcTemplate);
        String sql = "CREATE TABLE form_id_map_tbl_1 (" +
                "  open_id varchar(100) NOT NULL," +
                "  form_id varchar(100) NOT NULL," +
                "  create_time TIMESTAMP NOT NULL," +
                "  due_time TIMESTAMP NOT NULL," +
                "  PRIMARY KEY (form_id)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
        schemaDAO.updateSchema(sql);
        assertThat(schemaDAO.isTableExist("form_id_map_tbl_1"), is(true));


    }
}