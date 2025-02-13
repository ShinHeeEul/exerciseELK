package grpc.server.service;


import grpc.proto.IotLogProto;
import grpc.proto.IotLogServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@Slf4j
public class GrpcServerService extends IotLogServiceGrpc.IotLogServiceImplBase {


    //grpc 프레임워크한테 StreamObserver<HelloRequest> 반환. 이걸 반환해줘야 grpc 프레임워크 측에서 요청을 받을 수 있음.
    @Override
    public StreamObserver<IotLogProto.LogRequest> streamLogs(StreamObserver<IotLogProto.LogResponse> responseObserver) {
        return new StreamObserver<>() {
            final StringBuilder messages = new StringBuilder();

            @Override
            public void onNext(IotLogProto.LogRequest logRequest) {
                // TODO 여기서 Kafka로 보내는 로직 처리
                messages.append(logRequest.getMessage()).append("\n");
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                IotLogProto.LogResponse logResponse = IotLogProto.LogResponse.newBuilder().setMessage(messages.toString()).build();
                System.out.println(messages);
                responseObserver.onNext(logResponse);
                responseObserver.onCompleted();
            }
        };
    }

}
