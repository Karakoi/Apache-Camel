package components.aws;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

import java.io.File;

@Slf4j
public class AWSProducer extends DefaultProducer {

    private AWSEndpoint endpoint;
    private AWS aws;

    public AWSProducer(AWSEndpoint endpoint) {
        super(endpoint);
        this.endpoint = endpoint;
        aws = new AWS(endpoint.getBucketName(), endpoint.getKeyName(), endpoint.getAccessKey(), endpoint.getSecretKey());
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        File file = exchange.getIn().getBody(File.class);
        aws.executeUpdate(endpoint.getOperation(), file);
    }

}
