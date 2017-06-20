package components.dynamicreports;

import components.dynamicreports.temp.Templates;
import lombok.extern.slf4j.Slf4j;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.chart.Bar3DChartBuilder;
import net.sf.dynamicreports.report.builder.chart.PieChartBuilder;
import net.sf.dynamicreports.report.builder.chart.TimeSeriesChartBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.style.FontBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.Orientation;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import org.apache.camel.Exchange;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static net.sf.dynamicreports.report.builder.DynamicReports.*;

@Slf4j
public class DynamicReports {

    public void build(DRDataSource dataSource) throws DRException {
        JasperReportBuilder report = report();

        //init styles
        FontBuilder boldFont = stl.fontArialBold();

        //init columns
        TextColumnBuilder<String> typeColumn = col.column("Type", "device_type", type.stringType());
        TextColumnBuilder<String> nameColumn = col.column("Device name", "device_name", type.stringType()).setPrintRepeatedDetailValues(false);
        TextColumnBuilder<Date> orderDateColumn = col.column("Date of purchase", "date_of_purchase", type.dateType());
        TextColumnBuilder<Integer> quantityColumn = col.column("Quantity", "quantity", type.integerType());
        TextColumnBuilder<BigDecimal> unitPriceColumn = col.column("Unit price", "unit_price", Templates.currencyType);
        //price = unitPrice * quantity
        TextColumnBuilder<BigDecimal> priceColumn = unitPriceColumn.multiply(quantityColumn).setTitle("Price")
                .setDataType(Templates.currencyType);
//        PercentageColumnBuilder pricePercColumn = col.percentageColumn("Price %", priceColumn);

        //init groups
        ColumnGroupBuilder typeGroup = grp.group(typeColumn);

        //init subtotals
        AggregationSubtotalBuilder<BigDecimal> unitPriceSum = sbt.sum(unitPriceColumn)
                .setLabel("Total:")
                .setLabelStyle(Templates.boldStyle);
        AggregationSubtotalBuilder<BigDecimal> priceSum = sbt.sum(priceColumn)
                .setLabel("")
                .setLabelStyle(Templates.boldStyle);

        //init charts
        Bar3DChartBuilder deviceChart = cht.bar3DChart()
                .setTitle("Sales by devices")
                .setTitleFont(boldFont)
                .setOrientation(Orientation.HORIZONTAL)
                .setCategory(nameColumn)
                .addSerie(
                        cht.serie(unitPriceColumn), cht.serie(priceColumn));

        PieChartBuilder typeChart = cht.pieChart()
                .setTitle("Sales by type")
                .setTitleFont(boldFont)
                .setFixedHeight(100)
                .setShowLegend(false)
                .setKey(nameColumn)
                .addSerie(
                        cht.serie(priceColumn));

        TimeSeriesChartBuilder dateChart = cht.timeSeriesChart()
                .setTitle("Sales by date")
                .setTitleFont(boldFont)
                .setFixedHeight(150)
                .setTimePeriod(orderDateColumn)
                .addSerie(
                        cht.serie(unitPriceColumn), cht.serie(priceColumn));
        //configure report
        report
                .setTemplate(Templates.reportTemplate)
                //columns
                .columns(
                        typeColumn, nameColumn, orderDateColumn, quantityColumn, unitPriceColumn, priceColumn)
                //groups
//                .groupBy(typeGroup)
                //subtotals
                .subtotalsAtFirstGroupFooter(sbt.sum(priceColumn))
                .subtotalsAtSummary(unitPriceSum, priceSum)
                //band components
                .title(
                        Templates.createTitleComponent("Sales"),
                        cmp.horizontalList(
                                deviceChart, cmp.verticalList(dateChart, typeChart)),
//                                deviceChart, cmp.verticalList()),
                        cmp.verticalGap(10))
                .pageFooter(
                        Templates.footerComponent)
                .setDataSource(dataSource);

        report.show();
    }

    public DRDataSource createDataSource(Exchange exchange) {


        DRDataSource dataSource = new DRDataSource("device_type", "device_name", "date_of_purchase", "quantity", "unit_price");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Set<List<String>> list = (Set<List<String>>) exchange.getIn().getBody();
        for (List<String> row : list) {
            try {
                dataSource.add(row.get(0).intern(), row.get(1), formatter.parse(row.get(2)), Integer.valueOf(row.get(3)), new BigDecimal(Integer.valueOf(row.get(4))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return dataSource;
    }

}
