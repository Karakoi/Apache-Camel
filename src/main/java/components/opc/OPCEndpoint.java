package components.opc;

import lombok.Getter;
import lombok.Setter;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriParam;

@Getter
@Setter
public class OPCEndpoint extends DefaultEndpoint {

    @UriParam
    private int connTimeDelay = 0;

    @UriParam
    private String hostName;

    @UriParam
    private String userName;

    @UriParam
    private String userPassword;

    @UriParam
    private String clsid;

    @UriParam
    private String itemsForWrite;

    @UriParam
    private String itemForWrite;

    @UriParam
    private String itemsForRead;

    @UriParam
    private String itemForRead;

    @UriParam
    private String typeOfProd;

    @UriParam
    private String typeOfConsumer;

    private Producer producer;
    private Consumer consumer;

    public OPCEndpoint() {
    }

    public OPCEndpoint(String uri, OPCComponent component) {
        super(uri, component);
    }

    public OPCEndpoint(String endpointUri) {
        super(endpointUri);
    }

    public Producer createProducer() throws Exception {
        // return new OPCWriteProducerSingleItem(this);
        // return getProducer();
        if (this.typeOfProd.equals("few")) {
            return new OPCWriteProducerFewItems(this);
        } else
            return new OPCWriteProducerSingleItem(this);

    }

    public Consumer createConsumer(Processor processor) {
        // return new OPCReadConsumerFewItems(this, processors);
        return new OPCReadConsumerSingleItem(this, processor);
        // return getConsumer();
    }

    public boolean isSingleton() {
        return false;
    }

}
