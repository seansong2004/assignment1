package com.paypal.test.service;

import org.junit.Assert;
import org.junit.Test;

import java.util.stream.IntStream;

/**
 * Created by seansong on 10/6/16.
 */
public class IdGeneratorTest {
    @Test
    public void testNormal() {
        IdGenerator idGenerator = IntStream.of(1, 2, 3).collect(IdGenerator::new, IdGenerator::accept, IdGenerator::combine);
        Assert.assertEquals(1+(1+2)+(2+3), idGenerator.getId());
    }

    @Test
    public void testSamePreviousNumberShouldNotBeCounted() {
        IdGenerator idGenerator = IntStream.of(2, 2, 3).collect(IdGenerator::new, IdGenerator::accept, IdGenerator::combine);
        Assert.assertEquals(2+2+(2+3), idGenerator.getId());
    }
}
