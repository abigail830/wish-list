package com.github.abigail830.wishlist.dtov1;


import com.github.abigail830.wishlist.entity.FormIDMapping;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FormIDMappingDTO {
    private String openID;
    private String formID;
    private Date createTime;
    private Date dueTime;

    public FormIDMappingDTO(FormIDMapping formIDMapping) {
        this.openID = formIDMapping.getOpenId();
        this.formID = formIDMapping.getFormId();
        this.createTime = formIDMapping.getCreateTime();
        this.dueTime = formIDMapping.getDueTime();
    }

}
