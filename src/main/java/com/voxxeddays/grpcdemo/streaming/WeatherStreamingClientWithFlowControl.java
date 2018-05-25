package com.voxxeddays.grpcdemo.streaming;

import com.google.common.primitives.Ints;
import io.grpc.ManagedChannel;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.stub.CallStreamObserver;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import com.voxxeddays.grpcdemo.CaliforniaCoordinates;
import com.voxxeddays.grpcdemo.Coordinates;
import com.voxxeddays.grpcdemo.WeatherRequest;
import com.voxxeddays.grpcdemo.WeatherResponse;

import java.io.IOException;
import java.util.Scanner;

public class WeatherStreamingClientWithFlowControl {

    public static void main(String[] args) throws InterruptedException, IOException {
        ManagedChannel channel = NettyChannelBuilder.forAddress("localhost", 8090).usePlaintext(true).build();

        WeatherStreamingServiceGrpc.WeatherStreamingServiceStub stub = WeatherStreamingServiceGrpc.newStub(channel);

        CallStreamObserver<WeatherRequest> requestStream =
                (CallStreamObserver<WeatherRequest>) stub.observe(new ClientResponseObserver<WeatherRequest, WeatherResponse>() {
                    @Override
                    public void beforeStart(ClientCallStreamObserver outboundStream) {
                        outboundStream.disableAutoInboundFlowControl();
                    }

                    @Override
                    public void onNext(WeatherResponse response) {
                        System.out.printf("Async client onNext: %s%n", response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onCompleted() {
                        System.out.println("Call completed!");
                    }
                });

        WeatherRequest request = WeatherRequest.newBuilder()
                .setCoordinates(Coordinates.newBuilder()
                        .setLatitude(CaliforniaCoordinates.LATITUDE)
                        .setLongitude(CaliforniaCoordinates.LONGITUDE))
                .build();

        requestStream.onNext(request);
        requestStream.request(5); // request up to 5 responses

        boolean keepRunning = true;
        Scanner scanner = new Scanner(System.in);

        while (keepRunning) {
            String input = scanner.nextLine();

            Integer requested = Ints.tryParse(input);
            if (requested != null) {
                System.out.printf("Requested %d responses.%n%n", requested);
                requestStream.request(requested);
            } else if ("q".equals(input)) {
                keepRunning = false;
            }
        }
    }

}
