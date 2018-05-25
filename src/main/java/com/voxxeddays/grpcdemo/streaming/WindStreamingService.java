package com.voxxeddays.grpcdemo.streaming;

import com.voxxeddays.grpcdemo.Coordinates;
import com.voxxeddays.grpcdemo.Wind;
import com.voxxeddays.grpcdemo.providers.RandomWindProvider;
import com.voxxeddays.grpcdemo.streaming.WindStreamingServiceGrpc.WindStreamingServiceImplBase;
import io.grpc.stub.StreamObserver;

/**
 * Periodically streams random {@link Wind} values.
 */
public class WindStreamingService extends WindStreamingServiceImplBase {

    private final RandomValuesStreamer<Wind> valuesStreamer = new RandomValuesStreamer<>(new RandomWindProvider());

    @Override
    public StreamObserver<Coordinates> observe(StreamObserver<Wind> responseObserver) {
        return valuesStreamer.stream(responseObserver);
    }

}
