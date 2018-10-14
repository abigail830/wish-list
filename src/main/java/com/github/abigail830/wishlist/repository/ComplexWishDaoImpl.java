package com.github.abigail830.wishlist.repository;

import com.github.abigail830.wishlist.entity.Wish;
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
public class ComplexWishDaoImpl {

    private static final Logger logger = LoggerFactory.getLogger(ComplexWishDaoImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Wish> wishRowMapper = new BeanPropertyRowMapper<>(Wish.class);

    private RowMapper<WishList> wishListRowMapper = new BeanPropertyRowMapper<>(WishList.class);

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
        List<Wish> wishes = jdbcTemplate.query("select wish.id " +
                        "from wish_tbl, wishlist_tbl " +
                        "where wishlist_tbl.id = wish_tbl.wish_list_id\n" +
                        "and wish_tbl.implementor_open_id=? and wishlist_tbl.open_id != ? " +
                        "and wish_tbl.wish_status='DONE';",
                wishRowMapper, openId, openId);
        return wishes.size();
    }

    public int getMyWishCompletedCount(String openId) {
        List<Wish> wishes = jdbcTemplate.query("select wish.id " +
                        "from wish_tbl, wishlist_tbl " +
                        "where wishlist_tbl.id = wish_tbl.wish_list_id\n" +
                        "and and wishlist_tbl.open_id = ? " +
                        "and wish_status='DONE';",
                wishRowMapper, openId);
        return wishes.size();
    }

}
