package es.munisports.scraper.leganes.parser.select;

import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import es.munisports.scraper.core.model.Team;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by David Munoz on 24/09/2017.
 */
public class TeamSelectParser {

    static public List<Team> parse(HtmlSelect selectTeams) {
        return selectTeams.getOptions().stream()
                .map(x -> mapToTeam(x))
                .filter(TeamSelectParser::isValidTeam)
                .collect(Collectors.toList());
    }

    static private Team mapToTeam(HtmlOption htmlOption) {
        Team team = new Team();
        team.setId(htmlOption.getValueAttribute());
        team.setName(htmlOption.getText());
        return team;
    }

    static private boolean isValidTeam(Team team) {
        return !"-1".equals(team.getId());
    }
}
