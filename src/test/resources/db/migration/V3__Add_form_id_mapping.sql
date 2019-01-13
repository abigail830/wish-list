CREATE TABLE form_id_map_tbl (
  open_id varchar(100) NOT NULL,
  form_id varchar(100) NOT NULL,
  create_time TIMESTAMP NOT NULL,
  due_time TIMESTAMP NOT NULL,
  PRIMARY KEY (form_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;