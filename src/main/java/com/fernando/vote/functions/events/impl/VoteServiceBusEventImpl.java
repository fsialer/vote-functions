package com.fernando.vote.functions.events.impl;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.fernando.vote.functions.events.ServiceBusEvent;

public class VoteServiceBusEventImpl implements ServiceBusEvent {
    private static final String CONNECTION_STRING = System.getenv("SERVICE_BUS_CONNECTION");
    private static final String QUEUE_NAME = "vote-queue";

    @Override
    public void sendQueue(String id) {
        try (ServiceBusSenderClient sender = new ServiceBusClientBuilder()
                .connectionString(CONNECTION_STRING)
                .sender()
                .queueName(QUEUE_NAME)
                .buildClient()) {
            // Crear tu JSON o mensaje simple
            String message = String.format("{\"surveyId\": \"%s\"}", id);
            // Enviar el mensaje
            sender.sendMessage(new ServiceBusMessage(message));
        }
    }
}
