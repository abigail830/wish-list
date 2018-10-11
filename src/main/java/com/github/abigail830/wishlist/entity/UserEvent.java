package com.github.abigail830.wishlist.entity;

import java.sql.Timestamp;

public class UserEvent {

    private Integer id;
    private String openId;
    private String eventType;
    private Timestamp eventTime;

    public UserEvent(String openId, String eventType, Timestamp eventTime) {
        this.openId = openId;
        this.eventType = eventType;
        this.eventTime = eventTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Timestamp getEventTime() {
        return eventTime;
    }

    public void setEventTime(Timestamp eventTime) {
        this.eventTime = eventTime;
    }

    @Override
    public String toString() {
        return "UserEvent{" +
                "id=" + id +
                ", openId='" + openId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", eventTime=" + eventTime +
                '}';
    }
}
