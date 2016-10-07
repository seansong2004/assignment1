package com.paypal.test.entity;

import com.paypal.test.util.ObjectMappers;

/**
 * Created by seansong on 10/6/16.
 */
public class StringEntity {
    private long id;
    private String value;

    public StringEntity() {}

    public StringEntity(long id, String value) {
        this.id = id;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return ObjectMappers.toString(this);
    }
}
