package io.grpc.examples.polling;
import io.grpc.ChannelImpl;
import io.grpc.transport.netty.NegotiationType;
import io.grpc.transport.netty.NettyChannelBuilder;
import io.grpc.ServerImpl;
import io.grpc.stub.StreamObserver;
import io.grpc.transport.netty.NettyServerBuilder;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.logging.Logger;

public class PollRequestServer {

private static final Logger logger = Logger.getLogger(PollRequestServer.class.getName());

private int port=50051;
private ServerImpl server;

public void start() {
    server = NettyServerBuilder.forPort(port)
        .addService(PollServiceGrpc.bindService(new PollRequestService()))
        .build().start();
  }



 public static void main(String[] args) throws Exception {
    PollRequestServer server = new PollRequestServer();
    server.start();
  }



private static class PollRequestService implements PollServiceGrpc.PollService {
@Override
public void createPoll (PollRequest request, StreamObserver<PollResponse> responseObserver) {

SecureRandom random = new SecureRandom();
String rannum = new BigInteger(50, random).toString(36);

logger.info("New Poll id created is::: " + rannum);

PollResponse reponse = PollResponse.newBuilder().setId(rannum).build();

responseObserver.onValue(reponse);

responseObserver.onCompleted();


}

}

}






