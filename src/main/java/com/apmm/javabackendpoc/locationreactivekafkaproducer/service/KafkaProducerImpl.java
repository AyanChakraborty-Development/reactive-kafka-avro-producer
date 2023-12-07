package com.apmm.javabackendpoc.locationreactivekafkaproducer.service;


import com.apmm.javabackendpoc.kafka.avro.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Service;
import reactor.kafka.sender.SenderRecord;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@Slf4j
public class KafkaProducerImpl implements KafkaProducer {
    private final ReactiveKafkaProducerTemplate<String, Location> reactiveKafkaProducerTemplate;

    public KafkaProducerImpl(ReactiveKafkaProducerTemplate<String, Location> reactiveKafkaProducerTemplate) {
        this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
    }

    @Override
    public void send(String topic, String key, Location value) {

       /* log.info("sending to topic={}, {}={}, {}={}", topic, "key", key, "value", value);

        reactiveKafkaProducerTemplate.send(topic, key, value)
                .doOnSuccess(senderResult -> log.info("sent data to topic : {} partition : {} offset : {}", senderResult.recordMetadata().topic(), senderResult.recordMetadata().partition(), senderResult.recordMetadata().offset()))
                .doOnError(throwable -> {
                    log.error("Error while sending the message to kafka: {}", throwable.getLocalizedMessage());
                    log.error("value: {}", value);
                })
                .subscribe();*/

        reactiveKafkaProducerTemplate.send(SenderRecord.create(createProducerRecord(topic, key, value),null))
                .doOnError(error -> log.info("unable to send message due to: {}", error.getMessage()))
                .subscribe(record -> {
                    RecordMetadata metadata = record.recordMetadata();
                    log.info("sent message with partition: {} offset: {}", metadata.partition(), metadata.offset());
                });

    }

    private ProducerRecord<String, Location> createProducerRecord(String topic, String key, Location value) {

        ProducerRecord<String, Location> locationProducerRecord = new ProducerRecord<>(topic, key, value);
        locationProducerRecord.headers().add(KafkaHeaders.MESSAGE_KEY, ("LocationEvent").getBytes(StandardCharsets.UTF_8));
        locationProducerRecord.headers().add(new RecordHeader("EventId", UUID.randomUUID().toString().getBytes()));

        return locationProducerRecord;
    }


}
