package components.opc;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OPCReadConsumerFewItems extends ScheduledPollConsumer {

	List<Object> data = new ArrayList<Object>();

	// private final OPCEndpoint endpoint;
	private final TestEndpoint endpoint;
	private OPCRead opcRead;
	private Map<Integer, String> items;
	private Map<String, List<String>> jsonMap = new HashMap<String, List<String>>();

	// public OPCReadConsumerFewItems(OPCEndpoint endpoint, Processor processors)
	// {
	// super(endpoint, processors);
	// this.endpoint = endpoint;
	// }

	public OPCReadConsumerFewItems(TestEndpoint endpoint, Processor processor) {
		super(endpoint, processor);
		this.endpoint = endpoint;
	}

	@Override
	public long beforePoll(long timeout) throws Exception {
		opcRead = new OPCRead();
		opcRead.init(endpoint.getHostName(), endpoint.getUserName(), endpoint.getUserPassword(), endpoint.getClsid());
		opcRead.connect();
		ItemsPerser();
		return super.beforePoll(timeout);
	}

	protected int poll() throws Exception {

		Exchange exchange = endpoint.createExchange();
		// create a message body
		beforePoll(1);
		getData();
		// exchange.getIn().setBody(opcRead.doRead(endpoint.getConnTimeDelay()));

		for (int i = 0; i < items.size(); i++) {
			for (Map.Entry<String, List<String>> m : jsonMap.entrySet()) {
				List<String> jsonList = new ArrayList<String>();
				jsonList = m.getValue();
				if (items.get(i).equals(m.getKey()) && jsonList.get(0).equals("read")) {
					exchange.getIn().setBody(opcRead.doRead(m.getKey(), endpoint.getConnTimeDelay()));
					exchange.getIn().setHeader("tagName", m.getKey());
				}
			}
		}

		try {
			// send message to next processors in the route
			getProcessor().process(exchange);
			return 1; // number of messages polled
		} finally {
			// log exception if an exception occurred and was not handled
			if (exchange.getException() != null) {
				getExceptionHandler().handleException("Error processing exchange", exchange, exchange.getException());
			}
		}
	}

	public void ItemsPerser() {
		String str = endpoint.getItemsForRead();
		String[] strItems = str.split(";");
		items = new HashMap<Integer, String>();
		for (int i = 0; i < strItems.length; i++) {
			items.put(i, strItems[i]);
		}
	}

	public void getData() {
		OPCJsonAdapter adapter = new OPCJsonAdapter();
		try {
			jsonMap = adapter.parseForConsumer();
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}

	public Map<Integer, String> getItems() {
		return items;
	}

	public void setItems(Map<Integer, String> items) {
		this.items = items;
	}

}
