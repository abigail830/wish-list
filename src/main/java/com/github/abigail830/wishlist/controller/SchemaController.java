package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.service.SchemaService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schema")
public class SchemaController {


    @Autowired
    private SchemaService schemaService;

    @PutMapping
    public void populateSchema(
            @ApiParam(example = "form_id_map_tbl") @RequestParam(value = "schemaName", required = true) String schemaName) {
        schemaService.populateTable(schemaName);

    }
}
