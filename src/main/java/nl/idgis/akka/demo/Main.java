package nl.idgis.akka.demo;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class Main {

	public static void main(String[] args) throws Exception {
		ActorSystem actorSystem = ActorSystem.create();
		
		ActorRef echoService = actorSystem.actorOf(EchoService.props());
		ActorRef delayMeasureService = actorSystem.actorOf(DelayMeasureService.props());
		
		for(int i = 0; i < 10; i++) {
			Future<Object> future = Patterns.ask(
				delayMeasureService,
				new MeasureDelayRequest(
					echoService,
					new EchoRequest("message" + i)), 
				Timeout.apply(5, TimeUnit.SECONDS));
			
			System.out.println("" + i + ": " + Await.result(future, Duration.Inf()));
		}
		
		Await.ready(actorSystem.terminate(), Duration.Inf());
	}
}