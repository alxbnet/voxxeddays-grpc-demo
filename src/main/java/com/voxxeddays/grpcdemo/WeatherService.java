package com.voxxeddays.grpcdemo;

import io.grpc.stub.StreamObserver;

/**
 * Returns hard-coded weather response.
 */
public class WeatherService extends WeatherServiceGrpc.WeatherServiceImplBase {

    @Override
    public void getCurrent(WeatherRequest request, StreamObserver<WeatherResponse> responseObserver) {
        WeatherResponse response = WeatherResponse.newBuilder()
                .setTemperature(Temperature.newBuilder().setUnits(Temperature.Units.CELSUIS).setDegrees(20.f))
                .setHumidity(Humidity.newBuilder().setValue(.65f))
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
