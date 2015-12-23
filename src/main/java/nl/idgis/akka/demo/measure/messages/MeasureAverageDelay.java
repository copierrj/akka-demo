package nl.idgis.akka.demo.measure.messages;

import akka.actor.ActorRef;

/**
 * Requests the {@link nl.idgis.akka.demo.measure.MeasureAverageService MeasureAverageService}
 * to measure the average time it takes for the given target to respond to the
 * given message. 
 * 
 * @author Reijer Copier <reijer.copier@idgis.nl>
 *
 */
public class MeasureAverageDelay extends MeasureDelay {
	
	private static final long serialVersionUID = -4211555864184138375L;	
	
	private final int count;
	
	/**
	 * Create a new {@link MeasureAverageDelay} request for a message to be send
	 * to a specific target actor.
	 * 
	 * @param target the actor to send the message to.
	 * @param message the message to send.
	 * @param count the number of message to send.
	 */
	public MeasureAverageDelay(ActorRef target, Object message, int count) {
		super(target, message);
		
		this.count = count;
	}
	
	/**
	 * 
	 * @return the number of message to send.
	 */
	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "MeasureAverageDelay [count=" + count + ", target=" + target + ", message=" + message + "]";
	}
}
