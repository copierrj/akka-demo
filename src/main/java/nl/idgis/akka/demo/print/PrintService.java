package nl.idgis.akka.demo.print;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import nl.idgis.akka.demo.print.messages.AwaitCount;
import nl.idgis.akka.demo.print.messages.CountReached;

public class PrintService extends UntypedActor {
	
	private int count;
	
	private Map<Integer, Set<ActorRef>> waiting; 
	
	public static Props props() {
		return Props.create(PrintService.class);
	}
	
	@Override
	public void preStart() {
		count = 0;
		waiting = new HashMap<>();
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof AwaitCount) {
			handleAwaitCount((AwaitCount)msg);
		} else {		
			System.out.println("" + count++ + ": " + msg);
		}
		
		Set<ActorRef> waitingForCount = waiting.get(count);
		if(waitingForCount != null) {
			waiting.remove(count);
			waitingForCount.forEach(actorRef -> 
				actorRef.tell(new CountReached(), getSelf()));
		}
	}

	private void handleAwaitCount(AwaitCount msg) {
		int awaitCount = msg.getCount();
		if(awaitCount <= count) {
			getSender().tell(new CountReached(), getSelf());
		} else {
			Set<ActorRef> waitingForCount;
			if(waiting.containsKey(awaitCount)) {
				waitingForCount = waiting.get(awaitCount);
			} else {
				waitingForCount = new HashSet<>();
				waiting.put(awaitCount, waitingForCount);
			}
			
			waitingForCount.add(getSender());
		}
	}

}
