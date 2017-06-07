package components.heroku;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import utils.NumberParser;

@Slf4j
public class HerokuProducer extends DefaultProducer {

    private HerokuEndpoint endpoint;
    private HerokuDBConnector herokuDBConnector;

    public HerokuProducer(HerokuEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
        herokuDBConnector = new HerokuDBConnector(endpoint.getUrl(), endpoint.getUser(), endpoint.getPassword());
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        herokuDBConnector.writeNewItem(endpoint.getNewItemName(),
                new NumberParser().findNumberAsInteger(exchange.getIn().getBody(String.class)));
    }

}
