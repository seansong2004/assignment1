package com.paypal.test.controller;

import com.paypal.test.entity.StringEntity;
import com.paypal.test.model.GetStringsResponse;
import com.paypal.test.model.PostStringResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by seansong on 10/6/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StringRestControllerRTest {
    @Autowired
    private TestRestTemplate rest;

    @Test
    public void testPostAndGet() {
        ResponseEntity<PostStringResponse> postResponse = rest.postForEntity("/api/v1/string", "abc", PostStringResponse.class);
        long id = postResponse.getBody().getId();
        Assert.assertEquals(489, id);

        ResponseEntity<GetStringsResponse> getResponse = rest.getForEntity("/api/v1/string/" + id, GetStringsResponse.class);
        Assert.assertEquals(489, getResponse.getBody().getId());
        Assert.assertEquals("abc", getResponse.getBody().getStrings().get(0));
    }

    @Test
    public void testNotExistingString() {
        ResponseEntity<GetStringsResponse> getResponse = rest.getForEntity("/api/v1/string/999999999", GetStringsResponse.class);
        Assert.assertEquals(404, getResponse.getStatusCodeValue());

        getResponse = rest.getForEntity("/api/v1/string/fffff", GetStringsResponse.class);
        Assert.assertEquals(404, getResponse.getStatusCodeValue());
    }

}
