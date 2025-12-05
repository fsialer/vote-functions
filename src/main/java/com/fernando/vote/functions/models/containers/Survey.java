package com.fernando.vote.functions.models.containers;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Survey {
    private String id;
    private String poolId;
    private String question;
    private Long totalVotes;
    private Boolean active;
    private List<Option> options;
}
