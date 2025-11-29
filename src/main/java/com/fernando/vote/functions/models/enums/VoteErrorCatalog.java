package com.fernando.vote.functions.models.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VoteErrorCatalog {
    VOTE_ERROR("VOTE_000","Error inesperado."),
    VOTE_RECURRENT("VOTE_001","You voted in the survey.");
    private final String code;
    private final String message;
}
