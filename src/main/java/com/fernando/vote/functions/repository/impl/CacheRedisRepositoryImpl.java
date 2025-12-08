package com.fernando.vote.functions.repository.impl;

import com.fernando.vote.functions.repository.CacheRepository;
import redis.clients.jedis.JedisPooled;

import java.util.Map;

public class CacheRedisRepositoryImpl implements CacheRepository {
    private static final String REDIS_HOST= System.getenv("REDIS_HOST");
    private static final Integer REDIS_PORT= Integer.valueOf(System.getenv("REDIS_PORT"));
    private static final  Boolean REDIS_SSL= Boolean.parseBoolean(System.getenv("REDIS_SSL"));
    private static final int EXPIRATION_TIME_IN_SECOND=Integer.parseInt(System.getenv("EXPIRATION_TIME_IN_SECOND"));

    @Override
    public Long createHashSet(String key, String attribute) {
        try(JedisPooled jedis = new JedisPooled(
                REDIS_HOST,
                REDIS_PORT,
                REDIS_SSL
        )) {
            return jedis.hincrBy(key,attribute,1);
        }
    }

    @Override
    public Boolean existsKey(String key) {
        try(JedisPooled jedis = new JedisPooled(
                REDIS_HOST,
                REDIS_PORT,
                REDIS_SSL
        )) {
            return jedis.exists(key);
        }
    }

    @Override
    public void appendKey(String key, String value) {
        try(JedisPooled jedis = new JedisPooled(
                REDIS_HOST,
                REDIS_PORT,
                REDIS_SSL
        )) {
            jedis.append(key,value);
        }
    }

    @Override
    public Map<String, String> getHashSet(String key) {
        try(JedisPooled jedis = new JedisPooled(
                REDIS_HOST,
                REDIS_PORT,
                REDIS_SSL
        )) {
            return jedis.hgetAll(key);
        }
    }

    @Override
    public String getSet(String key) {
        try(JedisPooled jedis = new JedisPooled(
                REDIS_HOST,
                REDIS_PORT,
                REDIS_SSL
        )) {
            return jedis.get(key);
        }
    }

    @Override
    public String createSet(String key, String value,Boolean ttl) {
        try(JedisPooled jedis = new JedisPooled(
                REDIS_HOST,
                REDIS_PORT,
                REDIS_SSL
        )) {
            if(ttl){
                return jedis.setex(key,EXPIRATION_TIME_IN_SECOND,value);
            }
            return jedis.set(key,value);
        }
    }
}
