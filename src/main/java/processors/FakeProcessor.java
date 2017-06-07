package processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FakeProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		exchange.getIn().setBody(exchange.getIn().getBody());
	}
}
