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
		logger.info("Going to create wish list for user openId={};", wishList.getOpenID());
		jdbcTemplate.update(
				"REPLACE INTO wishlist_tbl (open_id, description) VALUES (?, ?)",
				wishList.getOpenID(),
				wishList.getDescription()
		);
	}

	public void updateWishListByOpenID(WishList wishList) {
		logger.info("Going to update wish list for user openId={};", wishList.getOpenID());
		jdbcTemplate.update(
				"UPDATE wishlist_tbl set description=? where open_id=?",
				wishList.getDescription(),
				wishList.getOpenID()
		);
	}

	public WishList getWishListByOpenId(String openId) {
		List<WishList> wishLists = jdbcTemplate.query("SELECT * FROM wishlist_tbl WHERE open_id = ?", rowMapper, openId);
		return wishLists.stream().findFirst().orElse(null);
	}

	public WishList getWishListById(String id) {
		List<WishList> wishLists = jdbcTemplate.query("SELECT * FROM wishlist_tbl WHERE id = ?", rowMapper, id);
		return wishLists.stream().findFirst().orElse(null);
	}
}
