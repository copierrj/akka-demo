package nl.idgis.akka.demo.measure;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import nl.idgis.akka.demo.measure.messages.MeasureDelay;

/**
 * This actor measures the time it takes for another
 * actor to respond to a specific message.
 * 
 * @author Reijer Copier <reijer.copier@idgis.nl>
 *
 */
public class MeasureService extends UntypedActor {
	
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	// used to create unique actor names
	private int handleCount;
	
	public static Props props() {
		return Props.create(MeasureService.class);
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof MeasureDelay) {
			handleMeasureDelayRequest((MeasureDelay)msg);
		} else {
			unhandled(msg);
		}
	}
	
	private void handleMeasureDelayRequest(MeasureDelay msg) {
		log.debug("measure request received: {}", msg);
		
		ActorRef measureHandler = getContext().actorOf(
			MeasureHandler.props(getSender(), System.currentTimeMillis()),
			"handler" + handleCount++);
		
		log.debug("handler created");
		
		ActorRef target = msg.getTarget();
		Object message = msg.getMessage();
		
		target.tell(message, measureHandler);
		
		log.debug("message {} sent to {}", message, target);
	}
	
	@Override
	public void preStart() {
		log.debug("starting...");
		
		handleCount = 0;
	}
	
	@Override
	public void postStop() {
		log.debug("stopped");
	}

}
