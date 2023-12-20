package org.example;

import com.generated.grpc.GreetingServiceGrpc;
import com.generated.grpc.GreetingServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import static com.generated.grpc.GreetingServiceOuterClass.HelloRequest.newBuilder;

import java.util.Iterator;

public class Client {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:8080")
                .usePlaintext().build();

        //спец.объект, что будет слать запрос на сервер
        GreetingServiceGrpc.GreetingServiceBlockingStub stub =
                GreetingServiceGrpc.newBlockingStub(channel);

        //можно передавать не все поля из прото-класса
        GreetingServiceOuterClass.HelloRequest request  = GreetingServiceOuterClass.HelloRequest
                .newBuilder().setName("Ithe4th").build();

        //после вызова этого метода с сервера принимается поток, не одно событие
        //пока сервер отправляет данные, будем идти по этому итератору
        Iterator<GreetingServiceOuterClass.HelloResponse> response = stub.greeting(request);
        while(response.hasNext())
                System.out.println(response.next());

        channel.shutdown();
    }
}
