package nl.idgis.akka.demo.print.messages;

import java.io.Serializable;

/**
 * Informs that a certain number of lines has been printed.
 * 
 * @author Reijer Copier <reijer.copier@idgis.nl>
 *
 */
public class CountReached implements Serializable {
	
	private static final long serialVersionUID = -3757189486883513357L;
	
	private final int count;
	
	/**
	 * Create a new {@link CountReached} to inform that a specific number of 
	 * lines have been printed.
	 * 
	 * @param count the number of lines printed.
	 */
	public CountReached(int count) {
		this.count = count;
	}

	/**
	 * 
	 * @return the number of lines printed.
	 */
	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "CountReached [count=" + count + "]";
	}
	
}
