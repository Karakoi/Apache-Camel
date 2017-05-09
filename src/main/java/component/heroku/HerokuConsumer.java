package component.heroku;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

public class HerokuConsumer extends DefaultConsumer {

    private final HerokuEndpoint endpoint;
    private HerokuDBConnector herokuDBConnector;

    public HerokuConsumer(HerokuEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
        herokuDBConnector = new HerokuDBConnector(endpoint.getUrl(), endpoint.getUser(), endpoint.getPassword());
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        Exchange exchange = endpoint.createExchange();
        exchange.getIn().setBody(herokuDBConnector.readItemByName(endpoint.getItemName()));
        getProcessor().process(exchange);
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
    }

}
