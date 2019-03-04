CREATE TABLE implementors_tbl (
  wish_id int NOT NULL,
  implementor_open_id varchar(100) NOT NULL,
  sequence int NOT NULL,
  PRIMARY KEY (wish_id,sequence)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

ALTER TABLE wishlist_tbl ADD implementors_limit int DEFAULT 1;
