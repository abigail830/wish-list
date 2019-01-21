package com.github.abigail830.wishlist.domainv1.card;


public class GeneralCouponInfoDTO {

    private BaseInfoDTO base_info;

    private AdvancedInfoDTO advanced_info;

    private String default_detail;

    public GeneralCouponInfoDTO() {

    }

    public GeneralCouponInfoDTO(BaseInfoDTO base_info, AdvancedInfoDTO advanced_info, String default_detail) {
        this.base_info = base_info;
        this.advanced_info = advanced_info;
        this.default_detail = default_detail;
    }

    public BaseInfoDTO getBase_info() {
        return base_info;
    }

    public void setBase_info(BaseInfoDTO base_info) {
        this.base_info = base_info;
    }

    public AdvancedInfoDTO getAdvanced_info() {
        return advanced_info;
    }

    public void setAdvanced_info(AdvancedInfoDTO advanced_info) {
        this.advanced_info = advanced_info;
    }

    public String getDefault_detail() {
        return default_detail;
    }

    public void setDefault_detail(String default_detail) {
        this.default_detail = default_detail;
    }

    @Override
    public String toString() {
        return "GeneralCouponInfoDTO{" +
                "base_info=" + base_info +
                ", advanced_info=" + advanced_info +
                ", default_detail='" + default_detail + '\'' +
                '}';
    }
}
