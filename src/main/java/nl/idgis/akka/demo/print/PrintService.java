package nl.idgis.akka.demo.print;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import nl.idgis.akka.demo.print.messages.AwaitCount;
import nl.idgis.akka.demo.print.messages.CountReached;

/**
 * This actor prints received messages and is capable of notifying
 * other actors when a certain number of lines have been printed.  
 * 
 * @author Reijer Copier <reijer.copier@idgis.nl>
 *
 */
public class PrintService extends UntypedActor {
	
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	// number of lines printed
	private int printCount;
	
	// actors currently waiting to be notified for a number of lines printed
	private Map<Integer, Set<ActorRef>> waiting; 
	
	public static Props props() {
		return Props.create(PrintService.class);
	}
	
	@Override
	public void preStart() {
		log.debug("starting...");
		
		printCount = 0;
		waiting = new HashMap<>();
	}
	
	@Override
	public void postStop() {
		log.debug("stopped");
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof AwaitCount) {
			handleAwaitCount((AwaitCount)msg);
		} else {
			handleElse(msg);
		}
	}

	private void handleElse(Object msg) {
		log.info("{}: {}", printCount++, msg);
		
		// notify actors waiting for a specific number of lines printed
		Set<ActorRef> waitingForCount = waiting.get(printCount);
		if(waitingForCount == null) {
			log.debug("no actor is waiting for {} printed lines", printCount);
		} else {
			// remove waiting actors and send notifications
			for(ActorRef actorRef : waiting.remove(printCount)) {
				log.debug("telling {} that we printed {} lines", actorRef, printCount);
				actorRef.tell(new CountReached(printCount), getSelf());
			}	
		}
	}

	private void handleAwaitCount(AwaitCount msg) {
		ActorRef sender = getSender();
		int awaitCount = msg.getCount();
		
		log.debug("actor {} wants to get notified when {} lines are printed", 
			sender, awaitCount);
		
		if(awaitCount <= printCount) {
			log.debug("we've already printed {} lines, immediately notify actor", awaitCount);	
			sender.tell(new CountReached(printCount), getSelf());
		} else {
			log.debug("storing reference to {} for future notification", sender);
			
			Set<ActorRef> waitingForCount;
			if(waiting.containsKey(awaitCount)) {
				waitingForCount = waiting.get(awaitCount);
			} else {
				waitingForCount = new HashSet<>();
				waiting.put(awaitCount, waitingForCount);
			}
			
			waitingForCount.add(sender);
		}
	}

}
