package component.erlang;

import lombok.Getter;
import lombok.Setter;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriParam;

@Getter
@Setter
public class ErlangEndpoint extends DefaultEndpoint {

    @UriParam
    private String node;

    @UriParam
    private String cookie;

    @UriParam
    private String operation;

    @UriParam
    private String dataForCalculation;

    public ErlangEndpoint() {
        super();
    }

    public ErlangEndpoint(String uri, ErlangComponent component) {
        super(uri, component);
    }

    public ErlangEndpoint(String endpointUri) {
        super(endpointUri);
    }

    @Override
    protected String createEndpointUri() {
        return super.createEndpointUri();
    }

    @Override
    public Producer createProducer() throws Exception {
        return new ErlangProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return new ErlangConsumer(this, processor);
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
