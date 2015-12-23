package nl.idgis.akka.demo.message;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

/**
 * A meaningless message to bounce 
 * of the {@link nl.idgis.akka.demo.echo.EchoService EchoService}.
 * 
 * @author Reijer Copier <reijer.copier@idgis.nl>
 *
 */
public class DemoMessage implements Serializable {
	
	private static final long serialVersionUID = 7027277436830580283L;
	
	private final String text;
	
	/**
	 * Create a {@link DemoMessage} without text payload.
	 */
	public DemoMessage() {
		this.text = null;
	}
	
	/**
	 * Create a {@link DemoMessage} text payload.
	 * 
	 * @param text the payload.
	 */
	public DemoMessage(String text) {
		this.text = Objects.requireNonNull(text, "text should not be null");
	}
	
	/**
	 * 
	 * @return optional text payload
	 */
	public Optional<String> getText() {
		return Optional.ofNullable(text);
	}

	@Override
	public String toString() {
		return "DemoMessage [text=" + text + "]";
	}

}
