package components.opc;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OPCWriteProducerFewItems extends DefaultProducer {

    private static final transient Logger LOG = LoggerFactory.getLogger(OPCWriteProducerFewItems.class);
    private OPCEndpoint endpoint;
    // private TestEndpoint endpoint;
    private OPCWrite opcWrite;
    private Map<Integer, String> items;
    private Map<String, List<String>> jsonMap = new HashMap<String, List<String>>();

    // public OPCWriteProducerFewItems(OPCEndpoint endpoint) {
    // super(endpoint);
    // this.endpoint = endpoint;
    // }

    public OPCWriteProducerFewItems(Endpoint endpoint) {
        super(endpoint);
        this.endpoint = (OPCEndpoint) endpoint;
    }

    @Override
    public Exchange createExchange(Exchange exchange) {
        Exchange exch = endpoint.createExchange();
        return super.createExchange(exch);
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        System.out.println("++++++++++++++++++++++++ PRODUCER: START READING ++++++++++++++++++++++++++");
        opcWrite = new OPCWrite();
        opcWrite.init(endpoint.getHostName(), endpoint.getUserName(), endpoint.getUserPassword(), endpoint.getClsid());
        opcWrite.connect();
        ItemsPerser();
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        System.out.println("++++++++++++++++++++++++ PRODUCER: STOP READING ++++++++++++++++++++++++++");
    }

    public void process(Exchange exchange) throws Exception {
        getData(exchange);

        for (int i = 0; i < items.size(); i++) {
            for (Map.Entry<String, List<String>> m : jsonMap.entrySet()) {
                List<String> jsonList = new ArrayList<String>();
                jsonList = m.getValue();
                if (items.get(i).equals(m.getKey()) && jsonList.get(0).equals("write")) {
                    opcWrite.doWrite(m.getKey(), jsonList.get(1), endpoint.getConnTimeDelay());
                }
            }
        }

    }

    public void ItemsPerser() {
        String str = endpoint.getItemsForWrite();
        String[] strItems = str.split(";");
        items = new HashMap<Integer, String>();
        for (int i = 0; i < strItems.length; i++) {
            items.put(i, strItems[i]);
        }
    }

    public Map<Integer, String> getItems() {
        return items;
    }

    public void setItems(Map<Integer, String> items) {
        this.items = items;
    }

    public void getData(Exchange exchange) {
        OPCJsonAdapter adapter = new OPCJsonAdapter();
        try {
            jsonMap = adapter.parseForProducer(exchange);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
