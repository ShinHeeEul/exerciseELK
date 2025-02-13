package grpc.client.configuration;

import grpc.proto.IotLogServiceGrpc;
import io.grpc.ManagedChannel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.grpc.client.*;

@Configuration
public class GrpcClientConfig {

    @Bean
    IotLogServiceGrpc.IotLogServiceStub stub(GrpcChannelFactory channels) {
        ManagedChannel channel = channels.createChannel("localhost:9090");
        return IotLogServiceGrpc.newStub(channel);
    }


}
