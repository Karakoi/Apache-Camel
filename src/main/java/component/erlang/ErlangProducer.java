package component.erlang;

import component.excel.Excel;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;

@Slf4j
public class ErlangProducer extends DefaultProducer {

	private ErlangEndpoint endpoint;
	private Erlang erlang;

	public ErlangProducer(ErlangEndpoint endpoint) {
		super(endpoint);
		this.endpoint = endpoint;
	}

	@Override
	protected void doStart() throws Exception {
		super.doStart();
		erlang = new Erlang(endpoint.getNode(), endpoint.getCookie());

	}

	@Override
	public void process(Exchange exchange) throws Exception {
		log.debug("Exchange: {}", exchange.getIn().getBody(String.class));
		erlang.writeAndReadData(endpoint.getOperation(), exchange.getIn().getBody(String.class));
	}

}
