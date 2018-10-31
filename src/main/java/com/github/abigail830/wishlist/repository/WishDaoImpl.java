package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.entity.User;
import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.entity.WishListDetail;
import com.github.abigail830.wishlist.util.Toggle;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WishDaoImpl {

	private static final Logger logger = LoggerFactory.getLogger(WishDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Wish> rowMapper = new BeanPropertyRowMapper<>(Wish.class);

	private RowMapper<Wish> wishRowMapperWithCreator = new WishRowMapper();

	public void createWish(Wish wish) {
		logger.info("Create wish for wishlist [wish_list_id={}].", wish.getWishListId());
		if (Toggle.TEST_MODE.isON()) {
			logger.info("It is running in test mode");
			jdbcTemplate.update(
					"INSERT INTO wish_tbl (wish_list_id, description, wish_status, implementor_open_id) " +
							"VALUES (?, ?, ?, ?)",
					wish.getWishListId(),
					wish.getDescription(),
					wish.getWishStatus(),
					wish.getImplementorOpenId()
			);

		} else {
			jdbcTemplate.update(
					"REPLACE INTO wish_tbl (wish_list_id, description, wish_status, implementor_open_id) " +
							"VALUES (?, ?, ?, ?)",
					wish.getWishListId(),
					wish.getDescription(),
					wish.getWishStatus(),
					wish.getImplementorOpenId()
			);

		}
	}

	public void updateWish(Wish wish) {
		logger.info("Update wish for wishlist [wish_id={}].", wish.getId());
		jdbcTemplate.update("UPDATE wish_tbl set description=?, wish_status=?, implementor_open_id=? " +
						"where ID=?",
				wish.getDescription(),
				wish.getWishStatus(),
				wish.getImplementorOpenId(),
				wish.getId()
		);
	}

	public List<Wish> getWishByWishListId(String wishListID) {
		logger.info("Query Wish by WishList ID: {}", wishListID);
		List<Wish> wishes = jdbcTemplate.query("SELECT * FROM wish_tbl WHERE wish_list_id = ?", rowMapper, wishListID);
		return wishes;
	}

	public List<Wish> getWishByID(String id) {
		logger.info("Query Wish by ID: {}", id);
		List<Wish> wishes = jdbcTemplate.query("SELECT * FROM wish_tbl WHERE id = ?", rowMapper, id);
		return wishes;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void deleteByWishListID(Integer wishListID) {
		logger.info("Delete Wish by WishList ID: {}", wishListID);
		jdbcTemplate.update("Delete from wish_tbl WHERE wish_list_id = ?", wishListID);
	}

	public void deleteByWishID(Integer wishID) {
		logger.info("Delete Wish by Wish ID: {}", wishID);
		jdbcTemplate.update("Delete from wish_tbl WHERE ID=?", wishID);
	}

	public List<Wish> getWishByTakenupUserID(String openId) {
		logger.info("Get Wish by taken ID: {}", openId);
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
						"user_table.avatar_url as avatar_url " +
						"from wish_tbl " +
						"join wishlist_tbl on wishlist_tbl.id = wish_tbl.wish_list_id " +
						"join user_tbl as user_table on wishlist_tbl.open_id = user_table.open_id " +
						"where wish_tbl.implementor_open_id = ?" ,
				wishRowMapperWithCreator, openId);
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
			wish.setImplementorOpenId("implementor_open_id");

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
}
