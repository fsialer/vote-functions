package com.fernando.vote.functions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.vote.functions.exceptions.VoteSendException;
import com.fernando.vote.functions.mapper.VoteMapper;
import com.fernando.vote.functions.models.requests.VoteRequest;
import com.fernando.vote.functions.services.IVoteService;
import com.fernando.vote.functions.services.impl.IVoteServiceImpl;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fernando.vote.functions.models.enums.VoteErrorCatalog.*;

public class VoteCreateFunction {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ValidatorFactory factory = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator())
            .buildValidatorFactory();
    private final Validator validator = factory.getValidator();

    @FunctionName("VoteCreateFunction")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) throws JsonProcessingException {
        String body = request.getBody().orElse(null);
        Map<String, String> headers=request.getHeaders();
        if (body == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Request body is required\"}").build();
        }

        VoteRequest voteRequest = objectMapper.readValue(body, VoteRequest.class);

        Set<ConstraintViolation<VoteRequest>> violations = validator.validate(voteRequest);
        if (!violations.isEmpty()) {
            String errors = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(","));
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + errors + "\"}").build();
        }

        try {
            IVoteService iVoteService=new IVoteServiceImpl();
            VoteMapper voteMapper=new VoteMapper();
            long totalVotes = iVoteService.registerVote(voteMapper.voteRequestToVote(voteRequest));

            Map<String, String> response = new HashMap<>();
            response.put("votes", String.valueOf(totalVotes));
            response.put("message", "Vote created successfully");

            return request.createResponseBuilder(HttpStatus.CREATED)
                    .header("Content-Type", "application/json")
                    .body(response).build();
        } catch (RuntimeException ex) {
            Map<String, String> errorResponse = new HashMap<>();

            if(ex instanceof VoteSendException){
                errorResponse.put("error", VOTE_RECURRENT.getCode());
                errorResponse.put("message", VOTE_RECURRENT.getMessage());
                context.getLogger().severe(VOTE_RECURRENT.getCode()+": " + ex.getMessage());
            }else{
                errorResponse.put("error", VOTE_ERROR.getCode());
                errorResponse.put("message", VOTE_ERROR.getMessage());
                context.getLogger().severe( VOTE_ERROR.getCode() + ex.getMessage());
            }

            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body(errorResponse).build();
        }
    }
}
