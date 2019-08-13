package org.gumiho.rest.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Service
public class KafkaServiceImpl implements KafkaService {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaServiceImpl.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String json, String topic) {
        String message = System.currentTimeMillis() + topic;
        //LOG.info("topic="+topic+",message="+message);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, json);
        future.addCallback(success -> LOG.info("KafkaMessageProducer 发送消息成功！"), fail -> LOG.error("KafkaMessageProducer 发送消息失败！"));

    }
}
