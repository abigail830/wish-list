package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.dto.UserInfo;
import com.github.abigail830.wishlist.entity.User;
import com.github.abigail830.wishlist.util.Toggle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class UserDaoImpl {


	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<User> rowMapper = new BeanPropertyRowMapper<>(User.class);

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void createUser(UserInfo user) {
		log.info("Insert IGNORE data openId={}, gender={}, nick_name={}, " +
				"city={}, country={}, province={}, lang={}, avatar_url={}",
				user.getOpenId(),
				user.getGender(),
				user.getNickName(),
				user.getCity(),
				user.getCountry(),
				user.getProvince(),
				user.getLanguage(),
				user.getAvatarUrl()
		);
		if (Toggle.TEST_MODE.isON()) {
			log.info("It is running in test mode");

			jdbcTemplate.update(
					"INSERT INTO user_tbl (open_id, gender, nick_name, city, country, province, lang, avatar_url) " +
							"VALUES (?, ?, ?, ?, ?, ?, ? ,?)",
					user.getOpenId(),
					user.getGender(),
					user.getNickName(),
					user.getCity(),
					user.getCountry(),
					user.getProvince(),
					user.getLanguage(),
					user.getAvatarUrl()
			);

		} else {
			jdbcTemplate.update(
					"INSERT ignore INTO user_tbl (open_id, gender, nick_name, city, country, province, lang, avatar_url) " +
							"VALUES (?, ?, ?, ?, ?, ?, ? ,?)",
					user.getOpenId(),
					user.getGender(),
					user.getNickName(),
					user.getCity(),
					user.getCountry(),
					user.getProvince(),
					user.getLanguage(),
					user.getAvatarUrl()
			);

		}
	}

	public void updateUserByOpenID(UserInfo user) {
		log.info("Update user openId={} to gender={}, nick_name={}, " +
						"city={}, country={}, province={}, lang={}, avatar_url={}",
				user.getOpenId(),
				user.getGender(),
				user.getNickName(),
				user.getCity(),
				user.getCountry(),
				user.getProvince(),
				user.getLanguage(),
				user.getAvatarUrl()
		);
		jdbcTemplate.update(
				"UPDATE user_tbl set gender=?, nick_name=?, city=?, country=?, province=?, lang=?, avatar_url=? where open_id=?",
				user.getGender(),
				user.getNickName(),
				user.getCity(),
				user.getCountry(),
				user.getProvince(),
				user.getLanguage(),
				user.getAvatarUrl(),
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
