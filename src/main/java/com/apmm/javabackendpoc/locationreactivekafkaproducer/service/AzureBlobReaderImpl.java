package com.apmm.javabackendpoc.locationreactivekafkaproducer.service;

import com.azure.core.util.BinaryData;
import com.azure.storage.blob.BlobAsyncClient;
import com.azure.storage.blob.BlobContainerAsyncClient;
import com.azure.storage.blob.models.BlobItem;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;



@Service
public class AzureBlobReaderImpl implements AzureBlobReader{
    private final BlobContainerAsyncClient blobContainerAsyncClient;

    public AzureBlobReaderImpl(BlobContainerAsyncClient blobContainerAsyncClient) {
        this.blobContainerAsyncClient = blobContainerAsyncClient;
    }

    @Override
    public Mono<List<String>> getListOfBlobNames(String path) {

        return blobContainerAsyncClient.listBlobs()
                .map(BlobItem::getName)
                .collectList();
    }

    @Override
    public Mono<String> getBlobContent(String blobName) {
        BlobAsyncClient blobClient = blobContainerAsyncClient.getBlobAsyncClient(blobName);

        return blobClient
                .downloadContent()
                .map(BinaryData::toString)
                .single();
    }
}
