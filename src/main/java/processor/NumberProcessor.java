package processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import utils.NumberParser;

public class NumberProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setBody(new NumberParser().findNumber((String) exchange.getIn().getBody()));
    }
}
