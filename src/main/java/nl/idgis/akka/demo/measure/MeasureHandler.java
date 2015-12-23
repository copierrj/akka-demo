package nl.idgis.akka.demo.measure;

import java.util.Objects;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

import nl.idgis.akka.demo.measure.messages.MeasuredDelay;

public class MeasureHandler extends UntypedActor {
	
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
	public void onReceive(Object msg) throws Exception {
		sender.tell(
			new MeasuredDelay(
				msg, 
				System.currentTimeMillis() - startTime), 
			getSelf());
		
		getContext().stop(getSelf());
	}
}
