package components.opc;

import lombok.Getter;
import lombok.Setter;
import org.apache.camel.*;
import org.apache.camel.spi.UriParam;

import java.util.Map;

@Getter
@Setter
public class TestEndpoint implements Endpoint {

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
    private String typeOfProducer;

    @UriParam
    private String typeOfConsumer;

    private Producer producer;
    private Consumer consumer;

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void start() throws Exception {
    }

    @Override
    public void stop() throws Exception {

    }

    public TestEndpoint(String prod, String cons) {
        this.typeOfProducer = prod;
        this.typeOfConsumer = cons;
    }

    @Override
    public Producer createProducer() throws Exception {
        System.out.println(typeOfProducer);

        if (typeOfProducer.equals("single")) {
            producer = new OPCWriteProducerSingleItem(this);
            return producer;
        } else
            producer = new OPCWriteProducerFewItems(this);
        return producer;

    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        System.out.println(typeOfConsumer);
        if (typeOfConsumer.equals("single"))
            return new OPCReadConsumerSingleItem(this, processor);
        else
            return new OPCReadConsumerFewItems(this, processor);

    }

    @Override
    public String getEndpointUri() {
        return null;
    }

    @Override
    public EndpointConfiguration getEndpointConfiguration() {
        return null;
    }

    @Override
    public String getEndpointKey() {
        return null;
    }

    @Override
    public Exchange createExchange() {
        return this.createExchange();
    }

    @Override
    public Exchange createExchange(ExchangePattern pattern) {
        return this.createExchange();
    }

    @Override
    public Exchange createExchange(Exchange exchange) {
        return exchange;
    }

    @Override
    public CamelContext getCamelContext() {
        return null;
    }

    @Override
    public PollingConsumer createPollingConsumer() throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void configureProperties(Map<String, Object> options) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setCamelContext(CamelContext context) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean isLenientProperties() {
        // TODO Auto-generated method stub
        return false;
    }

}
