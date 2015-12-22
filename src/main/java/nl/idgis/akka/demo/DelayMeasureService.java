package nl.idgis.akka.demo;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class DelayMeasureService extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof MeasureDelayRequest) {
			handleMeasureDelayRequest((MeasureDelayRequest)msg);
		} else {
			unhandled(msg);
		}
	}
	
	public static Props props() {
		return Props.create(DelayMeasureService.class);
	}

	private void handleMeasureDelayRequest(MeasureDelayRequest msg) {
		ActorRef delayMeasureHandler = getContext().actorOf(
			DelayMeasureHandler.props(getSender(), System.currentTimeMillis()));
		
		msg.getEchoService().tell(msg.getEchoRequest(), delayMeasureHandler);
	}

}
