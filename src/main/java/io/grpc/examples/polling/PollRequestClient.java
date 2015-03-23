
package io.grpc.examples.polling;

import io.grpc.ChannelImpl;
import io.grpc.examples.polling.PollServiceGrpc.PollServiceBlockingStub;
import io.grpc.examples.polling.PollServiceGrpc.PollServiceStub;
import io.grpc.stub.StreamObserver;
import io.grpc.transport.netty.NegotiationType;
import io.grpc.transport.netty.NettyChannelBuilder;
import java.util.logging.Logger;
import java.util.*;
import java.util.Arrays;

public class PollRequestClient {

private static final Logger logger = Logger.getLogger(PollRequestClient.class.getName());

  private final ChannelImpl channel;
  private final PollServiceGrpc.PollServiceBlockingStub blockingStub;
  //private final PollServiceGrpc.PollServiceStub asyncStub;

  public PollRequestClient(String host, int port) {
    channel = NettyChannelBuilder.forAddress(host, port)
        .negotiationType(NegotiationType.PLAINTEXT)
        .build();
    blockingStub = PollServiceGrpc.newBlockingStub(channel);
    //asyncStub = PollServiceGrpc.newStub(channel);
  }



public static void main(String[] args) throws Exception {

PollRequestClient client = new PollRequestClient("localhost",50051);
String moderatorId = "12345";
String question = "What type of smartphone do you have?";
String startedAt = "2015-02-23T13:00:00.000Z";
String expiredAt = "2015-02-24T13:00:00.000Z";
String[] choice = { "Android","iPhone" };

logger.info("Moderator Id is:::::: " + moderatorId );
logger.info("Question description is:::::: " + question );
logger.info("Poll start at:::::: " + startedAt );
logger.info("Poll expire at:::::: " + expiredAt );
logger.info("Choice of Poll:::::: " +  Arrays.toString(choice) );

PollRequestClient.PollRequestService cl = client.new PollRequestService ();

cl.createPoll(moderatorId,question,startedAt,expiredAt,choice);

}



private class PollRequestService{

public void createPoll (String moderatorId,String question, String startedAt, String  expiredAt, String[] choice) {

PollRequest request = PollRequest.newBuilder().setQuestion(question).setStartedAt(startedAt).setExpiredAt(expiredAt).addChoice(choice[0]).addChoice(choice[1]).build();


PollResponse response = blockingStub.createPoll(request);

logger.info("New Poll id returned from server is:::::: " + response.getId());

}

}

}