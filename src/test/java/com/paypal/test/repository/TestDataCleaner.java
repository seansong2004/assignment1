package com.paypal.test.repository;

import com.paypal.test.repository.StringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by seansong on 10/7/16.
 */
@Component
public class TestDataCleaner {
    @Autowired
    StringRepository repository;

    public void delete(long id) {
        Path path = repository.getPath(id);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            //
        }
    }
}
