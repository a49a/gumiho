package org.gumiho.rest.service;

public interface KafkaService {
    void sendInfo(String json, String topic);
}
