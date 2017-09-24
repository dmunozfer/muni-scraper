package es.munisports.scraper.leganes.parser.select;

import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import es.munisports.scraper.core.model.Competition;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David Munoz on 24/09/2017.
 */
public class CompetitionSelectParser {

    static public List<Competition> parse(HtmlSelect selectCompetitions) {
        return selectCompetitions.getOptions().stream()
                .map(x -> mapToCompetition(x))
                .filter(CompetitionSelectParser::isValidCompetition)
                .collect(Collectors.toList());
    }

    static private Competition mapToCompetition(HtmlOption htmlOption) {
        Competition competition = new Competition();
        competition.setId(htmlOption.getValueAttribute());
        competition.setName(htmlOption.getText());
        return competition;
    }

    static private boolean isValidCompetition(Competition competition) {
        return !"-1".equals(competition.getId());
    }
}
