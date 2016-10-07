package com.paypal.test.service;

import org.springframework.stereotype.Service;

/**
 * Created by seansong on 10/6/16.
 */
@Service
public class IdGeneratorService {
    public long getId(String s) {
        if (s == null || s.equals(""))
            throw new IllegalArgumentException("bad input:" + s);
        IdGenerator idGenerator = s.chars().collect(IdGenerator::new, IdGenerator::accept, IdGenerator::combine);
        return idGenerator.getId();
    }
}
