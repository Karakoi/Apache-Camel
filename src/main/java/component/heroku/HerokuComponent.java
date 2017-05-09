package component.heroku;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

import java.util.Map;

@Getter
@Setter
//@RequiredArgsConstructor
public class HerokuComponent extends DefaultComponent {

    private Endpoint endpoint;

    public HerokuComponent(Endpoint endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
       Endpoint endpoint = new HerokuEndpoint(uri, this);
//         endpoint = new HerokuEndpoint(uri, this);
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
