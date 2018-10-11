USE wishlist;

CREATE TABLE user_tbl (
  ID int NOT NULL AUTO_INCREMENT,
  open_id varchar(255) NOT NULL,
  gender varchar(45) DEFAULT NULL,
  nickName varchar(255) DEFAULT NULL,
  city varchar(255) DEFAULT NULL,
  country varchar(255) DEFAULT NULL,
  province varchar(255) DEFAULT NULL,
  lang varchar(45) DEFAULT NULL,
  PRIMARY KEY (ID),
  UNIQUE open_id
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE wishlist_tbl (
  ID int NOT NULL AUTO_INCREMENT,
  open_id varchar(255) NOT NULL,
  description varchar(255) DEFAULT NULL,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE wish_tbl (
  ID int NOT NULL AUTO_INCREMENT,
  wishlist_id int NOT NULL,
  description varchar(255) DEFAULT NULL,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_DATE(),
  last_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  status varchar(255) DEFAULT NULL,
  implementor_open_id varchar(255) DEFAULT NULL,
  PRIMARY KEY (ID,wishlist_id),
  FOREIGN KEY (wishlist_id) REFERENCES wishlist_tbl(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE user_event (
  ID int NOT NULL AUTO_INCREMENT,
  open_id varchar(255) NOT NULL,
  event_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  event_type varchar(255) NOT NULL,
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
