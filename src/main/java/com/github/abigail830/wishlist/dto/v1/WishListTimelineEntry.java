package com.github.abigail830.wishlist.dto.v1;

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
