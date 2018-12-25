ALTER TABLE wishlist_tbl ADD brief varchar(255) DEFAULT NULL;
ALTER TABLE wishlist_tbl CHANGE COLUMN  description title varchar(255) DEFAULT NULL;