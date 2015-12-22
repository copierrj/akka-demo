package nl.idgis.akka.demo;

import java.io.Serializable;
import java.util.Objects;

import akka.actor.ActorRef;

public class MeasureDelayRequest implements Serializable {
	
	private static final long serialVersionUID = 8354278689392391799L;

	private final ActorRef echoService;
	
	private final EchoRequest echoRequest;
	
	public MeasureDelayRequest(ActorRef echoService, EchoRequest echoRequest) {
		this.echoService = Objects.requireNonNull(echoService, "echoService must not be null");
		this.echoRequest = Objects.requireNonNull(echoRequest, "echoRequest must not be null");
	}

	public ActorRef getEchoService() {
		return echoService;
	}

	public EchoRequest getEchoRequest() {
		return echoRequest;
	}

	@Override
	public String toString() {
		return "MeasureDelayRequest [echoService=" + echoService + ", echoRequest=" + echoRequest + "]";
	}	
	
}
