package com.paypal.test.service;

import java.util.function.IntConsumer;

/**
 * Created by seansong on 10/6/16.
 */
class IdGenerator implements IntConsumer {
    private int prev = 0;
    private long id = 0;

    public long getId() {
        return id;
    }

    @Override
    public void accept(int value) {
        if (value == prev) {
            id += value;
        }
        else {
            id += (prev + value);
        }
        prev = value;
    }

    static void combine(IdGenerator r, IdGenerator r1) {
        throw new UnsupportedOperationException();
    }
}
