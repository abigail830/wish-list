USE wishlist;

CREATE TABLE user_tbl (
  ID int NOT NULL AUTO_INCREMENT,
  open_id varchar(100) unique NOT NULL,
  gender varchar(45) DEFAULT NULL,
  nick_name varchar(255) DEFAULT NULL,
  city varchar(255) DEFAULT NULL,
  country varchar(255) DEFAULT NULL,
  province varchar(255) DEFAULT NULL,
  lang varchar(45) DEFAULT NULL,
  avatar_url varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE wishlist_tbl (
  ID int NOT NULL AUTO_INCREMENT,
  open_id varchar(100) NOT NULL,
  description varchar(255) DEFAULT NULL,
  create_time TIMESTAMP NOT NULL,
  due_time TIMESTAMP NOT NULL,
  implementors_limit int DEFAULT 1,
  address varchar(255) DEFAULT NULL;
  is_self_witness boolean DEFAULT false;
  PRIMARY KEY (ID),
  FOREIGN KEY (open_id) REFERENCES user_tbl(open_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE wish_tbl (
  ID int NOT NULL AUTO_INCREMENT,
  wish_list_id int NOT NULL,
  description varchar(255) DEFAULT NULL,
  create_time TIMESTAMP NOT NULL,
  last_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  wish_status varchar(255) DEFAULT NULL,
  implementor_open_id varchar(100) DEFAULT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (wish_list_id) REFERENCES wishlist_tbl(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE user_event (
  ID int NOT NULL AUTO_INCREMENT,
  open_id varchar(100) NOT NULL,
  event_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  event_type varchar(255) NOT NULL,
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

create INDEX open_id on wishlist.wishlist_tbl (open_id);

create INDEX implementor_open_id on wishlist.wish_tbl (implementor_open_id);

CREATE TABLE form_id_map_tbl (
  open_id varchar(100) NOT NULL,
  form_id varchar(100) NOT NULL,
  create_time TIMESTAMP NOT NULL,
  due_time TIMESTAMP NOT NULL,
  PRIMARY KEY (form_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE coupon_map_tbl (
  open_id varchar(100) NOT NULL,
  coupon varchar(100) NOT NULL,
  coupon_type varchar(100) NOT NULL,
  coupon_status varchar(100) NOT NULL,
  PRIMARY KEY (open_id,coupon,coupon_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE implementors_tbl (
  wish_id int NOT NULL,
  implementor_open_id varchar(100) NOT NULL,
  sequence int NOT NULL,
  PRIMARY KEY (wish_id,sequence)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;