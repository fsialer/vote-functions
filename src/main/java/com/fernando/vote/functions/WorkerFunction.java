package com.fernando.vote.functions;

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
            @ServiceBusQueueTrigger(name = "message", queueName = "vote-queue", connection = "REDIS_PORT") String message,
            final ExecutionContext context
    ) {
        context.getLogger().info("Java Service Bus Queue trigger function executed.");
        context.getLogger().info(message);
    }
}
