package com.fernando.vote.functions;

import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.vote.functions.mapper.PoolMapper;
import com.fernando.vote.functions.models.containers.Pool;
import com.fernando.vote.functions.models.requests.PoolRequest;
import com.fernando.vote.functions.services.PoolService;
import com.fernando.vote.functions.services.impl.PoolServiceImpl;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * Azure Functions with HTTP Trigger.
 */
public class PoolCreateFunction {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ValidatorFactory factory = Validation.byDefaultProvider()
            .configure()
            .messageInterpolator(new org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator())
            .buildValidatorFactory();
    private final Validator validator = factory.getValidator();

    @FunctionName("PoolCreateFunction")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) throws JsonProcessingException {
        String body = request.getBody().orElse(null);
        if (body == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Request body is required\"}").build();
        }
        
        PoolRequest poolRequest = objectMapper.readValue(body, PoolRequest.class);

        Set<ConstraintViolation<PoolRequest>> violations = validator.validate(poolRequest);
        if (!violations.isEmpty()) {
            String errors = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(","));
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + errors + "\"}").build();
        }

        try {
            PoolMapper mapper=new PoolMapper();
            PoolService poolService =new PoolServiceImpl();
            Pool createdPool = poolService.createPool(mapper.poolRequestToPool(poolRequest));
            
            Map<String, String> response = new HashMap<>();
            response.put("id", createdPool.getPoolId());
            response.put("message", "Survey created successfully");
            
            return request.createResponseBuilder(HttpStatus.CREATED)
                    .header("Content-Type", "application/json")
                    .body(response).build();
        } catch (RuntimeException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            Throwable cause = ex.getCause();
            errorResponse.put("error", "Error:");
            errorResponse.put("message", ex.getMessage());
            context.getLogger().severe("Error: " + cause);
            context.getLogger().severe("Error: " + ex.getCause().getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body(errorResponse).build();
        }
    }
}
