package com.fernando.vote.functions.events.impl;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.fernando.vote.functions.events.ServiceBusEvent;

public class ServiceBusEventImpl implements ServiceBusEvent {
    private static final String CONNECTION_STRING = System.getenv("SERVICE_BUS_CONNECTION");
    private static final String QUEUE_NAME = "vote-queue";

    public void sendVote(String id,String poolId) {

        // Crear el cliente
        try (ServiceBusSenderClient sender = new ServiceBusClientBuilder()
                .connectionString(CONNECTION_STRING)
                .sender()
                .queueName(QUEUE_NAME)
                .buildClient()) {
            // Crear tu JSON o mensaje simple
            String mensaje = String.format("{\"id\": \"%s\", \"poolId\": %d}", id, poolId);

            // Enviar el mensaje
            sender.sendMessage(new ServiceBusMessage(mensaje));
            System.out.println("Voto enviado: " + mensaje);
        }
    }

    @Override
    public void sendQueue(String id, String poolId) {
        try (ServiceBusSenderClient sender = new ServiceBusClientBuilder()
                .connectionString(CONNECTION_STRING)
                .sender()
                .queueName(QUEUE_NAME)
                .buildClient()) {
            // Crear tu JSON o mensaje simple
            String mensaje = String.format("{\"id\": \"%s\", \"poolId\": %d}", id, poolId);
            // Enviar el mensaje
            sender.sendMessage(new ServiceBusMessage(mensaje));
        }
    }
}
