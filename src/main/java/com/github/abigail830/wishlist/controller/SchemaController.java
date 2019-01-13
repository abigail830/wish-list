package com.github.abigail830.wishlist.controller;

import com.github.abigail830.wishlist.service.SchemaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/schema")
public class SchemaController {


    @Autowired
    private SchemaService schemaService;

    @ApiOperation(value = "Update schema")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "请求成功")})
    @RequestMapping(value = "", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public void populateSchema(
            @ApiParam(example = "form_id_map_tbl") @RequestParam(value = "schemaName", required = true) String schemaName) {
        schemaService.populateTable(schemaName);

    }
}
