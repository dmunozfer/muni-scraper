package es.munisports.scraper.leganes.parser.table;

import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import es.munisports.scraper.core.model.Match;
import es.munisports.scraper.core.model.Team;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David Munoz on 24/09/2017.
 */
public class ScheduleAbstractTableParser extends AbstractTableParser {

    static public List<Match> parse(HtmlTable resultsTable) {
        return getTableRowsWithoutHeader(resultsTable).stream()
                .map(x -> mapToMatchSchedule(x))
                .collect(Collectors.toList());
    }

    static private Match mapToMatchSchedule(HtmlTableRow row) {
        Match match = new Match();
        String strDate = getColumnText(row, 0);
        String strTime = getColumnText(row, 1);
        match.setDate(toLocalDate(strDate + " " + strTime));
        match.setLocalTeam(new Team(getColumnText(row, 2)));
        match.setVisitorTeam(new Team(getColumnText(row, 3)));
        match.setCourt(getColumnText(row, 4));
        match.setReferee(getColumnText(row, 5));
        return match;
    }

    static private LocalDateTime toLocalDate(String dateString) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return LocalDateTime.parse(dateString, dtf);
    }
}
