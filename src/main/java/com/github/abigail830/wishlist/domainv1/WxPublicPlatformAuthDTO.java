package com.github.abigail830.wishlist.domainv1;

public class WxPublicPlatformAuthDTO {
    private String access_token;

    private String expires_in;

    private String errcode;

    private String errmsg;

    private long validInSecond;

    public WxPublicPlatformAuthDTO() {
    }

    public WxPublicPlatformAuthDTO(String access_token, String expires_in, String errcode, String errmsg, long validInSecond) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.validInSecond = validInSecond;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
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

    public long getValidInSecond() {
        return validInSecond;
    }

    public void setValidInSecond(long validInSecond) {
        this.validInSecond = validInSecond;
    }
}
