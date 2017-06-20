package processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;

public class GetLastElemFromListProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        ArrayList list = exchange.getIn().getBody(ArrayList.class);
        exchange.getIn().setBody(list.get(list.size() - 1));
    }

}
