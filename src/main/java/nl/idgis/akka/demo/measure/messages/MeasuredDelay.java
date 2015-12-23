package nl.idgis.akka.demo.measure.messages;

import java.io.Serializable;

/**
 * Response to a {@link MeasureDelay} request.
 * 
 * @author Reijer Copier <reijer.copier@idgis.nl>
 *
 */
public class MeasuredDelay implements Serializable {	
	
	private static final long serialVersionUID = -7479779364884669206L;

	private final Object response;
	
	private final long delay;
	
	/**
	 * Create a new {@link MeasuredDelay} message with received response
	 * and calculated delay.
	 * 
	 * @param response the received response.
	 * @param delay the calculated delay.
	 */
	public MeasuredDelay(Object response, long delay) {
		this.response = response;
		this.delay = delay;
	}

	/**
	 * 
	 * @return the response received.
	 */
	public Object getResponse() {
		return response;
	}

	/** 
	 * 
	 * @return the time it took to receive the response.
	 */
	public long getDelay() {
		return delay;
	}

	@Override
	public String toString() {
		return "MeasuredDelay [response=" + response + ", delay=" + delay + "]";
	}
}
