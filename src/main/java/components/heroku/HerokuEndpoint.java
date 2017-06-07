package components.heroku;

import lombok.Getter;
import lombok.Setter;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriParam;

@Getter
@Setter
public class HerokuEndpoint extends DefaultEndpoint {

    @UriParam
    private String url;

    @UriParam
    private String user;

    @UriParam
    private String password;

    @UriParam
    private String itemName;

    @UriParam
    private String newItemName;

    public HerokuEndpoint() {
        super();
    }

    public HerokuEndpoint(String uri, HerokuComponent component) {
        super(uri, component);
    }

    public HerokuEndpoint(String endpointUri) {
        super(endpointUri);
    }

    @Override
    protected String createEndpointUri() {
        return super.createEndpointUri();
    }

    @Override
    public Producer createProducer() throws Exception {
        return new HerokuProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return new HerokuConsumer(this, processor);
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
