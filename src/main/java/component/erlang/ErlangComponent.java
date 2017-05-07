package component.erlang;

import lombok.Getter;
import lombok.Setter;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

import java.util.Map;

@Getter
@Setter
public class ErlangComponent extends DefaultComponent {

    private Endpoint endpoint;

    public ErlangComponent(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
//        Endpoint endpoint = new ErlangEndpoint();
        setProperties(endpoint, parameters);
        return endpoint;
    }

//	public Endpoint getEndpoint() {
//		return endpoint;
//	}
//
//	public void setEndpoint(Endpoint endpoint) {
//		this.endpoint = endpoint;
//	}

}
