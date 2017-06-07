package components.aws;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class AWSComponent extends DefaultComponent {

    private final Endpoint endpoint;

//    public AWSComponent(Endpoint endpoint) {
//        this.endpoint = endpoint;
//    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
//        Endpoint endpoint = new AWSEndpoint();
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