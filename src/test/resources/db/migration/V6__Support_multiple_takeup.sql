CREATE TABLE implementors_tbl (
  wish_id int NOT NULL,
  implementor_open_id varchar(100) NOT NULL,
  sequence int NOT NULL,
  PRIMARY KEY (wish_id,sequence)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;