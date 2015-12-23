package nl.idgis.akka.demo.measure.messages;

import java.io.Serializable;
import java.util.Objects;

import akka.actor.ActorRef;

public class MeasureDelay implements Serializable {

	private static final long serialVersionUID = 6317302736152244607L;

	private final ActorRef target;
	
	private final Object request;
	
	public MeasureDelay(ActorRef echoService, Object request) {
		this.target = Objects.requireNonNull(echoService, "target must not be null");
		this.request = Objects.requireNonNull(request, "request must not be null");
	}

	public ActorRef getTarget() {
		return target;
	}

	public Object getRequest() {
		return request;
	}
}
