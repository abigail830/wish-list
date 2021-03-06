package com.github.abigail830.wishlist.entity;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WishList implements Comparable{

    private Integer id;
    private String openId;
    private String title;
    private String brief;
    private Timestamp createTime;
    private Timestamp dueTime;
    private Integer implementorsLimit;
    private String address;
    private Boolean isSelfWitness;


    @Override
    public int compareTo(Object obj) {
        WishList compareObj = (WishList) obj;
        if (compareObj.getCreateTime() != null && this.createTime != null) {
            return this.createTime.compareTo(compareObj.getCreateTime());
        } else {
            return 0;
        }
    }
}
