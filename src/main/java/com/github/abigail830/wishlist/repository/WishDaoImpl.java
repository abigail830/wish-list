package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.entity.Wish;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishDaoImpl {

	private static final Logger logger = LoggerFactory.getLogger(WishDaoImpl.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<Wish> rowMapper = new BeanPropertyRowMapper<>(Wish.class);

	public void createWish(Wish wish) {
		logger.info("Going to create wish for wishlist [wish_list_id={}].", wish.getWishListId());
		jdbcTemplate.update(
				"REPLACE INTO wish_tbl (wish_list_id, description, wish_status, implementor_open_id) " +
						"VALUES (?, ?, ?, ?)",
				wish.getWishListId(),
				wish.getDescription(),
				wish.getWishStatus(),
				wish.getImplementorOpenId()
		);
	}

	public void updateWish(Wish wish) {
		logger.info("Going to update wish for wishlist [wish_list_id={}].", wish.getWishListId());
		jdbcTemplate.update("UPDATE wish_tbl set description=?, wish_status=?, implementor_open_id=? " +
						"where wish_list_id=?",
				wish.getDescription(),
				wish.getWishStatus(),
				wish.getImplementorOpenId(),
				wish.getWishListId()
		);
	}

	public Wish getWishByWishListId(String wishListID) {
		List<Wish> wishes = jdbcTemplate.query("SELECT * FROM wish_tbl WHERE wish_list_id = ?", rowMapper, wishListID);
		return wishes.stream().findFirst().orElse(null);
	}

	public Wish getWishByID(String id) {
		List<Wish> wishes = jdbcTemplate.query("SELECT * FROM wish_tbl WHERE id = ?", rowMapper, id);
		return wishes.stream().findFirst().orElse(null);
	}
}
