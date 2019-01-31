package com.github.abigail830.wishlist.dtov1;


import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TakenWishTimeline {
    private List<TakenWishTimelineEntry> takenWishTimelineEntryList;

}
