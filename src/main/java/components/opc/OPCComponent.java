package components.opc;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

import java.util.Map;

public class OPCComponent extends DefaultComponent {

    private Endpoint endpoint;

    public OPCComponent(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {

        // Endpoint endpoint = new OPCReadEndpoint(uri, this);
        // endpoint = new OPCReadEndpoint(uri, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }

    public Endpoint getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

}
