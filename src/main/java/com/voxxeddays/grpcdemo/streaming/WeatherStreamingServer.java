package com.voxxeddays.grpcdemo.streaming;

import io.grpc.Server;
import io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.NettyServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import com.voxxeddays.grpcdemo.streaming.HumidityStreamingServiceGrpc.HumidityStreamingServiceStub;
import com.voxxeddays.grpcdemo.streaming.TemperatureStreamingServiceGrpc.TemperatureStreamingServiceStub;
import com.voxxeddays.grpcdemo.streaming.WindStreamingServiceGrpc.WindStreamingServiceStub;

import java.io.IOException;

/**
 * Starts weather, temperature, humidity and wind streaming servers.
 */
public class WeatherStreamingServer {

    public static void main(String[] args) throws IOException, InterruptedException {

        String host = "localhost";
        int temperaturePort = 8081;
        int humidityPort = 8082;
        int windPort = 8083;

        Server temperatureServer = NettyServerBuilder.forPort(temperaturePort)
                .addService(new TemperatureStreamingService()).build().start();
        Server humidityServer = NettyServerBuilder.forPort(humidityPort)
                .addService(new HumidityStreamingService()).build().start();
        Server windServer = NettyServerBuilder.forPort(windPort)
                .addService(new WindStreamingService()).build().start();

        TemperatureStreamingServiceStub temperatureStub =
                TemperatureStreamingServiceGrpc.newStub(NettyChannelBuilder.forAddress(host, temperaturePort).usePlaintext(true).build());
        HumidityStreamingServiceStub humidityStub =
                HumidityStreamingServiceGrpc.newStub(NettyChannelBuilder.forAddress(host, humidityPort).usePlaintext(true).build());
        WindStreamingServiceStub windStub =
                WindStreamingServiceGrpc.newStub(NettyChannelBuilder.forAddress(host, windPort).usePlaintext(true).build());

        WeatherStreamingService weatherService = new WeatherStreamingService(temperatureStub, humidityStub, windStub);
        Server weatherServer = NettyServerBuilder.forPort(8090).addService(weatherService)
                .addService(ProtoReflectionService.newInstance())
                .build().start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            weatherServer.shutdownNow();
            temperatureServer.shutdownNow();
            humidityServer.shutdownNow();
            windServer.shutdownNow();
        }));

        weatherServer.awaitTermination();
    }
}
