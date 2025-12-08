package com.fernando.vote.functions.repository;

import java.util.Map;

public interface CacheRepository {
    Long createHashSet(String key, String attribute);
    Boolean existsKey(String key);
    void appendKey(String key,String value);
    Map<String,String> getHashSet(String key);
    String getSet(String key);
    String createSet(String key, String value,Boolean ttl);

}
