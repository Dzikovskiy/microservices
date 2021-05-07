package by.dzikovskiy.postgresmicro.service;

import by.dzikovskiy.usermicroservice.Greeter;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = GreeterGrpcService.class)
class GreeterGrpcServiceTest {

    @Autowired
    private GreeterGrpcService grpcService;

    @Test
    void testSayHello() throws Exception {
        //given
        Greeter.HelloRequest request = Greeter.HelloRequest.newBuilder().setMessage("World").build();
        StreamRecorder<Greeter.HelloResponse> responseObserver = StreamRecorder.create();
        //when
        grpcService.sayHello(request, responseObserver);
        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }
        //then
        assertNull(responseObserver.getError());
        List<Greeter.HelloResponse> results = responseObserver.getValues();
        assertEquals(1, results.size());
        Greeter.HelloResponse response = results.get(0);
        assertEquals("Hello World", response.getMessage());
    }
}