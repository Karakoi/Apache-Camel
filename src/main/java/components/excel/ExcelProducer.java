package components.excel;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

public class ExcelProducer extends DefaultProducer {

	private ExcelEndpoint endpoint;
	private Excel excel;

	public ExcelProducer(ExcelEndpoint endpoint) {
		super(endpoint);
		this.endpoint = endpoint;
	}

	@Override
	protected void doStart() throws Exception {
		super.doStart();
		excel = new Excel();

	}

	@Override
	public void process(Exchange exchange) throws Exception {
		System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS \n" + exchange.getIn().getBody(String.class));
		String str = exchange.getIn().getBody(String.class);
		excel.writeData(endpoint.getFileNameTo(), str);
	}

}
