package com.fernando.vote.functions.mapper;

import com.fernando.vote.functions.models.containers.Option;
import com.fernando.vote.functions.models.containers.Pool;
import com.fernando.vote.functions.models.requests.PoolRequest;

import java.util.stream.Collectors;

public class PoolMapper {
    public Pool poolRequestToPool(PoolRequest poolRequest){
        return Pool.builder()
                .question(poolRequest.getQuestion())
                .options(poolRequest.getOptions().stream().map(optionRequest -> Option.builder()
                        .optionId("OP"+optionRequest.getOptionId().toString())
                        .text(optionRequest.getDescription())
                        .build()).collect(Collectors.toList()))
                .build();
    }
}
