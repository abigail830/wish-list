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
