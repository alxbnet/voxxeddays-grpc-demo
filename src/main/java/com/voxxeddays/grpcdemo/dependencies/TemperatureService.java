package com.voxxeddays.grpcdemo.dependencies;

import com.voxxeddays.grpcdemo.Coordinates;
import com.voxxeddays.grpcdemo.Temperature;
import com.voxxeddays.grpcdemo.dependencies.TemperatureServiceGrpc.TemperatureServiceImplBase;
import com.voxxeddays.grpcdemo.providers.RandomTemperatureProvider;
import io.grpc.stub.StreamObserver;

import java.util.function.Supplier;

/**
 * Replies with randomly generated {@link Temperature}.
 */
public class TemperatureService extends TemperatureServiceImplBase {

    private final Supplier<Temperature> temperatureProvider = new RandomTemperatureProvider();

    @Override
    public void getCurrent(Coordinates request, StreamObserver<Temperature> responseObserver) {
        responseObserver.onNext(temperatureProvider.get());
        responseObserver.onCompleted();
    }

}
