--ALTER TABLE wishlist_tbl MODIFY COLUMN create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
--ALTER TABLE wish_tbl MODIFY COLUMN create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
--ALTER TABLE wish_tbl MODIFY COLUMN last_update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;