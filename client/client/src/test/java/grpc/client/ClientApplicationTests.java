package grpc.client;

import grpc.client.service.GrpcClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClientApplicationTests {


	@Autowired
	private GrpcClientService grpcClientService;

	@Test
	void contextLoads() {
		grpcClientService.test();
	}

}
