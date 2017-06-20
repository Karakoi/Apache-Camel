package components.dynamicreports;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

public class DynamicReportsConsumer extends DefaultConsumer {

    private final DynamicReportsEndpoint endpoint;
    private DynamicReports dynamicReports;

    public DynamicReportsConsumer(DynamicReportsEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
        dynamicReports = new DynamicReports();
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        Exchange exchange = endpoint.createExchange();
        getProcessor().process(exchange);
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
    }

}
