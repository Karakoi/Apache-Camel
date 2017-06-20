package components.dynamicreports;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

@Slf4j
public class DynamicReportsProducer extends DefaultProducer {

    private DynamicReportsEndpoint endpoint;
    private DynamicReports dynamicReports;

    public DynamicReportsProducer(DynamicReportsEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
        dynamicReports = new DynamicReports();
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        dynamicReports.build(dynamicReports.createDataSource(exchange));
    }

}
