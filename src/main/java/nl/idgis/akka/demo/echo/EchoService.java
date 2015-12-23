package nl.idgis.akka.demo.echo;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import akka.actor.Props;
import akka.actor.UntypedActor;

import nl.idgis.akka.demo.echo.messages.EchoResponse;

import scala.concurrent.duration.FiniteDuration;

public class EchoService extends UntypedActor {
	
	private Random random;

	@Override
	public void onReceive(Object msg) throws Exception {
		int delay = random.nextInt(1000);
		
		getContext().system().scheduler().scheduleOnce(
			FiniteDuration.apply(delay, TimeUnit.MILLISECONDS),
			getSender(),
			new EchoResponse(msg),
			getContext().dispatcher(),
			getSelf());
	}
	
	@Override
	public void preStart() {
		random = new Random();
	}

	public static Props props() {
		return Props.create(EchoService.class);
	}

}
