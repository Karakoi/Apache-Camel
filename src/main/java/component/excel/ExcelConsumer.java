package component.excel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

public class ExcelConsumer extends DefaultConsumer {

	private final ExcelEndpoint endpoint;
	private Excel excel;

	public ExcelConsumer(ExcelEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
		this.endpoint = endpoint;
	}

	@Override
	protected void doStart() throws Exception {
		super.doStart();
		excel = new Excel();
		Exchange exchange = endpoint.createExchange();
		exchange.getIn().setBody(excel.readSingleData(endpoint.getFileNameFrom(), endpoint.getSheetNo(),
				endpoint.getRowNo(), endpoint.getCellNo()));
		getProcessor().process(exchange);
	}

	@Override
	protected void doStop() throws Exception {
		super.doStop();
	}

}
