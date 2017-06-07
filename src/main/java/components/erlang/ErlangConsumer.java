package components.erlang;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

public class ErlangConsumer extends DefaultConsumer {

    private final ErlangEndpoint endpoint;
    private Erlang erlang;

    public ErlangConsumer(ErlangEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        erlang = new Erlang(endpoint.getNode(), endpoint.getCookie());
        Exchange exchange = endpoint.createExchange();
        exchange.getIn().setBody(erlang.writeAndReadData(endpoint.getOperation(), endpoint.getDataForCalculation()));
        getProcessor().process(exchange);
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
    }

}
