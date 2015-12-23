package nl.idgis.akka.demo;

import java.util.stream.Stream;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import nl.idgis.akka.demo.echo.EchoService;
import nl.idgis.akka.demo.measure.MeasureService;
import nl.idgis.akka.demo.measure.messages.MeasureDelay;
import nl.idgis.akka.demo.print.PrintService;
import nl.idgis.akka.demo.print.messages.AwaitCount;
import nl.idgis.akka.demo.print.messages.CountReached;

/**
 * This actor is the so called application supervisor. It is started
 * by akka.Main and its termination automatically results in
 * the termination of the application. 
 * 
 * @author Reijer Copier <reijer.copier@idgis.nl>
 *
 */
public class Main extends UntypedActor {
	
	private final LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	private final int REQUEST_COUNT = 10;
	
	private ActorRef echoService, measureService, printService;
	
	@Override
	public void preStart() throws Exception {
		log.info("starting...");

		log.debug("starting child actors...");
		
		echoService = getContext().actorOf(EchoService.props());
		measureService = getContext().actorOf(MeasureService.props());
		printService = getContext().actorOf(PrintService.props());
		
		log.debug("child actors started");
		
		log.debug("sending measure requests...");
		
		for(int i = 0; i < REQUEST_COUNT; i++) {
			measureService.tell(
				new MeasureDelay(
					echoService,
					"message" + i), 
				printService);
		}
		
		log.debug("all requests sent");
		
		log.debug("waiting for all measure results being printed");
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
		log.info("all measurements taken and printed, stopping...");
		getContext().stop(getSelf());
	}
	
	public static void main(String[] args) {
		// call akkaMain with the same arguments,
		// prepended with the name of this class.
		
		akka.Main.main(
			Stream
				.concat(
					Stream.of(Main.class.getCanonicalName()),
					Stream.of(args))
				.toArray(String[]::new));
	}
}