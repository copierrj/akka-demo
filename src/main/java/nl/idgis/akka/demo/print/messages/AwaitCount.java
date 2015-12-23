package nl.idgis.akka.demo.print.messages;

import java.io.Serializable;

/**
 * Request the {@link nl.idgis.akka.demo.print.PrintService PrintService}
 * to be notified after a certain number of lines have been printed.
 * 
 * @author Reijer Copier <reijer.copier@idgis.nl>
 *
 */
public class AwaitCount implements Serializable {
	
	private static final long serialVersionUID = -452306035568491247L;
	
	private final int count;
	
	/**
	 * Create a new {@link AwaitCount} request for a specific number of lines. 
	 * 
	 * @param count the number of lines the 
	 * {@link nl.idgis.akka.demo.print.PrintService PrintService}
	 * should have printed before we are notified.
	 */
	public AwaitCount(int count) {
		this.count = count;
	}
	
	/**
	 * 
	 * @return the number of lines
	 */
	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "AwaitCount [count=" + count + "]";
	}
}
