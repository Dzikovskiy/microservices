package by.dzikovskiy.usermicroservice.service;

import by.dzikovskiy.usermicroservice.Greeter;
import by.dzikovskiy.usermicroservice.HelloServiceGrpc;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class GreeterGrpcService {
    @GrpcClient("local-grpc-server")
    private HelloServiceGrpc.HelloServiceBlockingStub stub;

    public String sendMessage(String name) {
        try {
            final Greeter.HelloResponse response = this.stub.sayHello(Greeter.HelloRequest.newBuilder().setMessage(name).build());
            return response.getMessage();
        } catch (final StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode().name();
        }
    }
}
