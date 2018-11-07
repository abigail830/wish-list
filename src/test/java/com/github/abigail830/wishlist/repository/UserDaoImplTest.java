package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.domain.UserInfo;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UserDaoImplTest {

    private static JdbcTemplate jdbcTemplate;
    private static JdbcDataSource ds;
    private static Flyway flyway;

    @BeforeClass
    public static void setup() {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:UserDaoImplTest;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
        jdbcTemplate = new JdbcTemplate(ds);
        String insertSQL = "INSERT INTO user_tbl (open_id, gender, nick_name, city, country, province, lang, avatar_url) " +
                "VALUES ('openID1','M', 'nickname', 'city', 'country', 'province', 'lang', 'imageUrl')";
        jdbcTemplate.execute(insertSQL);
    }


    @After
    public void tearDown() throws Exception {
        jdbcTemplate.update("DELETE FROM user_event WHERE ID is not null");
        jdbcTemplate.update("DELETE FROM wish_tbl WHERE ID is not null");
        jdbcTemplate.update("DELETE FROM wishlist_tbl WHERE ID is not null");
        jdbcTemplate.update("DELETE FROM user_tbl WHERE ID is not null");
    }

    //TODO: H2 doesn't support insert ignore, so no test for create user

    @Test
    public void testCanGetUserByOpenID() throws Exception {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setJdbcTemplate(jdbcTemplate);
        assertThat(userDao.getUserByOpenId("openID1"), is(notNullValue()));
        assertThat(userDao.getUserByOpenId("openID2"), is(nullValue()));
    }


    @Test
    public void testCanGetUserByID() throws Exception {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setJdbcTemplate(jdbcTemplate);
        assertThat(userDao.getUserById("1"), is(notNullValue()));
    }

    @Test
    public void testCanUpdateByOpenID() throws Exception {
        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setJdbcTemplate(jdbcTemplate);
        UserInfo userInfo = new UserInfo("openID1", "M", "nickname2", "city", "country", "province", "lang", "imageUrl");
        userDao.updateUserByOpenID(userInfo);
        assertThat(userDao.getUserByOpenId("openID1").getNickName(), is("nickname2"));
    }

}