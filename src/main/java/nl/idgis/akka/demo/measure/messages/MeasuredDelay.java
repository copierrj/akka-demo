package nl.idgis.akka.demo.measure.messages;

import java.io.Serializable;

public class MeasuredDelay implements Serializable {
	
	private static final long serialVersionUID = -5754090827959691276L;

	private final Object response;
	
	private final long time;
	
	public MeasuredDelay(Object response, long time) {
		this.response = response;
		this.time = time;
	}

	public Object getResponse() {
		return response;
	}

	public long getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "MeasuredDelay [response=" + response + ", time=" + time + "]";
	}
}
