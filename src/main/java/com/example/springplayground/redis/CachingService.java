package com.example.springplayground.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CachingService {
    @Cacheable(value = "String")
    public String getString() {
        log.info("getName() called");
        return "CachingService";
    }

    @Cacheable(value = "Key", key = "#key", condition = "#key < 10 && #key > 0")
    public String getKey(Integer key) {
        log.info("getKey() called");
        return "Key" + key;
    }

    @Cacheable(value = "object")
    public Object getObject(String name, int age) {
        log.info("getObject() called");
        return new SimpleObject(name, age);
    }

    @Cacheable(value = "objects")
    public SimpleObjectList getObjects() {
        log.info("getObjects() called");
        return new SimpleObjectList(List.of(new SimpleObject("Alice", 20), new SimpleObject("Bob", 30)));
    }
}
