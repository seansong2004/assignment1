package com.paypal.test.repository;

import com.paypal.test.entity.StringEntity;
import com.paypal.test.service.IdGeneratorService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

/**
 * Created by seansong on 10/6/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StringRepositoryITest {
    @Autowired
    StringRepository repository;

    @Autowired
    IdGeneratorService idGeneratorService;

    @Test
    public void testFilePath() {
        Path path = repository.getPath(1);
        Assert.assertEquals("1.txt", path.getFileName().toString());
    }

    @Test
    public void testCreateAndGet() throws IOException {
        // delete test file
        long id = 1;
        Path path = repository.getPath(id);
        Files.deleteIfExists(path);
        Assert.assertFalse("failed to delete test file", Files.exists(path));

        try {
            // save string
            StringEntity entity = new StringEntity(id, "abc");
            repository.save(entity);
            Assert.assertTrue("test file creation failed", Files.exists(path));

            // read file and compare value
            List<String> saved = repository.get(entity.getId()).orElse(Collections.EMPTY_LIST);
            Assert.assertEquals(1, saved.size());
            Assert.assertEquals("abc", saved.get(0));

            // save one more string with same id
            entity = new StringEntity(id, "def");
            repository.save(entity);
            saved = repository.get(entity.getId()).orElse(Collections.EMPTY_LIST);
            Assert.assertEquals(2, saved.size());
            Assert.assertEquals("abc", saved.get(0));
            Assert.assertEquals("def", saved.get(1));
        }
        finally {
            // delete test file
            Files.deleteIfExists(path);
            Assert.assertFalse("failed to delete test file", Files.exists(path));
        }
    }

}
