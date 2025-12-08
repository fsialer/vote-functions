package com.fernando.vote.functions.models.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OptionResponse {
    private String optionId;
    private String text;
    private Long votes;
}
