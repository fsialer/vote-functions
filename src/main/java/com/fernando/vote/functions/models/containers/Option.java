package com.fernando.vote.functions.models.containers;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Option {
    private String id;
    private String text;
    private Long votes;
}
