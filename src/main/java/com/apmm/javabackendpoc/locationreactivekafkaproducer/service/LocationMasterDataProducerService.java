package com.apmm.javabackendpoc.locationreactivekafkaproducer.service;


import com.apmm.javabackendpoc.kafka.avro.model.Location;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class LocationMasterDataProducerService implements CommandLineRunner {

    private final AzureBlobReader azureBlobReader;

    private final KafkaProducer kafkaProducer;

    private final Mapper mapper;



    public LocationMasterDataProducerService(AzureBlobReader azureBlobReader, KafkaProducer kafkaProducer, Mapper mapper) {
        this.azureBlobReader = azureBlobReader;
        this.kafkaProducer = kafkaProducer;
        this.mapper = mapper;
    }

    public void triggerFlow(){

        var key = "LocationEvent";
        var topic = "test.topic.4";

        log.info("starting ...............");

/*        azureBlobReader.getListOfBlobNames("json_data")
                .subscribe(blobListNames -> {

                    blobListNames.forEach(blob -> {

                        azureBlobReader.getBlobContent(blob)
                                .subscribe(content -> {

                                    kafkaProducer.send(topic, String.valueOf(key), mapper.mapToLocation(content));
                                });
                    });
                });*/


        /*Mono<List<String>> blobNamesMono = azureBlobReader.getListOfBlobNames("json_data");

        Mono<List<String>> blobContentsMono = blobNamesMono
                .flatMapMany(Flux::fromIterable)
                .flatMap(azureBlobReader::getBlobContent)
                .collectList();

        Mono<List<Location>> locationListMono = blobContentsMono
                .flatMapMany(Flux::fromIterable)
                .flatMap(mapper::mapToLocation)
                .collectList();

        locationListMono
                .flatMapMany(Flux::fromIterable)
                .onErrorContinue((throwable, o) -> log.error("Error!! {}",o.toString()))
                .subscribe(
                        location -> kafkaProducer.send(topic, String.valueOf(key), location)
                );*/

        azureBlobReader
                .getListOfBlobNames("json_data")
                .flatMapMany(Flux::fromIterable)
                .flatMap(azureBlobReader::getBlobContent)
                .flatMap(mapper::mapToLocation)
                .subscribe(
                        location -> kafkaProducer.send(topic, key, location)
                );

    }

    @Override
    public void run(String... args) throws Exception {
        triggerFlow();
    }
}
