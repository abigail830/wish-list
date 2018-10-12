package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.entity.User;
import com.github.abigail830.wishlist.entity.UserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserEventImpl {

	private static final Logger logger = LoggerFactory.getLogger(UserEventImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<UserEvent> rowMapper = new BeanPropertyRowMapper<>(UserEvent.class);

	public void createUserEvent(UserEvent userEvent) {
		logger.info("Going to insert user event openId={}, eventType={}",
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

	public UserEvent getUserById(String id) {
		List<UserEvent> userEvents = jdbcTemplate.query("SELECT * FROM user_event WHERE id = ?", rowMapper, id);
		return userEvents.stream().findFirst().orElse(null);
	}
}
