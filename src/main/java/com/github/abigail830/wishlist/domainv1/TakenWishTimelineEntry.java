package com.github.abigail830.wishlist.domainv1;


import java.util.List;

public class TakenWishTimelineEntry {
    private String asofMonth;

    private List<TakenWishDTO> takenWishDTOList;


    public String getAsofMonth() {
        return asofMonth;
    }

    public List<TakenWishDTO> getTakenWishDTOList() {
        return takenWishDTOList;
    }

    public TakenWishTimelineEntry(String asofMonth, List<TakenWishDTO> takenWishDTOList) {
        this.asofMonth = asofMonth;
        this.takenWishDTOList = takenWishDTOList;
    }

}
