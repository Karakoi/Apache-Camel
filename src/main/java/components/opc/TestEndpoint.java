package components.opc;

import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.Consumer;
import org.apache.camel.Endpoint;
import org.apache.camel.EndpointConfiguration;
import org.apache.camel.Exchange;
import org.apache.camel.ExchangePattern;
import org.apache.camel.PollingConsumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.spi.UriParam;

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

	public int getConnTimeDelay() {
		return connTimeDelay;
	}

	public void setConnTimeDelay(int connTimeDelay) {
		this.connTimeDelay = connTimeDelay;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getClsid() {
		return clsid;
	}

	public void setClsid(String clsid) {
		this.clsid = clsid;
	}

	public String getItemsForWrite() {
		return itemsForWrite;
	}

	public void setItemsForWrite(String itemsForWrite) {
		this.itemsForWrite = itemsForWrite;
	}

	public String getItemForWrite() {
		return itemForWrite;
	}

	public void setItemForWrite(String itemForWrite) {
		this.itemForWrite = itemForWrite;
	}

	public String getItemsForRead() {
		return itemsForRead;
	}

	public void setItemsForRead(String itemsForRead) {
		this.itemsForRead = itemsForRead;
	}

	public String getItemForRead() {
		return itemForRead;
	}

	public void setItemForRead(String itemForRead) {
		this.itemForRead = itemForRead;
	}

	public String getTypeOfProducer() {
		return typeOfProducer;
	}

	public void setTypeOfProducer(String typeOfProducer) {
		this.typeOfProducer = typeOfProducer;
	}

	public String getTypeOfConsumer() {
		return typeOfConsumer;
	}

	public void setTypeOfConsumer(String typeOfConsumer) {
		this.typeOfConsumer = typeOfConsumer;
	}

	public Producer getProducer() {
		return producer;
	}

	public void setProducer(Producer producer) {
		this.producer = producer;
	}

	public Consumer getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer consumer) {
		this.consumer = consumer;
	}

	@Override
	public boolean isLenientProperties() {
		// TODO Auto-generated method stub
		return false;
	}

}
