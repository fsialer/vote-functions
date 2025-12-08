package com.fernando.vote.functions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fernando.vote.functions.mapper.VoteMapper;
import com.fernando.vote.functions.models.requests.PoolIdRequest;
import com.fernando.vote.functions.services.IVoteService;
import com.fernando.vote.functions.services.impl.IVoteServiceImpl;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Service Bus Trigger.
 */
public class WorkerFunction {
    /**
     * This function will be invoked when a new message is received at the Service Bus Queue.
     */
    @FunctionName("WorkerFunction")
    public void run(
            @ServiceBusQueueTrigger(name = "message", queueName = "vote-queue", connection = "SERVICE_BUS_CONNECTION") String message,
            final ExecutionContext context
    ) {

        try {
            ObjectMapper objectMapper=new ObjectMapper();
            PoolIdRequest poolIdRequest =objectMapper.readValue(message, PoolIdRequest.class);
            VoteMapper voteMapper=new VoteMapper();
            IVoteService iVoteService=new IVoteServiceImpl();
            iVoteService.synVotes(voteMapper.surveyIdRequestToSurveyId(poolIdRequest));
            context.getLogger().info("Votes saved.");
        } catch (RuntimeException | JsonProcessingException  e) {
            context.getLogger().severe(e.getMessage());
        }
    }


}
