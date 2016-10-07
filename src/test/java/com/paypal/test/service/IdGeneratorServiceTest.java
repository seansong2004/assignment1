package com.paypal.test.service;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by seansong on 10/6/16.
 */
public class IdGeneratorServiceTest {
    private IdGeneratorService service = new IdGeneratorService();

    @Test
    public void testNormal() {
        Assert.assertEquals(97 + (97 + 98) + (98 + 99), service.getId("abc"));
    }

    @Test
    public void testDuplicateCharacter() {
        Assert.assertEquals(97 + (97 + 98) + (98) + (98 + 99), service.getId("abbc"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNullIsNotAllowed() {
        service.getId(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testBlankStringIsNotAllowed() {
        service.getId("");
    }

}
