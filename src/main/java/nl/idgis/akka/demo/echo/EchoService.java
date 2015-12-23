package nl.idgis.akka.demo.echo;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import nl.idgis.akka.demo.echo.messages.EchoResponse;

import scala.concurrent.duration.FiniteDuration;

/**
 * This actor responds (after a random delay) to every message 
 * it receives with a {@link EchoResponse} containing the
 * received message.
 * 
 * @author Reijer Copier <reijer.copier@idgis.nl>
 *
 */
public class EchoService extends UntypedActor {
	
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	private Random random;
	
	public static Props props() {
		return Props.create(EchoService.class);
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		log.debug("message received: {}", msg);
		
		int delay = random.nextInt(1000);
		
		log.debug("schedule response with delay: {} ms", delay);
		
		getContext().system().scheduler().scheduleOnce(
			FiniteDuration.apply(delay, TimeUnit.MILLISECONDS),
			getSender(),
			new EchoResponse(msg),
			getContext().dispatcher(),
			getSelf());
	}
	
	@Override
	public void preStart() {
		log.debug("starting...");
		
		random = new Random();
	}
	
	@Override
	public void postStop() {
		log.debug("stopped");
	}	

}
