package com.voxxeddays.grpcdemo.streaming;

import com.voxxeddays.grpcdemo.Coordinates;
import com.voxxeddays.grpcdemo.Temperature;
import com.voxxeddays.grpcdemo.providers.RandomTemperatureProvider;
import com.voxxeddays.grpcdemo.streaming.TemperatureStreamingServiceGrpc.TemperatureStreamingServiceImplBase;
import io.grpc.stub.StreamObserver;

/**
 * Periodically streams random {@link Temperature} values.
 */
public class TemperatureStreamingService extends TemperatureStreamingServiceImplBase {

    private final RandomValuesStreamer<Temperature> valuesStreamer =
            new RandomValuesStreamer<>(new RandomTemperatureProvider());

    @Override
    public StreamObserver<Coordinates> observe(StreamObserver<Temperature> responseObserver) {
        return valuesStreamer.stream(responseObserver);
    }
}
