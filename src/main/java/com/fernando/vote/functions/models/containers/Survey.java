package com.fernando.vote.functions.models.containers;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Survey {
    private String id;
    private String poolId;
    private String type;
    private String question;
    private Integer totalVotes;
    private Boolean closed;
    private long ttl;
}
