package com.voxxeddays.grpcdemo.streaming;

import com.voxxeddays.grpcdemo.Coordinates;
import com.voxxeddays.grpcdemo.Humidity;
import com.voxxeddays.grpcdemo.providers.RandomHumidityProvider;
import com.voxxeddays.grpcdemo.streaming.HumidityStreamingServiceGrpc.HumidityStreamingServiceImplBase;
import io.grpc.stub.StreamObserver;

/**
 * Periodically streams random {@link Humidity} values.
 */
public class HumidityStreamingService extends HumidityStreamingServiceImplBase {

    private final RandomValuesStreamer<Humidity> valuesStreamer =
            new RandomValuesStreamer<>(new RandomHumidityProvider());

    @Override
    public StreamObserver<Coordinates> observe(StreamObserver<Humidity> responseObserver) {
        return valuesStreamer.stream(responseObserver);
    }
}
