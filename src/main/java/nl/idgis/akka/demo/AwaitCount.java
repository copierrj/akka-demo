package nl.idgis.akka.demo;

import java.io.Serializable;

public class AwaitCount implements Serializable {
	
	private static final long serialVersionUID = -452306035568491247L;
	
	private final int count;
	
	public AwaitCount(int count) {
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}

	@Override
	public String toString() {
		return "AwaitCount [count=" + count + "]";
	}
}
