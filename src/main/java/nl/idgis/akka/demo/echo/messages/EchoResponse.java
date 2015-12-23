package nl.idgis.akka.demo.echo.messages;

import java.io.Serializable;
import java.util.Objects;

/**
 * Message used by {@link nl.idgis.akka.demo.echo.EchoService EchoService} to
 * respond to echo requests. 
 * 
 * @author Reijer Copier <reijer.copier@idgis.nl>
 *
 */
public class EchoResponse implements Serializable {

	private static final long serialVersionUID = 359902123915080518L;
	
	private final Object message;
	
	/**
	 * Create a new {@link EchoResponse} which contains a received message.
	 *  
	 * @param message the received message.
	 */
	public EchoResponse(Object message) {
		this.message = Objects.requireNonNull(message, "message must not be null");
	}
	
	/**
	 *  
	 * @return the original message received by the 
	 * {@link nl.idgis.akka.demo.echo.EchoService EchoService}. 
	 */
	public Object getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "EchoResponse [message=" + message + "]";
	}
}
