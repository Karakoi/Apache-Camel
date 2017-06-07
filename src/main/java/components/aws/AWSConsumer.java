package components.aws;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultConsumer;

public class AWSConsumer extends DefaultConsumer {

    private final AWSEndpoint endpoint;
    private AWS aws;

    public AWSConsumer(AWSEndpoint endpoint, Processor processor) {
        super(endpoint, processor);
        this.endpoint = endpoint;
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        aws = new AWS(endpoint.getBucketName(), endpoint.getKeyName(), endpoint.getAccessKey(), endpoint.getSecretKey());
        Exchange exchange = endpoint.createExchange();
        exchange.getIn().setBody(aws.executeRead(endpoint.getOperation()));
        getProcessor().process(exchange);
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
    }

}
