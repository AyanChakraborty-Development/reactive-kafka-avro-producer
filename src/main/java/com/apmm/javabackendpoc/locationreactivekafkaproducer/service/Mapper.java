package com.apmm.javabackendpoc.locationreactivekafkaproducer.service;


import com.apmm.javabackendpoc.kafka.avro.model.Location;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class Mapper {

    private final ObjectMapper objectMapper;

    public Mapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public Mono<Location> mapToLocation(String jsonData)  {
        return Mono.justOrEmpty(objectMapper.readValue(jsonData, Location.class));

    }
}
