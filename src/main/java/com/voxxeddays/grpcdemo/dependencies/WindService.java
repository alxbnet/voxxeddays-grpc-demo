package com.voxxeddays.grpcdemo.dependencies;

import com.voxxeddays.grpcdemo.Coordinates;
import com.voxxeddays.grpcdemo.Wind;
import com.voxxeddays.grpcdemo.dependencies.WindServiceGrpc.WindServiceImplBase;
import com.voxxeddays.grpcdemo.providers.RandomWindProvider;
import io.grpc.stub.StreamObserver;

import java.util.function.Supplier;

/**
 * Replies with randomly generated {@link Wind}.
 */
public class WindService extends WindServiceImplBase {

    private final Supplier<Wind> windProvider = new RandomWindProvider();

    @Override
    public void getCurrent(Coordinates request, StreamObserver<Wind> responseObserver) {
        responseObserver.onNext(windProvider.get());
        responseObserver.onCompleted();
    }
}