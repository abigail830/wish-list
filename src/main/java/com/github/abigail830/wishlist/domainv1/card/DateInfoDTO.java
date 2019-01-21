package com.github.abigail830.wishlist.domainv1.card;

public class DateInfoDTO {

    private String type;

    private int begin_timestamp;

    private int end_timestamp;

    private int fixed_begin_term;

    public DateInfoDTO(String type, int begin_timestamp, int end_timestamp, int fixed_begin_term) {
        this.type = type;
        this.begin_timestamp = begin_timestamp;
        this.end_timestamp = end_timestamp;
        this.fixed_begin_term = fixed_begin_term;
    }

    public DateInfoDTO(){

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBegin_timestamp() {
        return begin_timestamp;
    }

    public void setBegin_timestamp(int begin_timestamp) {
        this.begin_timestamp = begin_timestamp;
    }

    public int getEnd_timestamp() {
        return end_timestamp;
    }

    public void setEnd_timestamp(int end_timestamp) {
        this.end_timestamp = end_timestamp;
    }

    public int getFixed_begin_term() {
        return fixed_begin_term;
    }

    public void setFixed_begin_term(int fixed_begin_term) {
        this.fixed_begin_term = fixed_begin_term;
    }

    @Override
    public String toString() {
        return "DateInfoDTO{" +
                "type='" + type + '\'' +
                ", begin_timestamp=" + begin_timestamp +
                ", end_timestamp=" + end_timestamp +
                ", fixed_begin_term=" + fixed_begin_term +
                '}';
    }
}
