package com.ipdev.evote.grpc.client;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GrpcAppClientApplicationTests {
	@Autowired
	GrpcAppClient client;
	@Test
	public void contextLoads() {
		EVoteServiceGrpc.EVoteServiceBlockingStub service = client.getService();
		Assert.assertNotNull(service);
		PartyResponse blb = service.getAvailableChoices(PartyRequest.newBuilder().setToken("blb").build());
		List<RowBlank> votedList = blb.getVotedList();
		votedList.isEmpty();
	}

	@SpringBootApplication
	static class TestConfiguration {
	}

}
