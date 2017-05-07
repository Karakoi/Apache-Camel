package component.excel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ExcelProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		System.out.println(
				"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA \n" + exchange.getIn().getBody(String.class));
	}
}
