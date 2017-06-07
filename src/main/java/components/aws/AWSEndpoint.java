package components.aws;

import lombok.Getter;
import lombok.Setter;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriParam;

@Getter
@Setter
public class AWSEndpoint extends DefaultEndpoint {

    @UriParam
    private String bucketName;

    @UriParam
    private String keyName;

    @UriParam
    private String operation;

    @UriParam
    private String accessKey;

    @UriParam
    private String secretKey;

    public AWSEndpoint() {
        super();
    }

    public AWSEndpoint(String uri, AWSComponent component) {
        super(uri, component);
    }

    public AWSEndpoint(String endpointUri) {
        super(endpointUri);
    }

    @Override
    protected String createEndpointUri() {
        return super.createEndpointUri();
    }

    @Override
    public Producer createProducer() throws Exception {
        return new AWSProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return new AWSConsumer(this, processor);
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
