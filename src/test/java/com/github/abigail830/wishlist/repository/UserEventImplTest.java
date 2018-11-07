package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.entity.UserEvent;
import org.flywaydb.core.Flyway;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class UserEventImplTest{

    private static JdbcTemplate jdbcTemplate;
    private static JdbcDataSource ds;

    @BeforeClass
    public static void setup() {
        ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:UserEventImplTest;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        Flyway flyway = Flyway.configure().dataSource(ds).load();
        flyway.migrate();
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Test
    public void testCreateUserEvent() throws Exception {
        UserEventImpl userEventImpl = new UserEventImpl();
        userEventImpl.setJdbcTemplate(jdbcTemplate);
        userEventImpl.createUserEvent(new UserEvent("openID"));
        assertThat(jdbcTemplate.queryForList("select * from user_event where open_id='openID'").size(), is(1));

    }

    @Test
    public void testGetUserEventByOpenId() throws Exception {
        UserEventImpl userEventImpl = new UserEventImpl();
        userEventImpl.setJdbcTemplate(jdbcTemplate);
        userEventImpl.createUserEvent(new UserEvent("openID2"));
        assertThat(userEventImpl.getUserEventByOpenId("openID2").getOpenId(), is("openID2"));
    }

    @Test
    public void testGetUserEventByEventType() throws Exception {
        UserEventImpl userEventImpl = new UserEventImpl();
        userEventImpl.setJdbcTemplate(jdbcTemplate);
        UserEvent userEvent = new UserEvent("openID3");
        userEvent.setEventType("TEST");
        userEventImpl.createUserEvent(userEvent);
        assertThat(userEventImpl.getUserEventByEventType("TEST").getOpenId(), is("openID3"));
    }

    @Test
    public void testGetUserEventByOpenIdAndEventType() throws Exception {
        UserEventImpl userEventImpl = new UserEventImpl();
        userEventImpl.setJdbcTemplate(jdbcTemplate);
        UserEvent userEvent = new UserEvent("openID4");
        userEvent.setEventType("TEST2");
        userEventImpl.createUserEvent(userEvent);
        assertThat(userEventImpl.getUserEventByOpenIdAndEventType("openID4","TEST2").getOpenId(), is("openID4"));
    }

}