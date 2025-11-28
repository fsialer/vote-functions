package com.fernando.vote.functions.models.containers;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Option {
    private String id;
    private String poolId;
    private String type;
    private String description;
    private Integer quantityVotes;
    private long ttl;
}
