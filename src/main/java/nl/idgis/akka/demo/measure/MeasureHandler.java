package nl.idgis.akka.demo.measure;

import java.util.Objects;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import nl.idgis.akka.demo.measure.messages.MeasuredDelay;

/**
 * This actor waits for the response of another actor whose 
 * response time we're currently measuring.
 * 
 * @author Reijer Copier <reijer.copier@idgis.nl>
 *
 */
public class MeasureHandler extends UntypedActor {
	
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	private final ActorRef sender;
	
	private final long startTime;
	
	public MeasureHandler(ActorRef sender, long startTime) {
		this.sender = sender;
		this.startTime = startTime;
	}
	
	public static Props props(ActorRef sender, long startTime) {
		return Props.create(
			MeasureHandler.class, 
			Objects.requireNonNull(sender, "sender must not be null"),
			Objects.requireNonNull(startTime, "startTime must not be null"));
	}
	
	@Override
	public void preStart() {
		log.debug("starting...");
	}
	
	@Override
	public void postStop() {
		log.debug("stopped");
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		long delay = System.currentTimeMillis() - startTime;
		log.debug("message received: {} with delay: {} ms", msg, delay);
		
		sender.tell(new MeasuredDelay(msg, delay), getSelf());
		
		log.debug("stopping...");
		getContext().stop(getSelf());
	}
}
