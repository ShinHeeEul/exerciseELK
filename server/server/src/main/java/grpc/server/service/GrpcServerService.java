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
            @Override
            public void onNext(IotLogProto.LogRequest logRequest) {
                if(logRequest.getLogLevel() == IotLogProto.LogLevel.INFO) {
                    log.info(logRequest.getMessage());
                } else if(logRequest.getLogLevel() == IotLogProto.LogLevel.DEBUG) {
                    log.debug(logRequest.getMessage());
                } else if(logRequest.getLogLevel() == IotLogProto.LogLevel.WARN) {
                    log.warn(logRequest.getMessage());
                } else {
                    log.error(logRequest.getMessage());
                }
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                IotLogProto.LogResponse logResponse = IotLogProto.LogResponse.newBuilder().setMessage("전송 완료").build();
                log.info("completed : " + logResponse.getMessage());
                responseObserver.onNext(logResponse);
                responseObserver.onCompleted();
            }
        };
    }

}
