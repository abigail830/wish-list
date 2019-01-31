package com.github.abigail830.wishlist.entity;


import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FormIDMapping {

    private String openId;
    private String formId;
    private Timestamp createTime;
    private Timestamp dueTime;

}
