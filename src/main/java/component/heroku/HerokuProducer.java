package component.heroku;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

@Slf4j
public class HerokuProducer extends DefaultProducer {

    private HerokuEndpoint endpoint;
    private HerokuDBConnector herokuDBConnector;

    public HerokuProducer(HerokuEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        herokuDBConnector = new HerokuDBConnector(endpoint.getUrl(), endpoint.getUser(), endpoint.getPassword());
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        log.debug("Exchange: {}", exchange.getIn().getBody(String.class));
        herokuDBConnector.writeNewItem(endpoint.getNewItemName(), exchange.getIn().getBody(Integer.class));
    }

}
