package com.voxxeddays.grpcdemo.dependencies;

import com.voxxeddays.grpcdemo.Coordinates;
import com.voxxeddays.grpcdemo.Humidity;
import com.voxxeddays.grpcdemo.dependencies.HumidityServiceGrpc.HumidityServiceImplBase;
import com.voxxeddays.grpcdemo.providers.RandomHumidityProvider;
import io.grpc.stub.StreamObserver;

import java.util.function.Supplier;

/**
 * Replies with randomly generated {@link Humidity}.
 */
public class HumidityService extends HumidityServiceImplBase {

    private final Supplier<Humidity> humidityProvider = new RandomHumidityProvider();

    @Override
    public void getCurrent(Coordinates request, StreamObserver<Humidity> responseObserver) {
        responseObserver.onNext(humidityProvider.get());
        responseObserver.onCompleted();
    }

}
