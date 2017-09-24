package es.munisports.scraper.leganes.parser.table;

import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David Munoz on 24/09/2017.
 */
public abstract class AbstractTableParser {

    static protected List<HtmlTableRow> getTableRowsWithoutHeader(HtmlTable classificationTable) {
        return classificationTable.getRows().stream()
                .filter(x -> !x.getAttribute("class").equals("DatagridCabecera"))
                .filter(x -> x.getCells().size() > 1)
                .collect(Collectors.toList());
    }

    static protected String getColumnText(HtmlTableRow row, int column) {
        return row.getCell(column).getTextContent();
    }
}
