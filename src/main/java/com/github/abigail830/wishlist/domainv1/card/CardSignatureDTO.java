package com.github.abigail830.wishlist.domainv1.card;

public class CardSignatureDTO {
    private String nonceStr;
    private String timestamp;
    private String signature;
    private String cardID;

    public CardSignatureDTO(String nonceStr, String cardID, String signature, String timestamp) {
        this.nonceStr = nonceStr;
        this.cardID = cardID;
        this.signature = signature;
        this.timestamp = timestamp;
    }

    public CardSignatureDTO(){

    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "CardSignatureDTO{" +
                "nonceStr='" + nonceStr + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", signature='" + signature + '\'' +
                ", cardID='" + cardID + '\'' +
                '}';
    }
}
