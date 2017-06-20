package processors;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ToJsonProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        JsonParser parser = new JsonParser();
        Object obj = parser.parse(exchange.getIn().getBody(String.class));
        JsonObject jsonObject = (JsonObject) obj;
        exchange.getIn().setBody(jsonObject);
    }
}
