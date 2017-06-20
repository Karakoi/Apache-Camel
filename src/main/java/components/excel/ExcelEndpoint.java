package components.excel;

import lombok.Getter;
import lombok.Setter;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriParam;

@Getter
@Setter
public class ExcelEndpoint extends DefaultEndpoint {

    @UriParam
    private String fileNameTo;

    @UriParam
    private String fileNameFrom;

    @UriParam
    private int sheetNo;

    @UriParam
    private int rowNo;

    @UriParam
    private int cellNo;

    public ExcelEndpoint() {
    }

    public ExcelEndpoint(String uri, ExcelComponent component) {
        super(uri, component);
    }

    public ExcelEndpoint(String endpointUri) {
        super(endpointUri);
    }

    @Override
    public Producer createProducer() throws Exception {
        return new ExcelProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return new ExcelConsumer(this, processor);
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
