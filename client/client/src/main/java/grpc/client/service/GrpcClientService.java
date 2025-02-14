package grpc.client.service;

import com.google.protobuf.Timestamp;
import grpc.proto.IotLogProto;
import grpc.proto.IotLogServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class GrpcClientService {

    private final IotLogServiceGrpc.IotLogServiceStub iotLogServiceStub;

    public void test() {
            StreamObserver<IotLogProto.LogRequest> requestObserver = iotLogServiceStub.streamLogs(new StreamObserver<>() {
                @Override
                public void onNext(IotLogProto.LogResponse logResponse) {
                    log.info(logResponse.toString());
                }

                @Override
                public void onError(Throwable t) {
                    log.error(t.toString());
                    t.printStackTrace();

                }

                @Override
                public void onCompleted() {
                    log.info("completed");
                }
            });

            for(int i = 0; i < 10; i++) {
                requestObserver.onNext(generateLogRequest(i));
            }
            requestObserver.onCompleted();
    }

    public IotLogProto.LogRequest generateLogRequest(int number) {

        int randomInt = (int) ((Math.random() * 100) % 6);

        IotLogProto.LogLevel level = IotLogProto.LogLevel.forNumber(randomInt);

        log.info("{}", randomInt);

        Instant now = Instant.now();
        Timestamp timestamp = Timestamp.newBuilder()
                .setSeconds(now.getEpochSecond())
                .setNanos(now.getNano())
                .build();
        return IotLogProto.LogRequest.newBuilder()
                .setMessage("로그 메세지입니다. : " + number)
                .setLogLevel(level)
                .setDeviceId(number + "")
                .setTimestamp(timestamp)
                .build();
    }

}
