package com.github.abigail830.wishlist.domainv1.card;

public class APITicketDTO {

    private String errcode;
    private String errmsg;
    private String ticket;
    private long expires_in;
    private long validInSecond;

    public APITicketDTO(String errcode, String errmsg, long expires_in, String ticket, long validInSecond) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.expires_in = expires_in;
        this.ticket = ticket;
        this.validInSecond = validInSecond;
    }

    public APITicketDTO(){

    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public long getValidInSecond() {
        return validInSecond;
    }

    public void setValidInSecond(long validInSecond) {
        this.validInSecond = validInSecond;
    }

    @Override
    public String toString() {
        return "APITicketDTO{" +
                "errcode='" + errcode + '\'' +
                ", errmsg='" + errmsg + '\'' +
                ", ticket='" + ticket + '\'' +
                ", expires_in=" + expires_in +
                ", validInSecond=" + validInSecond +
                '}';
    }
}
