package component.excel;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.apache.camel.spi.UriParam;

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

	public String getFileNameTo() {
		return fileNameTo;
	}

	public void setFileNameTo(String fileName) {
		this.fileNameTo = fileName;
	}

	public String getFileNameFrom() {
		return fileNameFrom;
	}

	public void setFileNameFrom(String fileNameFrom) {
		this.fileNameFrom = fileNameFrom;
	}

	public int getSheetNo() {
		return sheetNo;
	}

	public void setSheetNo(int sheet) {
		this.sheetNo = sheet;
	}

	public int getRowNo() {
		return rowNo;
	}

	public void setRowNo(int row) {
		this.rowNo = row;
	}

	public int getCellNo() {
		return cellNo;
	}

	public void setCellNo(int cell) {
		this.cellNo = cell;
	}

}
