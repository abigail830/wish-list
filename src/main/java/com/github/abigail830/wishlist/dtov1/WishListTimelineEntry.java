package com.github.abigail830.wishlist.dtov1;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WishListTimelineEntry {

    private String asofMonth;

    private List<WishListDTO> wishListDTOList;

}
