package processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ToJsonProcessor implements Processor {

	public void process(Exchange exchange) throws Exception {
		// System.err.println("||||||||||||||||||||||||||||||||||||||||||||| ");
		System.err.println(exchange.getIn().getBody(String.class) + "\n");

		JsonParser parser = new JsonParser();
		Object obj = parser.parse(exchange.getIn().getBody(String.class));
		JsonObject jsonObject = (JsonObject) obj;
		exchange.getIn().setBody(jsonObject);
	}
}
