package com.github.abigail830.wishlist.dto.v1;


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
