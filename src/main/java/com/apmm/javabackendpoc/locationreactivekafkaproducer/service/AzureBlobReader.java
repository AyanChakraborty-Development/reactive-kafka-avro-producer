package com.apmm.javabackendpoc.locationreactivekafkaproducer.service;


import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface AzureBlobReader {
    Mono<List<String>> getListOfBlobNames(String path);
    Mono<String> getBlobContent( String blobName);
}
