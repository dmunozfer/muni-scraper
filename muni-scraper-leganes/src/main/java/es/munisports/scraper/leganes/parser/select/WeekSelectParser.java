package es.munisports.scraper.leganes.parser.select;

import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import es.munisports.scraper.core.model.Week;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David Munoz on 24/09/2017.
 */
public class WeekSelectParser {

    static public List<Week> parse(HtmlSelect selectWeeks) {
        return selectWeeks.getOptions().stream()
                .map(x -> mapToWeek(x))
                .collect(Collectors.toList());
    }

    static private Week mapToWeek(HtmlOption htmlOption) {
        Week week = new Week();
        week.setId(htmlOption.getValueAttribute());
        week.setName(htmlOption.getText());
        return week;
    }
}
