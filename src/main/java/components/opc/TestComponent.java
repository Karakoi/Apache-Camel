package components.opc;

import org.apache.camel.*;

public class TestComponent implements Component {

    @Override
    public void setCamelContext(CamelContext camelContext) {

    }

    @Override
    public CamelContext getCamelContext() {
        return null;
    }

    @Override
    public Endpoint createEndpoint(String uri) throws Exception {
        return new OPCEndpoint(uri);
    }

    @Override
    public boolean useRawUri() {
        return false;
    }

    @Override
    public EndpointConfiguration createConfiguration(String uri) throws Exception {
        return null;
    }

    @Override
    public ComponentConfiguration createComponentConfiguration() {
        return null;
    }

}
