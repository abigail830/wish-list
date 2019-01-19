CREATE TABLE coupon_map_tbl (
  open_id varchar(100) NOT NULL,
  coupon varchar(100) NOT NULL,
  coupon_type varchar(100) NOT NULL,
  coupon_status varchar(100) NOT NULL,
  PRIMARY KEY (open_id,coupon,coupon_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;