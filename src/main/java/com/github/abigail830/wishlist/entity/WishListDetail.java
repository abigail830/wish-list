package com.github.abigail830.wishlist.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class WishListDetail {

    private Integer listId;
    private String listOpenId;
    private String listDescription;
    private Timestamp listCreateTime;
    private Timestamp listDueTime;

    List<Wish> wishes = new ArrayList<>();

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getListOpenId() {
        return listOpenId;
    }

    public void setListOpenId(String listOpenId) {
        this.listOpenId = listOpenId;
    }

    public String getListDescription() {
        return listDescription;
    }

    public void setListDescription(String listDescription) {
        this.listDescription = listDescription;
    }

    public Timestamp getListCreateTime() {
        return listCreateTime;
    }

    public void setListCreateTime(Timestamp listCreateTime) {
        this.listCreateTime = listCreateTime;
    }

    public Timestamp getListDueTime() {
        return listDueTime;
    }

    public void setListDueTime(Timestamp listDueTime) {
        this.listDueTime = listDueTime;
    }

    public List<Wish> getWishes() {
        return wishes;
    }

    public void setWishes(List<Wish> wishes) {
        this.wishes = wishes;
    }

    public void addWish(Wish wish){
        this.wishes.add(wish);
    }

    @Override
    public String toString() {
        return "WishListDetail{" +
                "listId=" + listId +
                ", listOpenId='" + listOpenId + '\'' +
                ", listDescription='" + listDescription + '\'' +
                ", listCreateTime=" + listCreateTime +
                ", listDueTime=" + listDueTime +
                ", wishes=" + wishes +
                '}';
    }
}
