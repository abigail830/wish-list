package com.github.abigail830.wishlist.domainv1.card;


public class CardDTO {
    private String card_type;

    private GeneralCouponInfoDTO general_coupon;

    public CardDTO(String card_type, GeneralCouponInfoDTO general_coupon) {
        this.card_type = card_type;
        this.general_coupon = general_coupon;
    }

    public CardDTO(){

    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public GeneralCouponInfoDTO getGeneral_coupon() {
        return general_coupon;
    }

    public void setGeneral_coupon(GeneralCouponInfoDTO general_coupon) {
        this.general_coupon = general_coupon;
    }

    @Override
    public String toString() {
        return "CardDTO{" +
                "card_type='" + card_type + '\'' +
                ", general_coupon=" + general_coupon +
                '}';
    }
}
