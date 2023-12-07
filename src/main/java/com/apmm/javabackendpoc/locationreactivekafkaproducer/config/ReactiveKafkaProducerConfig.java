package com.apmm.javabackendpoc.locationreactivekafkaproducer.config;


import com.apmm.javabackendpoc.kafka.avro.model.Location;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ReactiveKafkaProducerConfig {

    @Bean
    public ReactiveKafkaProducerTemplate<String, Location> reactiveKafkaProducerTemplate() {
        return new ReactiveKafkaProducerTemplate<>(SenderOptions.create(producerConfig()));
    }

    public Map<String, Object> producerConfig() {
        Map<String, Object> properties = new ConcurrentHashMap<>();
//        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "broker-addesss");
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, KafkaAvroSerializer.class);
        properties.put(ProducerConfig.ACKS_CONFIG, "0");
        properties.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "1000");
        properties.put(ProducerConfig.RETRIES_CONFIG, "0");
        properties.put(ProducerConfig.CLIENT_ID_CONFIG, "DEMO_APP_1");
        properties.put("schema.registry.url", "http://127.0.0.1:8081");

//        properties.put("security.protocol", "SASL_SSL");
//        properties.put(SaslConfigs.SASL_MECHANISM, "PLAIN");
//        properties.put(SaslConfigs.SASL_JAAS_CONFIG, "org.apache.kafka.common.security.plain.PlainLoginModule required username='username' password='password';");
        return properties;
    }

}
