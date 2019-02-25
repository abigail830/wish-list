package com.github.abigail830.wishlist.dto.v1;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QRCodeRequestDTO {
    int width;
    int height;
    String content;

}
