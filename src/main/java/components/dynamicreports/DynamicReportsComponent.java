package components.dynamicreports;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;

import java.util.Map;

@Getter
@Setter
@RequiredArgsConstructor
public class DynamicReportsComponent extends DefaultComponent {

    private final Endpoint endpoint;


    @Override
    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
//        Endpoint endpoint = new DynamicReportsEndpoint();
        setProperties(endpoint, parameters);
        return endpoint;
    }

}
