package com.paypal.test.controller;

import com.paypal.test.entity.StringEntity;
import com.paypal.test.model.GetStringsResponse;
import com.paypal.test.model.PostStringResponse;
import com.paypal.test.repository.TestDataCleaner;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;

/**
 * Created by seansong on 10/6/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StringRestControllerRTest {
    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private TestDataCleaner cleaner;

    @Test
    public void testPostAndGet() {
        ResponseEntity<PostStringResponse> postResponse = rest.postForEntity("/api/v1/string", "abc", PostStringResponse.class);
        long id = postResponse.getBody().getId();
        Assert.assertEquals(489, id);

        ResponseEntity<GetStringsResponse> getResponse = rest.getForEntity("/api/v1/string/" + id, GetStringsResponse.class);
        Assert.assertEquals(489, getResponse.getBody().getId());
        Assert.assertEquals("abc", getResponse.getBody().getStrings().get(0));

        cleaner.delete(489);
    }

    @Test
    public void testNotExistingString() {
        ResponseEntity<GetStringsResponse> getResponse =
                rest.getForEntity("/api/v1/string/999999999", GetStringsResponse.class);
        Assert.assertEquals(404, getResponse.getStatusCodeValue());

        getResponse = rest.getForEntity("/api/v1/string/fffff", GetStringsResponse.class);
        Assert.assertEquals(404, getResponse.getStatusCodeValue());
    }

    @Test
    public void testConcurrecy() {
        CompletableFuture<ResponseEntity<PostStringResponse>> job1 = postAsync("abcde");
        CompletableFuture<ResponseEntity<PostStringResponse>> job2 = postAsync("abcde");

        CompletableFuture<Long> idFuture = job1.thenCombine(job2, (r1,r2) -> {
            if (r1.getStatusCodeValue() >= 300) {
                System.out.println("r1:" + r1.getBody());
            }
            if (r2.getStatusCodeValue() >= 300) {
                System.out.println("r2:" + r2.getBody());
            }
            Assert.assertEquals(201, r1.getStatusCodeValue());
            Assert.assertEquals(201, r2.getStatusCodeValue());
            Assert.assertEquals("abcde", r1.getBody().getValue());
            Assert.assertEquals("abcde", r2.getBody().getValue());
            Assert.assertEquals(r1.getBody().getId(), r2.getBody().getId());
            return r1.getBody().getId();
        });
        long id = idFuture.join();

        ResponseEntity<GetStringsResponse> getResponse = rest.getForEntity("/api/v1/string/" + id, GetStringsResponse.class);
        Assert.assertEquals(id, getResponse.getBody().getId());
        Assert.assertEquals("abcde", getResponse.getBody().getStrings().get(0));

        cleaner.delete(id);
    }

    private CompletableFuture<ResponseEntity<PostStringResponse>> postAsync(String string) {
        return CompletableFuture.supplyAsync(() -> rest.postForEntity("/api/v1/string",  string, PostStringResponse.class));
    }


}
