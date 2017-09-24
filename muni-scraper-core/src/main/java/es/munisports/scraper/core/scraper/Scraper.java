package es.munisports.scraper.core.scraper;

import es.munisports.scraper.core.model.*;

import java.util.List;

/**
 * Created by David Munoz on 22/09/2017.
 */
public interface Scraper {

    List<Sport> fetchSports();
    List<Team> fetchTeams(Sport sport);
    List<Competition> fetchCompetitions(Sport sport);
    List<Week> fetchWeeksFromCompetition(Competition competition);
    Classification fetchClassificationFromCompetition(Competition competition);
    List<Match> fetchMatchsResultFromCompetitionAndMatch(Competition competition, Week week);
    List<Match> fetchMatchsScheduleFromCompetitionAndMatch(Competition competition, Week week);
}
