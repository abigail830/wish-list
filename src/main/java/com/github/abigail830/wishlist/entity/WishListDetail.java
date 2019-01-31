package com.github.abigail830.wishlist.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WishListDetail {

    private Integer listId;
    private String listOpenId;
    private String listTitle;
    private String listBrief;
    private Timestamp listCreateTime;
    private Timestamp listDueTime;

    List<Wish> wishes = new ArrayList<>();

    public void addWish(Wish wish){
        wishes.add(wish);
    }

}
