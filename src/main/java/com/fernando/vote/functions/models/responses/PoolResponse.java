package com.fernando.vote.functions.models.responses;

import com.fernando.vote.functions.models.containers.Option;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PoolResponse {
    private String id;
    private String poolId;
    private String question;
    private Long totalVotes;
    private Boolean active;
    private List<OptionResponse> options;
}
