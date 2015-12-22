package nl.idgis.akka.demo;

import java.io.Serializable;
import java.util.Objects;

public class EchoResponse implements Serializable {

	private static final long serialVersionUID = 359902123915080518L;
	
	private final String message;
	
	public EchoResponse(String message) {
		this.message = Objects.requireNonNull(message, "message must not be null");
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "EchoResponse [message=" + message + "]";
	}
}
