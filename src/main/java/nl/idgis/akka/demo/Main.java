package nl.idgis.akka.demo;

import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.OnComplete;
import akka.pattern.Patterns;
import akka.util.Timeout;

public class Main {

	public static void main(String[] args) throws Exception {
		ActorSystem actorSystem = ActorSystem.create();
		
		ActorRef service = actorSystem.actorOf(EchoService.props());
		for(int i = 0; ; i++) {
			Patterns.ask(
				service, 
				new EchoRequest("message" + i), 
				Timeout.apply(1, TimeUnit.SECONDS)).onComplete(new OnComplete<Object>() {

					@Override
					public void onComplete(Throwable t, Object msg) throws Throwable {
						if(t == null) {
							System.out.println(msg);
						} else {
							t.printStackTrace();
						}
					}
					
				}, actorSystem.dispatcher());
			
			Thread.sleep(1000);
		}
	}
}