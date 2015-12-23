package nl.idgis.akka.demo.measure;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActorWithStash;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

import nl.idgis.akka.demo.measure.messages.MeasureAverageDelay;
import nl.idgis.akka.demo.measure.messages.MeasuredAverageDelay;

public class MeasureAverageService extends UntypedActorWithStash {
	
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	public static Props props() {
		return Props.create(MeasureAverageService.class);
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
		if(msg instanceof MeasureAverageDelay) {
			handleMeasureDelayAverage((MeasureAverageDelay)msg);
		} else {
			unhandled(msg);
		}
	}
	
	private Procedure<Object> measuring(ActorRef sender, ActorRef target, int count, long startTime){
		return new Procedure<Object>() {
			
			int currentCount = count;
			
			long totalDelay = 0;

			@Override
			public void apply(Object msg) throws Exception {
				if(getSender().equals(target)) {
					long delay = System.currentTimeMillis() - startTime;
					log.debug("message received: {} with delay: {} ms", msg, delay);
					
					totalDelay += delay;
					
					if(--currentCount == 0) {
						long avg = totalDelay / count;
						
						log.debug("all messages received, average delay: {}", avg);
						sender.tell(new MeasuredAverageDelay(avg), getSelf());
						
						// revert to default behavior
						getContext().become(receive());
						
						// unstash all earlier stashed messages
						unstashAll();
					}
				} else {
					log.debug("message received that we currently can't process: {}", msg);
					
					// stash request so we can process it later
					stash();
				}
			}
			
		};
	}

	private void handleMeasureDelayAverage(MeasureAverageDelay msg) {
		log.debug("measure request received: {}", msg);
		
		ActorRef target = msg.getTarget();
		Object message = msg.getMessage();
		int count = msg.getCount();
		
		long currentTime = System.currentTimeMillis();
		
		log.debug("sending measure requests...");
		for(int i = 0; i < count; i++) {
			target.tell(message, getSelf());
		}
		
		log.debug("measuring delays...");
		getContext().become(measuring(getSender(), target, count, currentTime));
	}

}
