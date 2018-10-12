package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.entity.User;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl {

	private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);

	public void createUser(User user) {
		logger.info("Going to insert user data openId={}, gender={}, nick_name={}, " +
				"city={}, country={}, province={}, language={}",
				user.getOpenId(),
				user.getGender(),
				user.getNickName(),
				user.getCity(),
				user.getCountry(),
				user.getProvince(),
				user.getLang()
		);
		jdbcTemplate.update(
				"REPLACE INTO user_tbl (open_id, gender, nick_name, city, country, province, lang) " +
						"VALUES (?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?)",
				user.getOpenId(),
				user.getGender(),
				user.getNickName(),
				user.getCity(),
				user.getCountry(),
				user.getProvince(),
				user.getLang()
		);
	}

	public void updateUserByOpenID(User user) {
		logger.info("Going to update user openId={} to gender={}, nick_name={}, " +
						"city={}, country={}, province={}, language={}",
				user.getOpenId(),
				user.getGender(),
				user.getNickName(),
				user.getCity(),
				user.getCountry(),
				user.getProvince(),
				user.getLang()
		);
		jdbcTemplate.update(
				"UPDATE user_tbl set gender=?, nick_name=?, city=?, country=?, province=?, lang=? where open_id=?",
				user.getGender(),
				user.getNickName(),
				user.getCity(),
				user.getCountry(),
				user.getProvince(),
				user.getLang(),
				user.getOpenId()
		);
	}

	public User getUserByOpenId(String openId) {
		List<User> users = jdbcTemplate.query("SELECT * FROM user_tbl WHERE open_id = ?", rowMapper, openId);
		return users.stream().findFirst().orElse(null);
	}

	public User getUserById(String id) {
		List<User> users = jdbcTemplate.query("SELECT * FROM user_tbl WHERE id = ?", rowMapper, id);
		return users.stream().findFirst().orElse(null);
	}
}
