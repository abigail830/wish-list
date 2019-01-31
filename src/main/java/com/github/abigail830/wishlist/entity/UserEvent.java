package com.github.abigail830.wishlist.entity;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserEvent {

    private Integer id;
    private String openId;
    private String eventType;
    private Timestamp eventTime;

    public UserEvent(String openId) {
        this.openId = openId;
    }

}
