package com.fernando.vote.functions.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteRequest {
    @JsonProperty("pool_id")
    @NotNull(message = "pool_id is required.")
    private String poolId;
    @JsonProperty("option_id")
    @NotNull(message = "option_id is required.")
    private String optionId;
}
