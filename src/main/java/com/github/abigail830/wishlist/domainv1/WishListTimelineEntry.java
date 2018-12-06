package com.github.abigail830.wishlist.domainv1;

import java.util.List;
import java.util.Map;

public class WishListTimelineEntry {

    private String asofMonth;

    private List<WishListDTO> wishListDTOList;


    public String getAsofMonth() {
        return asofMonth;
    }

    public List<WishListDTO> getWishListDTOList() {
        return wishListDTOList;
    }

    public WishListTimelineEntry(String asofMonth, List<WishListDTO> wishListDTOList) {
        this.asofMonth = asofMonth;
        this.wishListDTOList = wishListDTOList;
    }

    @Override
    public String toString() {
        return "WishListTimelineEntry{" +
                "asofMonth='" + asofMonth + '\'' +
                ", wishListDTOList=" + wishListDTOList +
                '}';
    }
}
