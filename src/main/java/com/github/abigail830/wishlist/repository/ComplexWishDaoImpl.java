package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.entity.Wish;
import com.github.abigail830.wishlist.entity.WishListDetail;
import com.github.abigail830.wishlist.util.Constants;
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
public class ComplexWishDaoImpl {

    private static final Logger logger = LoggerFactory.getLogger(ComplexWishDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Wish> wishRowMapper = new BeanPropertyRowMapper<>(Wish.class);

    private RowMapper<WishListDetail> wishListDetailRowMapper = new BeanPropertyRowMapper<>(WishListDetail.class);

    /**
     * This is to query the wish complete count for:
     * - wish is raised by others (not myself)
     * - wish is implemented by me
     * - wish_status is DONE
     *
     * @param openId
     * @return
     */
    public int getFriendsWishCompletedCountByImplementorOpenID(String openId) {
        List<Wish> wishes = jdbcTemplate.query("select wish_tbl.ID " +
                "from wish_tbl, wishlist_tbl " +
                "where wishlist_tbl.id = wish_tbl.wish_list_id " +
                "and wish_tbl.implementor_open_id=? and wishlist_tbl.open_id != ? " +
                "and wish_tbl.wish_status='DONE'", wishRowMapper, openId, openId);
        return wishes.size();
    }

    /**
     * This is to query the wish complete count for:
     * - wish list is created by speicify openId
     * - wish_status is DONE
     *
     * @param openId
     * @return
     */
    public int getMyWishCompletedCount(String openId) {
        String sql = "select wish_tbl.ID from wish_tbl, wishlist_tbl " +
                "where wishlist_tbl.id = wish_tbl.wish_list_id " +
                "and wishlist_tbl.open_id = ? " +
                "and wish_status='"+ Constants.WISH_STATUS_DONE+"'";
        List<Wish> wishes = jdbcTemplate.query(sql, wishRowMapper, openId);
        return wishes.size();
    }

    public WishListDetail getWishListDetail(String wishListId) {
        return jdbcTemplate.query("select wishlist_tbl.ID as list_id, " +
                        "wishlist_tbl.open_id as list_open_id, " +
                        "wishlist_tbl.description as list_description, " +
                        "wishlist_tbl.create_time as list_create_time, " +
                        "wishlist_tbl.due_time as list_due_time, " +
                        "wish_tbl.ID as ID, " +
                        "wish_tbl.wish_list_id as wish_list_id, " +
                        "wish_tbl.description as description, " +
                        "wish_tbl.create_time as create_time, " +
                        "wish_tbl.last_update_time as last_update_time, " +
                        "wish_tbl.wish_status as wish_status, " +
                        "wish_tbl.implementor_open_id as implementor_open_id " +
                        "from wishlist_tbl, wish_tbl " +
                        "where wishlist_tbl.id = wish_tbl.wish_list_id " +
                        "and wishlist_tbl.id = ?",
                new ResultSetExtractor<WishListDetail>(){

                    @Override
                    public WishListDetail extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                        WishListDetail wishListDetail = null;
                        int row = 0;
                        while (resultSet.next()) {
                            if (wishListDetail == null) {
                                wishListDetail = wishListDetailRowMapper.mapRow(resultSet, row);
                            }
                            wishListDetail.addWish(wishRowMapper.mapRow(resultSet, row));
                            row++;
                        }
                        return wishListDetail;
                    }
                }, wishListId);
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
