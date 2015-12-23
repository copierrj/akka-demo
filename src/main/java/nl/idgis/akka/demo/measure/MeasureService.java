package nl.idgis.akka.demo.measure;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import nl.idgis.akka.demo.measure.messages.MeasureDelay;

public class MeasureService extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof MeasureDelay) {
			handleMeasureDelayRequest((MeasureDelay)msg);
		} else {
			unhandled(msg);
		}
	}
	
	public static Props props() {
		return Props.create(MeasureService.class);
	}

	private void handleMeasureDelayRequest(MeasureDelay msg) {
		ActorRef delayMeasureHandler = getContext().actorOf(
			MeasureHandler.props(getSender(), System.currentTimeMillis()));
		
		msg.getTarget().tell(msg.getRequest(), delayMeasureHandler);
	}

}
