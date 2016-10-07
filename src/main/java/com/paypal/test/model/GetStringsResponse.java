package com.paypal.test.model;

import com.paypal.test.util.ObjectMappers;

import java.util.List;

/**
 * Created by seansong on 10/6/16.
 */
public class GetStringsResponse {
    private long id;
    private List<String> strings;

    public GetStringsResponse() {}

    public GetStringsResponse(long id, List<String> strings) {
        this.id = id;
        this.strings = strings;
    }

    public long getId() {
        return id;
    }

    public List<String> getStrings() {
        return strings;
    }

    @Override
    public String toString() {
        return ObjectMappers.toString(this);
    }
}
