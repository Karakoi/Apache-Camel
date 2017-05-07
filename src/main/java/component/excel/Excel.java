package component.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.ChartLegend;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel {

	public void writeData(String fileName, String data) throws IOException {
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("Сторінка 1");
		Row row0 = sheet.createRow(0);
		Cell cell0 = row0.createCell(0);
		cell0.setCellValue("Змінна з OPC Server");
		Cell cell1 = row0.createCell(1);
		cell1.setCellValue("Час,с");

		List<Integer> list = parser(data);
		for (int i = 0; i < list.size(); i++) {
			Row row = sheet.createRow(i + 1);
			Cell cellValue = row.createCell(0);
			Cell cellTime = row.createCell(1);
			cellValue.setCellValue(list.get(i));
			cellTime.setCellValue(i);
		}

		// запишемо кількість зчитаних значень
		// Cell cell2 = row0.createCell(2);
		// cell2.setCellValue(list.size());

		drawCharts(sheet, list.size());

		OutputStream os = new FileOutputStream(fileName);
		workbook.write(os);
		os.close();
	}

	public List<Integer> parser(String str) {
		List<Integer> res = new ArrayList<>();
		Pattern pat = Pattern.compile("[-]?[0-9]+(.[0-9]+)?");
		Matcher matcher = pat.matcher(str);
		while (matcher.find()) {
			String s = matcher.group();
			res.add(Integer.valueOf(s));
		}
		System.out.println("\n" + res);
		return res;
	}

	public String readSingleData(String fileName, int sheet, int row, int cell) throws IOException {

		InputStream is = new FileInputStream(fileName);
		Workbook workbook = new XSSFWorkbook(is);
		return getCell(workbook.getSheetAt(sheet).getRow(row).getCell(cell));
	}

	public void drawCharts(Sheet sheet, int pointsNum) {
		// final int NUM_OF_ROWS = pointsNum;
		// final int NUM_OF_COLUMNS = 2;

		Drawing drawing = sheet.createDrawingPatriarch();
		ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 3, 5, 15, 20);

		Chart chart = drawing.createChart(anchor);
		ChartLegend legend = chart.getOrCreateLegend();
		legend.setPosition(LegendPosition.TOP_RIGHT);

		LineChartData data = chart.getChartDataFactory().createLineChartData();

		// Use a category axis for the bottom axis.
		ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
		ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
		leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

		ChartDataSource<Number> x = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(1, pointsNum, 1, 1));
		ChartDataSource<Number> y = DataSources.fromNumericCellRange(sheet, new CellRangeAddress(1, pointsNum, 0, 0));

		data.addSeries(x, y);

		chart.plot(data, bottomAxis, leftAxis);

	}

	public String getCell(Cell cell) {

		String result = "";
		switch (cell.getCellTypeEnum()) {
		case STRING:
			result = cell.getRichStringCellValue().getString();
			break;
		case NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				result = cell.getDateCellValue().toString();
			} else {
				result = Double.toString(cell.getNumericCellValue());
			}
			break;
		case BOOLEAN:
			result = Boolean.toString(cell.getBooleanCellValue());
			break;
		case FORMULA:
			result = cell.getCellFormula().toString();
			break;
		case BLANK:
			break;
		default:
			break;
		}
		return result;
	}
}
