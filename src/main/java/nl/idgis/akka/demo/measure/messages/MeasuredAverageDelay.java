package nl.idgis.akka.demo.measure.messages;

import java.io.Serializable;

/**
 * Response to a {@link MeasureAverageDelay} request.
 * 
 * @author Reijer Copier <reijer.copier@idgis.nl>
 *
 */
public class MeasuredAverageDelay implements Serializable {

	private static final long serialVersionUID = -6515237621135995521L;
	
	private final long delay;
	
	/**
	 * Create a new {@link MeasuredAverageDelay} message with the
	 * calculated average delay.
	 * 
	 * @param delay the calculated average delay.
	 */
	public MeasuredAverageDelay(long delay) {
		this.delay = delay;
	}

	/** 
	 * 
	 * @return the average time it took to receive the response.
	 */
	public long getDelay() {
		return delay;
	}

	@Override
	public String toString() {
		return "MeasuredAverageDelay [delay=" + delay + "]";
	}
}
