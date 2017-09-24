package es.munisports.scraper.leganes.parser.select;

import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import es.munisports.scraper.core.model.Sport;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David Munoz on 24/09/2017.
 */
public class SportSelectParser {

    static public List<Sport> parse(HtmlSelect selectSports) {
        return selectSports.getOptions().stream()
                .map(x -> mapToSport(x))
                .filter(SportSelectParser::isValidSport)
                .collect(Collectors.toList());
    }

    static private Sport mapToSport(HtmlOption htmlOption) {
        Sport sport = new Sport();
        sport.setId(htmlOption.getValueAttribute());
        sport.setName(htmlOption.getText());
        return sport;
    }

    static private boolean isValidSport(Sport sport) {
        return !"-1".equals(sport.getId());
    }
}
