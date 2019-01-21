package com.github.abigail830.wishlist.domainv1.card;

public class CouponDTO {
    private CardDTO card;

    public CouponDTO(){

    }

    public CouponDTO(CardDTO card) {
        this.card = card;
    }

    public CardDTO getCard() {
        return card;
    }

    public void setCard(CardDTO card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "CouponDTO{" +
                "card=" + card +
                '}';
    }
}
