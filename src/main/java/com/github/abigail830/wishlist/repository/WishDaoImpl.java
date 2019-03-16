package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.entity.User;
import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.util.Constants;
import com.github.abigail830.wishlist.util.Toggle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
public class WishDaoImpl {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Wish> rowMapper = new BeanPropertyRowMapper<>(Wish.class);

	private RowMapper<User> userMapper = new BeanPropertyRowMapper<>(User.class);

	private RowMapper<Wish> wishRowMapperWithCreator = new WishRowMapperWithCreator();

	private RowMapper<Wish> wishRowMapperWithImplementor = new WishWithTakenUPInfoRowMapper();

	private RowMapper<Wish> wishRowMapper = new WishRowMapper();

	public void createWish(Wish wish) {
		log.info("Create wish for wishlist [wish_list_id={}].", wish.getWishListId());
		if (Toggle.TEST_MODE.isON()) {
			log.info("It is running in test mode");
			jdbcTemplate.update(
					"INSERT INTO wish_tbl (wish_list_id, description, wish_status, implementor_open_id) " +
							"VALUES (?, ?, ?, ?)",
					(int) wish.getWishListId(),
					wish.getDescription(),
					wish.getWishStatus(),
					wish.getImplementorOpenId()
			);

		} else {
			jdbcTemplate.update(
					"REPLACE INTO wish_tbl (wish_list_id, description, wish_status, implementor_open_id) " +
							"VALUES (?, ?, ?, ?)",
					(int) wish.getWishListId(),
					wish.getDescription(),
					wish.getWishStatus(),
					wish.getImplementorOpenId()
			);

		}
	}

	public void updateWish(Wish wish) {
		log.debug(wish.toString());
		log.info("Update wish for wishlist [wish_id={}].", wish.getId());
		jdbcTemplate.update("UPDATE wish_tbl set description=?, wish_status=?, implementor_open_id=? " +
						"where ID=?",
				wish.getDescription(),
				wish.getWishStatus(),
				wish.getImplementorOpenId(),
				wish.getId()
		);
	}

	public List<Wish> getWishByWishListId(String wishListID) {
		log.info("Query Wish by WishList ID: {}", wishListID);
		List<Wish> wishes = jdbcTemplate.query("select wish_tbl.ID as ID, " +
				"wish_tbl.wish_list_id as wish_list_id, " +
				"wish_tbl.description as description, " +
				"wish_tbl.create_time as create_time, " +
				"wish_tbl.last_update_time as last_update_time, " +
				"wish_tbl.wish_status as wish_status, " +
				"wish_tbl.implementor_open_id as implementor_open_id, " +
				"user_table.open_id as implementor_open_id, " +
				"user_table.gender as implementor_gender, " +
				"user_table.nick_name as implementor_nick_name, " +
				"user_table.city as implementor_city, " +
				"user_table.country as implementor_country, " +
				"user_table.province as implementor_province, " +
				"user_table.lang as implementor_lang, " +
				"user_table.avatar_url as implementor_avatar_url, " +
				"wishlist_tbl.implementors_limit as implementors_limit " +
				"from wish_tbl " +
				"left join user_tbl as user_table on wish_tbl.implementor_open_id = user_table.open_id " +
				"left join wishlist_tbl on wish_tbl.wish_list_id = wishlist_tbl.ID " +
				"where wish_tbl.wish_list_id = ?", wishRowMapperWithImplementor, wishListID);
		for (Wish wish : wishes) {
			List<User> users = queryImplementors(wish.getId().toString());
			wish.setImplementors(users);
		}
		return wishes;
	}

