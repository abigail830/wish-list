package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.entity.UserEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
@Slf4j
public class UserEventImpl {


	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<UserEvent> rowMapper = new BeanPropertyRowMapper<>(UserEvent.class);

	public void createUserEvent(UserEvent userEvent) {
		log.info("Going to insert user event openId={}, eventType={}",
				userEvent.getOpenId(),
				userEvent.getEventType()
		);
		jdbcTemplate.update(
				"INSERT INTO user_event (open_id, event_type) VALUES (?, ?)",
				userEvent.getOpenId(),
				userEvent.getEventType()
		);
	}


	public UserEvent getUserEventByOpenId(String openId) {
		List<UserEvent> userEvents = jdbcTemplate.query("SELECT * FROM user_event WHERE open_id = ?", rowMapper, openId);
		return userEvents.stream().findFirst().orElse(null);
	}

	public UserEvent getUserEventByEventType(String eventType) {
		List<UserEvent> userEvents = jdbcTemplate.query("SELECT * FROM user_event WHERE event_type = ?", rowMapper, eventType);
		return userEvents.stream().findFirst().orElse(null);
	}

	public UserEvent getUserEventByOpenIdAndEventType(String openId, String eventType) {
		List<UserEvent> userEvents = jdbcTemplate.query("SELECT * FROM user_event " +
				"WHERE open_id = ? and event_type = ?", rowMapper, openId, eventType);
		return userEvents.stream().findFirst().orElse(null);
	}

	public UserEvent getUserEventByEventTime(Timestamp startTime, Timestamp endTime) {
		List<UserEvent> userEvents = jdbcTemplate.query("SELECT * FROM user_event " +
				"WHERE event_time >= ? and event_time < ? + INTERVAL 1 DAY", rowMapper, startTime, endTime);
		return userEvents.stream().findFirst().orElse(null);
	}

	public UserEvent getUserById(String id) {
		List<UserEvent> userEvents = jdbcTemplate.query("SELECT * FROM user_event WHERE id = ?", rowMapper, id);
		return userEvents.stream().findFirst().orElse(null);
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
