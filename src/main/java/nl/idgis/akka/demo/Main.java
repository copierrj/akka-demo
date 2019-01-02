package nl.idgis.akka.demo;

import java.util.stream.Stream;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Procedure;

import nl.idgis.akka.demo.echo.EchoService;
import nl.idgis.akka.demo.measure.MeasureAverageService;
import nl.idgis.akka.demo.measure.MeasureService;
import nl.idgis.akka.demo.measure.messages.MeasureAverageDelay;
import nl.idgis.akka.demo.measure.messages.MeasureDelay;
import nl.idgis.akka.demo.message.DemoMessage;
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
	
	private final int REQUEST_COUNT = 10, AVERAGE_REQUEST_COUNT = 2;
	
	private ActorRef echoService, measureService, printService;
	
	@Override
	public void preStart() throws Exception {
		log.info("starting...");

		log.debug("starting child actors...");
		
		echoService = getContext().actorOf(EchoService.props(), "echo");
		measureService = getContext().actorOf(MeasureService.props(), "measure");
		printService = getContext().actorOf(PrintService.props(), "print");
		
		log.debug("child actors started");
		
		log.debug("sending measure requests...");
		
		for(int i = 0; i < REQUEST_COUNT; i++) {
			measureService.tell(
				new MeasureDelay(
					echoService,
					new DemoMessage("message" + i)), 
				printService);
		}
		
		log.debug("all requests sent");
		
		log.debug("waiting for all measure results being printed");
		printService.tell(new AwaitCount(REQUEST_COUNT), getSelf());
	}
	
	@Override
	public void postStop() {
		log.debug("stopped");
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
		log.debug("print service reported {} printed lines", msg.getCount());
		log.info("all measurements taken and printed, next...");
		
		log.debug("killing measure service...");
		measureService.tell(PoisonPill.getInstance(), getSelf());
		
		log.debug("starting measure average service...");
		ActorRef measureAverageService = getContext().actorOf(
			MeasureAverageService.props(), "measure-average");
		
		log.debug("measure average service started");
		
		log.debug("sending average measure requests...");
		
		for(int i = 0; i < AVERAGE_REQUEST_COUNT; i++) {
			measureAverageService.tell(
				new MeasureAverageDelay(
					echoService,
					new DemoMessage("message" + i),
					REQUEST_COUNT), 
				printService);
		}
		
		printService.tell(new AwaitCount(REQUEST_COUNT + AVERAGE_REQUEST_COUNT), getSelf());
		
		getContext().become(waitingForAveragesPrinted());
	}
	
	private Procedure<Object> waitingForAveragesPrinted() {
		return new Procedure<Object>() {

			@Override
			public void apply(Object msg) throws Exception {
				if(msg instanceof CountReached) {
					handleCountReached((CountReached)msg);
				} else {
					unhandled(msg);
				}
				
			}
			
			private void handleCountReached(CountReached msg) {
				log.debug("print service reported {} printed lines", msg.getCount());
				log.info("all measurements taken and printed, stopping...");
				
				getContext().stop(getSelf());
			}
			
		};
	}
	
	public static void main(String[] args) throws Exception {
		
		for (;;) {
			
			// call akkaMain with the same arguments,
			// prepended with the name of this class.
		
			akka.Main.main(
				Stream
					.concat(
						Stream.of(Main.class.getCanonicalName()),
						Stream.of(args))
					.toArray(String[]::new));
			
			Thread.sleep(10000);
		}
	}
}