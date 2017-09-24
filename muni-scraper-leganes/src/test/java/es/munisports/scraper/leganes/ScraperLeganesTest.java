package es.munisports.scraper.leganes;

import es.munisports.scraper.core.model.*;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

import static es.munisports.scraper.leganes.cons.SportLeganes.FUTBOL_7;
import static es.munisports.scraper.leganes.cons.SportLeganes.PADEL;

/**
 * Created by David Munoz on 22/09/2017.
 */
public class ScraperLeganesTest {

    private ScraperLeganes scraperLeganes = new ScraperLeganes();

    @Test
    public void fetchSports() throws Exception {
        List<Sport> sports = scraperLeganes.fetchSports();
        assertThat(sports).isNotEmpty();
    }

    @Test
    public void fetchTeams() throws Exception {
        List<Team> teams = scraperLeganes.fetchTeams(sport());
        assertThat(teams).isNotEmpty();
    }

    @Test
    public void fetchCompetitions() throws Exception {
        List<Competition> competitions = scraperLeganes.fetchCompetitions(sport());
        assertThat(competitions).isNotEmpty();
    }

    @Test
    public void fetchWeeksFromCompetition() throws Exception {
        List<Week> weeks = scraperLeganes.fetchWeeksFromCompetition(competitionFutbol7Id1943());
        assertThat(weeks).isNotEmpty();
    }

    @Test
    public void fetchClassificationFromCompetition() throws Exception {
        Classification classification = scraperLeganes.fetchClassificationFromCompetition(competitionPadelFase1());
        assertThat(classification).extracting("entries").doesNotContainNull();
    }

    @Test
    public void fetchMatchsResultFromCompetitionAndMatch() throws Exception {
        List<Match> matches = scraperLeganes.fetchMatchsResultFromCompetitionAndMatch(competitionPadelFase1(), week("1"));
        assertThat(matches).isNotEmpty();
    }

    @Test
    public void fetchMatchsResultFromCompetitionAndMatchCompareWeeks() throws Exception {
        List<Match> matches1 = scraperLeganes.fetchMatchsResultFromCompetitionAndMatch(competitionPadelFase1(), week("1"));
        List<Match> matches2 = scraperLeganes.fetchMatchsResultFromCompetitionAndMatch(competitionPadelFase1(), week("2"));
        assertThat(matches1).isNotEqualTo(matches2);
    }

    @Test
    public void fetchMatchsScheduleFromCompetitionAndMatch() throws Exception {
        List<Match> matches = scraperLeganes.fetchMatchsScheduleFromCompetitionAndMatch(competitionPadelFase1(), week("1"));
        assertThat(matches).isNotEmpty();
    }

    @Test
    public void fetchMatchsScheduleFromCompetitionAndMatchCompareWeeks() throws Exception {
        List<Match> matches1 = scraperLeganes.fetchMatchsScheduleFromCompetitionAndMatch(competitionPadelFase1(), week("1"));
        List<Match> matches2 = scraperLeganes.fetchMatchsScheduleFromCompetitionAndMatch(competitionPadelFase1(), week("2"));
        assertThat(matches1).isNotEqualTo(matches2);
    }

    private Sport sport() {
        return FUTBOL_7.toSport();
    }

    private Competition competitionFutbol7Id1943() {
        Competition competition = new Competition();
        competition.setId("1943");
        competition.setSport(sport());
        return competition;
    }

    private Competition competitionPadelFase1() {
        Competition competition = new Competition();
        competition.setId("1907");
        competition.setSport(PADEL.toSport());
        return competition;
    }

    private Week week(String id) {
        Week week = new Week();
        week.setId(id);
        week.setName(id);
        return week;
    }
}