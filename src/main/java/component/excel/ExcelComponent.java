package component.excel;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

public class ExcelComponent extends DefaultComponent {

	private Endpoint endpoint;

	public ExcelComponent(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

	@Override
	protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
		Endpoint endpoint = new ExcelEndpoint();
		setProperties(endpoint, parameters);
		return endpoint;
	}

	public Endpoint getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(Endpoint endpoint) {
		this.endpoint = endpoint;
	}

}
