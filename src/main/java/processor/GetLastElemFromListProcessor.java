package processor;

import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class GetLastElemFromListProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		ArrayList<Object> list = exchange.getIn().getBody(ArrayList.class);
		exchange.getIn().setBody(list.get(list.size() - 1));
	}

}
