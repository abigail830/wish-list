package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.entity.User;
import com.github.abigail830.wishlist.entity.WishList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishListDaoImpl {

	private static final Logger logger = LoggerFactory.getLogger(WishListDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<WishList> rowMapper = new BeanPropertyRowMapper<>(WishList.class);

	public void createWishList(WishList wishList) {
		logger.info("Going to create wish list for user: {}", wishList.toString());
		jdbcTemplate.update(
				"INSERT INTO wishlist_tbl (open_id, description, create_time, due_time) VALUES (?, ?,?,?)",
				wishList.getOpenId(),
				wishList.getDescription(),
				wishList.getCreateTime(),
				wishList.getDueTime()
		);
	}

	public void updateWishListByID(WishList wishList) {
		logger.info("Going to update wish list for user : {}", wishList.toString());
		jdbcTemplate.update(
				"UPDATE wishlist_tbl set description=?, due_time=? where ID=?",
				wishList.getDescription(),
				wishList.getDueTime(),
				wishList.getId()
		);
	}

	public void deleteWishList(Integer wishListID ) {
		logger.info("Going to delete wish list {}", wishListID);
		jdbcTemplate.update("DELETE from wishlist_tbl where ID=?",wishListID);

	}

	public List<WishList> getWishListByOpenId(String openId) {
		List<WishList> wishLists = jdbcTemplate.query("SELECT * FROM wishlist_tbl WHERE open_id = ?", rowMapper, openId);
		return wishLists;
	}

	public List<WishList> getWishListById(String id) {
		List<WishList> wishLists = jdbcTemplate.query("SELECT * FROM wishlist_tbl WHERE id = ?", rowMapper, id);
		return wishLists;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
