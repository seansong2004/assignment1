package com.paypal.test.repository;

import com.paypal.test.entity.StringEntity;
import com.paypal.test.util.ObjectMappers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Created by seansong on 10/6/16.
 */
@Repository
public class StringRepository {
    @Value("${string.repository.base}")
    private String base;

    @PostConstruct
    public void init() throws IOException {
        try {
            Files.createDirectories(Paths.get(base));
        }
        catch (FileAlreadyExistsException ignore) {
        }
    }

    public void save(StringEntity entity) throws IOException {
        Optional<List<String>> existing = get(entity.getId());
        Set<String> set = new ConcurrentSkipListSet<>();
        if (existing.isPresent()) {
            set.addAll(existing.get());
        }
        if (set.contains(entity.getValue()))
            return;

        set.add(entity.getValue());
        ObjectMappers.get().writeValue(getPath(entity.getId()).toFile(), set);
    }

    public Optional<List<String>> get(long id) throws IOException {
        Path path = getPath(id);
        if (Files.notExists(path))
            return Optional.empty();
        else
            return Optional.of(ObjectMappers.get().readValue(path.toFile(), List.class));
    }

    Path getPath(long id) {
        return Paths.get(base, (id + ".txt"));
    }
}
