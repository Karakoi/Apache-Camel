package components.dynamicreports;

import components.erlang.ErlangComponent;
import lombok.Getter;
import lombok.Setter;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;

@Getter
@Setter
public class DynamicReportsEndpoint extends DefaultEndpoint {


    public DynamicReportsEndpoint() {
        super();
    }

    public DynamicReportsEndpoint(String uri, ErlangComponent component) {
        super(uri, component);
    }

    public DynamicReportsEndpoint(String endpointUri) {
        super(endpointUri);
    }

    @Override
    protected String createEndpointUri() {
        return super.createEndpointUri();
    }

    @Override
    public Producer createProducer() throws Exception {
        return new DynamicReportsProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return new DynamicReportsConsumer(this, processor);
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
