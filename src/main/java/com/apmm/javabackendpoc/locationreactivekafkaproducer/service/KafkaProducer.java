package com.apmm.javabackendpoc.locationreactivekafkaproducer.service;

import com.apmm.javabackendpoc.kafka.avro.model.Location;
import org.springframework.stereotype.Service;

@Service
public interface KafkaProducer {
    void send(String topic, String key, Location value);
}
