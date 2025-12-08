package com.fernando.vote.functions.mapper;

import com.fernando.vote.functions.models.containers.Option;
import com.fernando.vote.functions.models.containers.Pool;
import com.fernando.vote.functions.models.requests.PoolRequest;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class PoolMapper {
    public Pool poolRequestToPool(PoolRequest poolRequest){
        AtomicInteger index = new AtomicInteger(0);
        return Pool.builder()
                .question(poolRequest.getQuestion())
                .options(poolRequest.getOptions().stream().map(option -> Option.builder()
                        .optionId("OP"+index.getAndIncrement())
                        .text(option)
                        .build()).collect(Collectors.toList()))
                .build();
    }
}
