package com.paypal.test.model;

import com.paypal.test.entity.StringEntity;

/**
 * Created by seansong on 10/7/16.
 */
public class PostStringResponse {
    private long id;
    private String value;

    public PostStringResponse() {}

    public PostStringResponse(StringEntity entity) {
        id = entity.getId();
        value = entity.getValue();
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }
}
