package nl.idgis.akka.demo.measure.messages;

import java.io.Serializable;

import nl.idgis.akka.demo.echo.messages.EchoResponse;

public class MeasureDelayResponse implements Serializable {
	
	private static final long serialVersionUID = -5754090827959691276L;

	private final EchoResponse response;
	
	private final long time;
	
	public MeasureDelayResponse(EchoResponse response, long time) {
		this.response = response;
		this.time = time;
	}

	public EchoResponse getResponse() {
		return response;
	}

	public long getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "MeasureDelayResponse [response=" + response + ", time=" + time + "]";
	}
}
