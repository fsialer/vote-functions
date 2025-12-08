package com.fernando.vote.functions;

import java.io.IOException;
import java.util.*;

import com.fernando.vote.functions.models.containers.Survey;
import com.fernando.vote.functions.services.ISurveyService;
import com.fernando.vote.functions.services.impl.SurveyServiceImpl;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import static com.fernando.vote.functions.models.enums.VoteErrorCatalog.VOTE_ERROR;

/**
 * Azure Functions with HTTP Trigger.
 */
public class PoolGetFunction {
    /**
     * This function listens at endpoint "/api/PoolGetFunction". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/PoolGetFunction
     * 2. curl {your host}/api/PoolGetFunction?name=HTTP%20Query
     */
    @FunctionName("PoolGetFunction")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.FUNCTION, route = "{id}") HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        String id = request.getUri().getPath().split("/")[2]; // Se asume que la ruta sigue el patrón "/hola/{id}"
        try{
            ISurveyService iSurveyService=new SurveyServiceImpl();
            Survey pool=iSurveyService.getPool(id);
            return request.createResponseBuilder(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(pool).build();

        }catch (RuntimeException | IOException ex){
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", VOTE_ERROR.getCode());
            errorResponse.put("message", VOTE_ERROR.getMessage());
            context.getLogger().severe( VOTE_ERROR.getCode()+":" + ex.getMessage());
            return request.createResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body(errorResponse).build();
        }



    }
}
