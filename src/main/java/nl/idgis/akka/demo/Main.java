package nl.idgis.akka.demo;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

public class Main {

	public static void main(String[] args) throws Exception {
		ActorSystem actorSystem = ActorSystem.create();
		
		ActorRef echoService = actorSystem.actorOf(EchoService.props());
		ActorRef delayMeasureService = actorSystem.actorOf(DelayMeasureService.props());
		ActorRef printService = actorSystem.actorOf(PrintService.props());
		
		final int requestCount = 10;
		
		for(int i = 0; i < requestCount; i++) {
			delayMeasureService.tell(
				new MeasureDelayRequest(
					echoService,
					new EchoRequest("message" + i)), 
				printService);
		}
		
		Await.ready(
			Patterns.ask(
				printService, 
				new AwaitCount(requestCount), 
				Timeout.apply(5, TimeUnit.SECONDS)),
			Duration.Inf());
		
		Await.ready(actorSystem.terminate(), Duration.Inf());
	}
}