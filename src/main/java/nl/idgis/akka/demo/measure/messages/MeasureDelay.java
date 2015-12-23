package nl.idgis.akka.demo.measure.messages;

import java.io.Serializable;
import java.util.Objects;

import akka.actor.ActorRef;

/**
 * Requests the {@link nl.idgis.akka.demo.measure.MeasureService MeasureService}
 * to measure the time it takes for the given target to respond to the
 * given message. 
 * 
 * @author Reijer Copier <reijer.copier@idgis.nl>
 *
 */
public class MeasureDelay implements Serializable {
	
	private static final long serialVersionUID = -4211555864184138375L;

	private final ActorRef target;
	
	private final Object message;
	
	/**
	 * Create a new {@link MeasureDelay} request for a message to be send
	 * to a specific target actor.
	 * 
	 * @param target the actor to send the message to.
	 * @param message the message to send.
	 */
	public MeasureDelay(ActorRef target, Object message) {
		this.target = Objects.requireNonNull(target, "target must not be null");
		this.message = Objects.requireNonNull(message, "message must not be null");
	}

	/**
	 * 
	 * @return the target to send a message to.
	 */
	public ActorRef getTarget() {
		return target;
	}

	/**
	 * 
	 * @return the message to send.
	 */
	public Object getMessage() {
		return message;
	}
}
