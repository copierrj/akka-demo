package nl.idgis.akka.demo;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import nl.idgis.akka.demo.echo.EchoService;
import nl.idgis.akka.demo.echo.messages.EchoRequest;
import nl.idgis.akka.demo.measure.MeasureService;
import nl.idgis.akka.demo.measure.messages.MeasureDelay;
import nl.idgis.akka.demo.print.PrintService;
import nl.idgis.akka.demo.print.messages.AwaitCount;
import nl.idgis.akka.demo.print.messages.CountReached;

public class Main extends UntypedActor {
	
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	final int REQUEST_COUNT = 10;
	
	ActorRef echoService, delayMeasureService, printService;
	
	@Override
	public void preStart() throws Exception {
		log.info("starting...");
		
		echoService = getContext().actorOf(EchoService.props());
		delayMeasureService = getContext().actorOf(MeasureService.props());
		printService = getContext().actorOf(PrintService.props());
		
		for(int i = 0; i < REQUEST_COUNT; i++) {
			delayMeasureService.tell(
				new MeasureDelay(
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
		log.info("stopping...");
		getContext().stop(getSelf());
	}
}