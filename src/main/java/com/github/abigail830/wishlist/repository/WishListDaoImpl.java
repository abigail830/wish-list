package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.entity.WishList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
public class WishListDaoImpl {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private RowMapper<WishList> rowMapper = new BeanPropertyRowMapper<>(WishList.class);

	public void createWishList(WishList wishList) {
		log.info("Going to create wish list for user: {}", wishList.toString());
		jdbcTemplate.update(
				"INSERT INTO wishlist_tbl (open_id, title, brief, create_time, due_time, implementors_limit) VALUES (?,?,?,?,?,?)",
				wishList.getOpenId(),
				wishList.getTitle(),
				wishList.getBrief(),
				wishList.getCreateTime(),
				wishList.getDueTime(),
				wishList.getImplementorsLimit()
		);
	}

	public void updateWishListByID(WishList wishList) {
		log.info("Going to update wish list for user : {}", wishList.toString());
		jdbcTemplate.update(
				"UPDATE wishlist_tbl set title=?, brief=?, due_time=? where ID=?",
				wishList.getTitle(),
				wishList.getBrief(),
				wishList.getDueTime(),
				wishList.getId()
		);
	}

	public void deleteWishList(Integer wishListID ) {
		log.info("Going to delete wish list {}", wishListID);
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
