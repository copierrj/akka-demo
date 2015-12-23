package nl.idgis.akka.demo.echo.messages;

import java.io.Serializable;
import java.util.Objects;

public class EchoRequest implements Serializable {
	
	private static final long serialVersionUID = 6814123785287251724L;
	
	private final String message;
	
	public EchoRequest(String message) {
		this.message = Objects.requireNonNull(message, "message must not be null");
	}
	
	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "EchoRequest [message=" + message + "]";
	}
}
