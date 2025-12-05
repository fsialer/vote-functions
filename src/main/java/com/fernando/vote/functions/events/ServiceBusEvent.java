package com.fernando.vote.functions.events;

public interface ServiceBusEvent {
    void sendQueue(String id);
}
