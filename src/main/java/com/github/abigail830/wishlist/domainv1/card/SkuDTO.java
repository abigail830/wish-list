package com.github.abigail830.wishlist.domainv1.card;

public class SkuDTO {

    private int quantity;

    public SkuDTO(int quantity) {
        this.quantity = quantity;
    }

    public SkuDTO() {

    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "SkuDTO{" +
                "quantity=" + quantity +
                '}';
    }
}
