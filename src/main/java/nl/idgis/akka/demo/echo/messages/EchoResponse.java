package nl.idgis.akka.demo.echo.messages;

import java.io.Serializable;
import java.util.Objects;

public class EchoResponse implements Serializable {

	private static final long serialVersionUID = 359902123915080518L;
	
	private final Object message;
	
	public EchoResponse(Object message) {
		this.message = Objects.requireNonNull(message, "message must not be null");
	}
	
	public Object getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "EchoResponse [message=" + message + "]";
	}
}
