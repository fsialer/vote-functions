package com.fernando.vote.functions.models.containers;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vote {
    private String surveyId;
    private String optionId;
}
