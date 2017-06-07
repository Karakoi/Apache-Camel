package components.opc;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.ScheduledPollConsumer;

public class OPCReadConsumerSingleItem extends ScheduledPollConsumer {

	private final OPCEndpoint endpoint;
	// private final TestEndpoint endpoint;
	private OPCRead opcRead;

	// public OPCReadConsumerSingleItem(OPCEndpoint endpoint, Processor
	// processors) {
	// super(endpoint, processors);
	// this.endpoint = endpoint;
	// }

	public OPCReadConsumerSingleItem(Endpoint endpoint, Processor processor) {
		super(endpoint, processor);
		this.endpoint = (OPCEndpoint) endpoint;
	}

	@Override
	public long beforePoll(long timeout) throws Exception {
		opcRead = new OPCRead();
		opcRead.init(endpoint.getHostName(), endpoint.getUserName(), endpoint.getUserPassword(), endpoint.getClsid());
		opcRead.connect();
		return super.beforePoll(timeout);
	}

	protected int poll() throws Exception {

		Exchange exchange = endpoint.createExchange();
		// create a message body
		beforePoll(1);
		exchange.getIn().setBody(opcRead.doRead(endpoint.getItemForRead(), endpoint.getConnTimeDelay()));

		try {
			// send message to next processors in the route
			getProcessor().process(exchange);
			return 1; // number of messages polled
		} finally {
			// log exception if an exception occurred and was not handled
			if (exchange.getException() != null) {
//				getExceptionHandler().handleException("=====Error processing exchange", exchange,exchange.getException());
			}
		}
	}

}
