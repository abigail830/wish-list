package com.github.abigail830.wishlist.domainv1;


import java.util.List;

public class TakenWishTimeline {
    private List<TakenWishTimelineEntry> takenWishTimelineEntryList;

    public TakenWishTimeline(List<TakenWishTimelineEntry> takenWishTimelineEntryList) {
        this.takenWishTimelineEntryList = takenWishTimelineEntryList;
    }

    public List<TakenWishTimelineEntry> getTakenWishTimelineEntryList() {
        return takenWishTimelineEntryList;
    }

    public void setTakenWishTimelineEntryList(List<TakenWishTimelineEntry> takenWishTimelineEntryList) {
        this.takenWishTimelineEntryList = takenWishTimelineEntryList;
    }
}