	public List<Wish> getWishByID(String id) {
		log.info("Query Wish by ID: {}", id);
		List<Wish> wishes = jdbcTemplate.query("select wish_tbl.ID as ID, " +
				"wish_tbl.wish_list_id as wish_list_id, " +
				"wish_tbl.description as description, " +
				"wish_tbl.create_time as create_time, " +
				"wish_tbl.last_update_time as last_update_time, " +
				"wish_tbl.wish_status as wish_status, " +
				"wish_tbl.implementor_open_id as implementor_open_id, " +
				"wishlist_tbl.implementors_limit as implementors_limit " +
				"from wish_tbl " +
				"left join wishlist_tbl on wish_tbl.wish_list_id = wishlist_tbl.ID " +
				"where wish_tbl.id = ?", wishRowMapper, id);
		return wishes;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void deleteByWishListID(Integer wishListID) {
		log.info("Delete Wish by WishList ID: {}", wishListID);
		jdbcTemplate.update("Delete from wish_tbl WHERE wish_list_id = ?", wishListID);
	}

	public void deleteByWishID(Integer wishID) {
		log.info("Delete Wish by Wish ID: {}", wishID);
		jdbcTemplate.update("Delete from wish_tbl WHERE ID=?", wishID);
	}

	public List<Wish> getWishByTakenupUserIDV2(String openID) {
		log.info("V2 Get Wish by taken ID: {}", openID);
		List<Wish> wishes = jdbcTemplate.query("select wish_tbl.ID as ID, " +
						"wish_tbl.wish_list_id as wish_list_id, " +
						"wish_tbl.description as description, " +
						"wish_tbl.create_time as create_time, " +
						"wish_tbl.last_update_time as last_update_time, " +
						"wish_tbl.wish_status as wish_status, " +
						"wish_tbl.implementor_open_id as implementor_open_id, " +
						"user_table.open_id as open_id, " +
						"user_table.gender as gender, " +
						"user_table.nick_name as nick_name, " +
						"user_table.city as city, " +
						"user_table.country as country, " +
						"user_table.province as province, " +
						"user_table.lang as lang, " +
						"user_table.avatar_url as avatar_url, " +
						"wishlist_tbl.due_time as due_time " +
						"from wish_tbl " +
						"join wishlist_tbl on wishlist_tbl.id = wish_tbl.wish_list_id " +
						"join user_tbl as user_table on wishlist_tbl.open_id = user_table.open_id " +
						"join implementors_tbl on implementors_tbl.wish_id = wish_tbl.ID " +
						"where implementors_tbl.implementor_open_id = ?",
				wishRowMapperWithCreator, openID);
		return wishes;

	}

	public List<Wish> getWishByTakenupUserID(String openId) {
		log.info("Get Wish by taken ID: {}", openId);
		//TODO: multi take up support
		List<Wish> wishes = jdbcTemplate.query("select wish_tbl.ID as ID, " +
						"wish_tbl.wish_list_id as wish_list_id, " +
						"wish_tbl.description as description, " +
						"wish_tbl.create_time as create_time, " +
						"wish_tbl.last_update_time as last_update_time, " +
						"wish_tbl.wish_status as wish_status, " +
						"wish_tbl.implementor_open_id as implementor_open_id, " +
						"user_table.open_id as open_id, " +
						"user_table.gender as gender, " +
						"user_table.nick_name as nick_name, " +
						"user_table.city as city, " +
						"user_table.country as country, " +
						"user_table.province as province, " +
						"user_table.lang as lang, " +
						"user_table.avatar_url as avatar_url, " +
						"wishlist_tbl.due_time as due_time " +
						"from wish_tbl " +
						"join wishlist_tbl on wishlist_tbl.id = wish_tbl.wish_list_id " +
						"join user_tbl as user_table on wishlist_tbl.open_id = user_table.open_id " +
						"where wish_tbl.implementor_open_id = ?",
				wishRowMapperWithCreator, openId);
		List<Wish> wishByTakenupUserIDV2 = getWishByTakenupUserIDV2(openId);
		wishes.addAll(wishByTakenupUserIDV2);
		return wishes;
	}

	public List<Wish> getWishDetailByWishListID(String wishListID) {
		log.info("Get Wish by wish List ID: {}", wishListID);
		return jdbcTemplate.query("select wish_tbl.ID as ID, " +
						"wish_tbl.wish_list_id as wish_list_id, " +
						"wish_tbl.description as description, " +
						"wish_tbl.create_time as create_time, " +
						"wish_tbl.last_update_time as last_update_time, " +
						"wish_tbl.wish_status as wish_status, " +
						"wish_tbl.implementor_open_id as implementor_open_id, " +
						"user_table.open_id as open_id, " +
						"user_table.gender as gender, " +
						"user_table.nick_name as nick_name, " +
						"user_table.city as city, " +
						"user_table.country as country, " +
						"user_table.province as province, " +
						"user_table.lang as lang, " +
						"user_table.avatar_url as avatar_url, " +
						"wishlist_tbl.due_time as due_time " +
						"from wish_tbl " +
						"join wishlist_tbl on wishlist_tbl.id = wish_tbl.wish_list_id " +
						"join user_tbl as user_table on wishlist_tbl.open_id = user_table.open_id " +
						"where wishlist_tbl.id = ?" ,
				wishRowMapperWithCreator, wishListID);
	}

	public void takeupWish(String id, String takeUpOpenID) {
		log.info("Taking up Wish by Wish ID: {}", id);
		jdbcTemplate.update("Update wish_tbl set implementor_open_id=?, wish_status=? WHERE ID=?",
				takeUpOpenID,
				Constants.WISH_STATUS_TAKEUP,
				id);
	}

	public void completeWish(String id) {
		log.info("Complete Wish ID: {}", id);
		jdbcTemplate.update("Update wish_tbl set  wish_status=? WHERE ID=?",
				Constants.WISH_STATUS_DONE,
				id);
	}

	public void removeTakenupWish(String id) {
		log.info("remove take up for Wish ID: {}", id);
		jdbcTemplate.update("Update wish_tbl set implementor_open_id=?, wish_status=? WHERE ID=?",
				null,
				Constants.WISH_STATUS_NEW,
				id);
	}

	public int getCurrentImplementorSequence(String id) {
		log.info("Get implementor seq {} for wish {}", id);
		Integer seq = jdbcTemplate.queryForObject("select max(sequence) from implementors_tbl where wish_id = '" + id +"'" , Integer.class);
		log.info("Current implementor sequence for wish {} is {}", id, seq);
		return seq != null? seq : -1;
	}

	public void takeupWish(String id, String takeUpOpenID, int sequenceID) {
		log.info("Add take up wish info as wish {} open id {} sequence {}", id, takeUpOpenID, sequenceID);
		jdbcTemplate.update(
				"INSERT INTO implementors_tbl (wish_id, implementor_open_id, sequence) " +
						"VALUES (?, ?, ?)",
				Integer.valueOf(id),
				takeUpOpenID,
				sequenceID);
	}

	public void updateTakeupStatus(String id) {
		log.info("Mark take up for Wish ID: {}", id);
		jdbcTemplate.update("Update wish_tbl set wish_status=? WHERE ID=?",
				Constants.WISH_STATUS_TAKEUP,
				id);
	}

	public List<User> queryImplementors(String wishID) {
		log.info("Get implemetor by wish ID: {}", wishID);
		return jdbcTemplate.query("select user_table.open_id as open_id, " +
						"user_table.gender as gender, " +
						"user_table.nick_name as nick_name, " +
						"user_table.city as city, " +
						"user_table.country as country, " +
						"user_table.province as province, " +
						"user_table.lang as lang, " +
						"user_table.avatar_url as avatar_url " +
						"from implementors_tbl " +
						"join user_tbl as user_table on implementors_tbl.implementor_open_id = user_table.open_id " +
						"where implementors_tbl.wish_id = ?",
				userMapper, wishID);

	}

	public static class WishRowMapperWithCreator implements RowMapper<Wish> {
		@Override
		public Wish mapRow(ResultSet resultSet, int i) throws SQLException {

			Wish wish = new Wish();
			wish.setId(resultSet.getInt("ID"));
			wish.setWishListId(resultSet.getInt("wish_list_id"));
			wish.setDescription(resultSet.getString("description"));
			wish.setCreateTime(resultSet.getTimestamp("create_time"));
			wish.setLastUpdateTime(resultSet.getTimestamp("last_update_time"));
			wish.setWishStatus(resultSet.getString("wish_status"));
			wish.setImplementorOpenId("implementor_open_id");
			wish.setDueTime(resultSet.getTimestamp("due_time"));

			User user = new User();
			user.setOpenId(resultSet.getString("open_id"));
			user.setGender(resultSet.getString("gender"));
			user.setNickName(resultSet.getString("nick_name"));
			user.setLang(resultSet.getString("lang"));
			user.setAvatarUrl(resultSet.getString("avatar_url"));

			wish.setCreator(user);
			return wish;

		}
	}

	public static class WishWithTakenUPInfoRowMapper implements RowMapper<Wish> {
		@Override
		public Wish mapRow(ResultSet resultSet, int i) throws SQLException {

			Wish wish = new Wish();
			wish.setId(resultSet.getInt("ID"));
			wish.setWishListId(resultSet.getInt("wish_list_id"));
			wish.setDescription(resultSet.getString("description"));
			wish.setCreateTime(resultSet.getTimestamp("create_time"));
			wish.setLastUpdateTime(resultSet.getTimestamp("last_update_time"));
			wish.setWishStatus(resultSet.getString("wish_status"));
			wish.setImplementorOpenId("implementor_open_id");
			wish.setImplementorsLimit(resultSet.getInt("implementors_limit"));

			User user = new User();
			user.setOpenId(resultSet.getString("implementor_open_id"));
			user.setGender(resultSet.getString("implementor_gender"));
			user.setNickName(resultSet.getString("implementor_nick_name"));
			user.setLang(resultSet.getString("implementor_lang"));
			user.setAvatarUrl(resultSet.getString("implementor_avatar_url"));

			wish.setImplementor(user);
			return wish;

		}
	}

	public static class WishRowMapper implements RowMapper<Wish> {
		@Override
		public Wish mapRow(ResultSet resultSet, int i) throws SQLException {

			Wish wish = new Wish();
			wish.setId(resultSet.getInt("ID"));
			wish.setWishListId(resultSet.getInt("wish_list_id"));
			wish.setDescription(resultSet.getString("description"));
			wish.setCreateTime(resultSet.getTimestamp("create_time"));
			wish.setLastUpdateTime(resultSet.getTimestamp("last_update_time"));
			wish.setWishStatus(resultSet.getString("wish_status"));
			wish.setImplementorOpenId(resultSet.getString("implementor_open_id"));
			wish.setImplementorsLimit(resultSet.getInt("implementors_limit"));

			return wish;

		}
	}
}
