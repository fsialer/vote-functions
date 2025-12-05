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
    @JsonProperty("survey_id")
    @NotNull(message = "survey_id is required.")
    private String surveyId;
    @JsonProperty("option_id")
    @NotNull(message = "option_id is required.")
    private String optionId;
}
