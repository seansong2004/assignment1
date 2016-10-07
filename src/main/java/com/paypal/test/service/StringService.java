package com.paypal.test.service;

import com.paypal.test.entity.StringEntity;
import com.paypal.test.exception.NotFoundException;
import com.paypal.test.repository.StringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;

/**
 * Created by seansong on 10/6/16.
 */
@Service
public class StringService {

    private final IdGeneratorService idGeneratorService;
    private final StringRepository repository;

    @Autowired
    public StringService(IdGeneratorService idGeneratorService,
                         StringRepository repository) {
        Assert.notNull(idGeneratorService);
        Assert.notNull(repository);
        this.idGeneratorService = idGeneratorService;
        this.repository = repository;
    }

    public StringEntity save(String s) {
        StringEntity entity = new StringEntity(idGeneratorService.getId(s), s);
        try {
            repository.save(entity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return entity;
    }

    public List<String> get(long id) {
        try {
            return repository.get(id).orElseThrow(() -> new NotFoundException("no string on id " + id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
