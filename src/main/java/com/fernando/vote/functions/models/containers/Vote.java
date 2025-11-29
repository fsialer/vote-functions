package com.fernando.vote.functions.models.containers;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vote {
    private String id;
    private String poolId;
    private String optionId;
    private String ip;
    private String device;
    private String type;
    private long ttl;
}
