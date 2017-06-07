package processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import utils.NumberParser;

public class NumberProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody(new NumberParser().findNumberAsInteger((String) exchange.getIn().getBody()));
    }
}
