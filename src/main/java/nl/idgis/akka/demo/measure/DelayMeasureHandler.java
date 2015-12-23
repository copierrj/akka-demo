package nl.idgis.akka.demo.measure;

import java.util.Objects;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import nl.idgis.akka.demo.echo.messages.EchoResponse;
import nl.idgis.akka.demo.measure.messages.MeasureDelayResponse;

public class DelayMeasureHandler extends UntypedActor {
	
	private final ActorRef sender;
	
	private final long startTime;
	
	public DelayMeasureHandler(ActorRef sender, long startTime) {
		this.sender = sender;
		this.startTime = startTime;
	}
	
	public static Props props(ActorRef sender, long startTime) {
		return Props.create(
			DelayMeasureHandler.class, 
			Objects.requireNonNull(sender, "sender must not be null"),
			Objects.requireNonNull(startTime, "startTime must not be null"));
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof EchoResponse) {
			handleEchoResponse((EchoResponse)msg);
		} else {
			unhandled(msg);
		}
	}

	private void handleEchoResponse(EchoResponse msg) {
		sender.tell(
			new MeasureDelayResponse(
				msg, 
				System.currentTimeMillis() - startTime), 
			getSelf());
		
		getContext().stop(getSelf());
	}

}
