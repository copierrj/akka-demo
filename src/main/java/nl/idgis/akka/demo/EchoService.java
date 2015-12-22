package nl.idgis.akka.demo;

import akka.actor.Props;
import akka.actor.UntypedActor;

public class EchoService extends UntypedActor {

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof EchoRequest) {
			handleEchoRequest((EchoRequest)msg);
		} else {		
			unhandled(msg);
		}
	}
	
	private void handleEchoRequest(EchoRequest msg) {
		getSender().tell(new EchoResponse(msg.getMessage()), getSelf());
	}

	public static Props props() {
		return Props.create(EchoService.class);
	}

}
