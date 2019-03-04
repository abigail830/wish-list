package com.github.abigail830.wishlist.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Wish {
    private Integer id;
    private Integer wishListId;
    private String description;
    private Timestamp createTime;
    private Timestamp lastUpdateTime;
    private String wishStatus;
    private String implementorOpenId;
    private User implementor;
    private User Creator;
    private Timestamp dueTime;
    private Integer implementorLimit;
    private List<User> implementors;

}
