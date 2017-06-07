package processors;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class OPCReadParserProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {

		List<Object> list = new ArrayList<Object>();
		Object obj = exchange.getIn().getBody();
		String s = obj.toString();
		System.out.println(s);
		Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
		Matcher matcher = pat.matcher(s);
		while (matcher.find()) {
			list.add(matcher.group());
		}
		System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL \n" + list);

		Message message = exchange.getIn();
		String str = message.getBody(String.class);
		exchange.getIn().setBody(str);

	}

}
