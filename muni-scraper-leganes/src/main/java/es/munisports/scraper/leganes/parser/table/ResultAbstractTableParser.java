package es.munisports.scraper.leganes.parser.table;

import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import es.munisports.scraper.core.model.Match;
import es.munisports.scraper.core.model.Match.Score;
import es.munisports.scraper.core.model.Team;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David Munoz on 24/09/2017.
 */
public class ResultAbstractTableParser extends AbstractTableParser {

    static public List<Match> parse(HtmlTable resultsTable) {
        return getTableRowsWithoutHeader(resultsTable).stream()
                .map(x -> mapToMatchResult(x))
                .collect(Collectors.toList());
    }

    static private Match mapToMatchResult(HtmlTableRow row) {
        Match match = new Match();
        match.setWeek(Integer.valueOf(getColumnText(row, 0)));
        String strDate = getColumnText(row, 1);
        String strTime = getColumnText(row, 2);
        match.setDate(toLocalDate(strDate + " " + strTime));
        match.setLocalTeam(new Team(getColumnText(row, 3)));
        match.setVisitorTeam(new Team(getColumnText(row, 5)));
        match.setNotes(getColumnText(row, 8));

        Score score = new Score();
        score.setLocal(getColumnText(row, 4));
        score.setVisitor(getColumnText(row, 6));
        score.setPartials(getColumnText(row, 7));
        match.setScore(score);
        return match;
    }

    static private LocalDateTime toLocalDate(String dateString) {
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return LocalDateTime.parse(dateString, dtf);
    }
}
