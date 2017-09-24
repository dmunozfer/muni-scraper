package es.munisports.scraper.leganes.parser.table;

import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import es.munisports.scraper.core.model.Classification;
import es.munisports.scraper.core.model.Classification.Entry;
import es.munisports.scraper.core.model.Team;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David Munoz on 24/09/2017.
 */
public class ClassificationAbstractTableParser extends AbstractTableParser {

    static public Classification parse(HtmlTable classificationTable) {
        Classification classification = new Classification();
        classification.setEntries(getClassificationEntries(classificationTable));
        return classification;
    }

    static private List<Entry> getClassificationEntries(HtmlTable classificationTable) {
        return getTableRowsWithoutHeader(classificationTable).stream()
                .map(x -> mapToClassificationEntry(x))
                .collect(Collectors.toList());
    }

    static private Classification.Entry mapToClassificationEntry(HtmlTableRow row) {
        Classification.Entry entry = new Classification.Entry();
        entry.setPosition(getColumnText(row, 0));
        entry.setTeam(mapToTeam(row));
        entry.setStats(mapToClassificationStat(row));
        return entry;
    }

    static private Team mapToTeam(HtmlTableRow row) {
        Team team = new Team();
        team.setName(getColumnText(row, 1));
        return team;
    }

    static private Classification.Stat mapToClassificationStat(HtmlTableRow row) {
        Classification.Stat stats = new Classification.Stat();
        stats.setGames(mapToClassificationGame(row));
        stats.setPoints(mapToClassificationPoint(row));
        stats.setScores(mapToClassificationScore(row));
        stats.setSanction(getColumnText(row, 12));
        return stats;
    }

    static private Classification.Game mapToClassificationGame(HtmlTableRow row) {
        Classification.Game game = new Classification.Game();
        game.setPlayed(getColumnText(row, 2));
        game.setWinned(getColumnText(row, 3));
        game.setLosed(getColumnText(row, 4));
        game.setTied(getColumnText(row, 5));
        return game;
    }

    static private Classification.Point mapToClassificationPoint(HtmlTableRow row) {
        Classification.Point point = new Classification.Point();
        point.setScored(getColumnText(row, 6));
        point.setAgainst(getColumnText(row, 7));
        return point;
    }

    static private Classification.Score mapToClassificationScore(HtmlTableRow row) {
        Classification.Score score = new Classification.Score();
        score.setScored(getColumnText(row, 8));
        score.setAgainst(getColumnText(row, 9));
        score.setSanction(getColumnText(row, 10));
        score.setTotal(getColumnText(row, 11));
        return score;
    }
}
