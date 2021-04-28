package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.usermicroservice.Greeter.HelloRequest;
import by.dzikovskiy.usermicroservice.Greeter.HelloResponse;
import by.dzikovskiy.usermicroservice.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GreeterGrpcService extends HelloServiceGrpc.HelloServiceImplBase {
    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver) {
        HelloResponse response = HelloResponse.newBuilder()
                .setMessage("Hello " + request.getMessage())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
