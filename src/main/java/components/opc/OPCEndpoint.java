package components.opc;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriParam;

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

	public String getItemsForRead() {
		return itemsForRead;
	}

	public void setItemsForRead(String itemsForRead) {
		this.itemsForRead = itemsForRead;
	}

	public String getItemForWrite() {
		return itemForWrite;
	}

	public void setItemForWrite(String itemForWrite) {
		this.itemForWrite = itemForWrite;
	}

	public String getItemForRead() {
		return itemForRead;
	}

	public void setItemForRead(String itemForRead) {
		this.itemForRead = itemForRead;
	}

	public String getTypeOfProd() {
		return typeOfProd;
	}

	public void setTypeOfProd(String typeOfProd) {
		this.typeOfProd = typeOfProd;
	}

	public String getTypeOfConsumer() {
		return typeOfConsumer;
	}

	public void setTypeOfConsumer(String typeOfConsumer) {
		this.typeOfConsumer = typeOfConsumer;
	}

}
