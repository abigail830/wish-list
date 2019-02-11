package com.github.abigail830.wishlist.service;

import com.github.abigail830.wishlist.repository.SchemaDAOImpl;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class SchemaService {

    public static final Map<String, String> schemaMap =
            ImmutableMap.of(
                    "form_id_map_tbl","CREATE TABLE form_id_map_tbl (" +
                                                "  open_id varchar(100) NOT NULL," +
                                                "  form_id varchar(100) NOT NULL," +
                                                "  create_time TIMESTAMP NOT NULL," +
                                                "  due_time TIMESTAMP NOT NULL," +
                                                "  PRIMARY KEY (form_id)" +
                                                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4",
                    "coupon_map_tbl", "CREATE TABLE coupon_map_tbl (" +
                                                "  open_id varchar(100) NOT NULL," +
                                                "  coupon varchar(100) NOT NULL," +
                                                "  coupon_type varchar(100) NOT NULL," +
                                                "  coupon_status varchar(100) NOT NULL," +
                                                "  PRIMARY KEY (open_id,coupon,coupon_type)" +
                                                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");


    @Autowired
    private SchemaDAOImpl schemaDAO;

    public void populateTable (String tableName){
        if (!schemaDAO.isTableExist(tableName)) {
            log.info("Start to populate table " + tableName);
            schemaDAO.updateSchema(schemaMap.get(tableName));
            log.info("Populate table completed " + tableName);

        } else {
            log.info("Table is already existed or not in use" + tableName);
        }
    }
}
