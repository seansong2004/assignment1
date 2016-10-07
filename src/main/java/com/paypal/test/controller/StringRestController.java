package com.paypal.test.controller;

import com.paypal.test.entity.StringEntity;
import com.paypal.test.exception.NotFoundException;
import com.paypal.test.model.GetStringsResponse;
import com.paypal.test.service.StringService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by seansong on 10/6/16.
 */
@Api(value = "strings", description = "Add and view your strings")
@RestController
@RequestMapping(value = "/api/v1/string", headers = "Accept=application/json")
class StringRestController {
    private static final Logger logger = LoggerFactory.getLogger(StringRestController.class);
    private final StringService stringService;
    @Autowired
    public StringRestController(StringService stringService) {
        Assert.notNull(stringService);
        this.stringService = stringService;
    }

    @ApiOperation(value = "add a string")
    @RequestMapping(method = {RequestMethod.POST})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<StringEntity> addString(@RequestBody String string) throws Exception {
        return new ResponseEntity<>(stringService.save(string), HttpStatus.CREATED);
    }

    @ApiOperation(value = "get strings matching id")
    @RequestMapping(value="{sid}", method = {RequestMethod.GET})
    public
    @ResponseBody
    ResponseEntity<GetStringsResponse> get(@PathVariable String sid) throws NotFoundException, IOException {
        try {
            long id = Long.parseLong(sid);
            return new ResponseEntity<>(
                    new GetStringsResponse(id, stringService.get(id)),
                    HttpStatus.OK);
        }
        catch (Throwable e) {
            logger.error(e.getMessage(), e);
            throw new NotFoundException("");
        }
    }
}
