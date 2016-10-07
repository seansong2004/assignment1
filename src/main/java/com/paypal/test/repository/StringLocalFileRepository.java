package com.paypal.test.repository;

import com.fasterxml.jackson.databind.JsonMappingException;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by seansong on 10/6/16.
 */
@Repository
public class StringLocalFileRepository {
    @Value("${string.repository.base}")
    private String base;

    private final ConcurrentMap<Long,AtomicLong> lockMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() throws IOException {
        try {
            Files.createDirectories(Paths.get(base));
        }
        catch (FileAlreadyExistsException ignore) {
        }
    }

    private Object getLock(Long key) {
        return lockMap.compute(key, (k,v) -> {
            if (v == null)
                return new AtomicLong(1);
            v.decrementAndGet();
            return v;

        });
    }

    private void releaseLock(Long key) {
        lockMap.computeIfPresent(key, (k,v) -> v.decrementAndGet()==0 ? null : v);
    }

    public void save(StringEntity entity) throws IOException {
        try {
            synchronized (getLock(entity.getId())) {
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
        }
        finally {
            releaseLock(entity.getId());
        }
    }

    public Optional<List<String>> get(long id) throws IOException {
        Path path = getPath(id);
        if (Files.notExists(path)) {
            return Optional.empty();
        }
        else {
            try {
                return Optional.of(ObjectMappers.get().readValue(path.toFile(), List.class));
            }
            catch (JsonMappingException ignore) {
                return Optional.empty();
            }
        }
    }

    Path getPath(long id) {
        return Paths.get(base, (id + ".txt"));
    }
}
