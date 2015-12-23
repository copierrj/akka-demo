package nl.idgis.akka.demo;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;

public class Main extends UntypedActor {
	
	final int REQUEST_COUNT = 10;
	
	ActorRef echoService, delayMeasureService, printService;
	
	@Override
	public void preStart() throws Exception {
		echoService = getContext().actorOf(EchoService.props());
		delayMeasureService = getContext().actorOf(DelayMeasureService.props());
		printService = getContext().actorOf(PrintService.props());
		
		for(int i = 0; i < REQUEST_COUNT; i++) {
			delayMeasureService.tell(
				new MeasureDelayRequest(
					echoService,
					new EchoRequest("message" + i)), 
				printService);
		}
		
		printService.tell(new AwaitCount(REQUEST_COUNT), getSelf());
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		if(msg instanceof CountReached) {
			handleCountReached((CountReached)msg);
		} else {
			unhandled(msg);
		}
	}

	private void handleCountReached(CountReached msg) {
		getContext().stop(getSelf());
	}
}