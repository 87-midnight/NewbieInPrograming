package com.lcg.sample.entity;

import lombok.Data;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.cache.decorators.FifoCache;

import java.io.Serializable;

@Data
@CacheNamespace(eviction = FifoCache.class,flushInterval = 10000,size = 2048)
public class User implements Serializable {

    private Long id;
    private String username;
    private String password;
    private Integer gender;
}
