package com.github.abigail830.wishlist.domainv1.card;

public class BaseInfoDTO {
    private String logo_url;
    private String code_type;
    private String brand_name;
    private String title;
    private String color;
    private String notice;
    private String description;
    private SkuDTO sku;
    private DateInfoDTO date_info;

    public BaseInfoDTO() {

    }

    public BaseInfoDTO(String logo_url, String code_type, String brand_name, String title, String color, String notice,
                       String description, SkuDTO sku, DateInfoDTO date_info) {
        this.logo_url = logo_url;
        this.code_type = code_type;
        this.brand_name = brand_name;
        this.title = title;
        this.color = color;
        this.notice = notice;
        this.description = description;
        this.sku = sku;
        this.date_info = date_info;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getCode_type() {
        return code_type;
    }

    public void setCode_type(String code_type) {
        this.code_type = code_type;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SkuDTO getSku() {
        return sku;
    }

    public void setSku(SkuDTO sku) {
        this.sku = sku;
    }

    public DateInfoDTO getDate_info() {
        return date_info;
    }

    public void setDate_info(DateInfoDTO date_info) {
        this.date_info = date_info;
    }

    @Override
    public String toString() {
        return "BaseInfoDTO{" +
                "logo_url='" + logo_url + '\'' +
                ", code_type='" + code_type + '\'' +
                ", brand_name='" + brand_name + '\'' +
                ", title='" + title + '\'' +
                ", color='" + color + '\'' +
                ", notice='" + notice + '\'' +
                ", description='" + description + '\'' +
                ", sku=" + sku +
                ", date_info=" + date_info +
                '}';
    }
}
