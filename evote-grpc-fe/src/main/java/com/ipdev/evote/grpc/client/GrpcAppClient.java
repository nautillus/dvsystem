package com.ipdev.evote.grpc.client;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Component
public class GrpcAppClient {
    @GrpcClient("grpc-server")
    private EVoteServiceGrpc.EVoteServiceBlockingStub service;
    public EVoteServiceGrpc.EVoteServiceBlockingStub getService() {
        return service;
    }
}
