package nl.idgis.akka.demo.echo;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import akka.actor.Props;
import akka.actor.UntypedActor;
import nl.idgis.akka.demo.echo.messages.EchoRequest;
import nl.idgis.akka.demo.echo.messages.EchoResponse;
import scala.concurrent.duration.FiniteDuration;

public class EchoService extends UntypedActor {
	
	private Random random;

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof EchoRequest) {
			handleEchoRequest((EchoRequest)msg);
		} else {		
			unhandled(msg);
		}
	}
	
	@Override
	public void preStart() {
		random = new Random();
	}
	
	private void handleEchoRequest(EchoRequest msg) {
		int delay = random.nextInt(1000);
		
		getContext().system().scheduler().scheduleOnce(
			FiniteDuration.apply(delay, TimeUnit.MILLISECONDS),
			getSender(),
			new EchoResponse(msg.getMessage()),
			getContext().dispatcher(),
			getSelf());
	}

	public static Props props() {
		return Props.create(EchoService.class);
	}

}
