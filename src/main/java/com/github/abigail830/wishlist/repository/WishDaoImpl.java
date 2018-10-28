package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.util.Toggle;
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
		logger.info("Update wish for wishlist [wish_list_id={}].", wish.getId());
		jdbcTemplate.update("UPDATE wish_tbl set description=?, wish_status=?, implementor_open_id=? " +
						"where wish=?",
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
}
