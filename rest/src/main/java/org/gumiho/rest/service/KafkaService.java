package org.gumiho.rest.service;

public interface KafkaService {
    void send(String json, String topic);
}
