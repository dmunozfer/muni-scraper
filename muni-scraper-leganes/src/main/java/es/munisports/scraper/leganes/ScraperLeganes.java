package es.munisports.scraper.leganes;

import com.gargoylesoftware.htmlunit.html.*;
import es.munisports.scraper.core.model.*;
import es.munisports.scraper.core.scraper.Scraper;
import es.munisports.scraper.core.utils.MuniWebClient;
import es.munisports.scraper.leganes.parser.select.CompetitionSelectParser;
import es.munisports.scraper.leganes.parser.select.SportSelectParser;
import es.munisports.scraper.leganes.parser.select.TeamSelectParser;
import es.munisports.scraper.leganes.parser.select.WeekSelectParser;
import es.munisports.scraper.leganes.parser.table.ClassificationAbstractTableParser;
import es.munisports.scraper.leganes.parser.table.ResultAbstractTableParser;
import es.munisports.scraper.leganes.parser.table.ScheduleAbstractTableParser;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;

/**
 * Created by David Munoz on 21/09/2017.
 */
public class ScraperLeganes implements Scraper {

    private static final String URL_LOGIN = "http://www.leganes.org/campeonatosweb/Login.aspx";
    private static final String URL_MENU = "http://www.leganes.org/campeonatosweb/MenuArriba.aspx";

    @Override
    @SneakyThrows
    public List<Sport> fetchSports() {
        try (final MuniWebClient webClient = new MuniWebClient()) {
            HtmlSelect selectSports = (HtmlSelect) fetchElementFromMenu(webClient, "cbxDeportes");
            return SportSelectParser.parse(selectSports);
        }
    }

    @Override
    @SneakyThrows
    public List<Team> fetchTeams(Sport sport) {
        try (final MuniWebClient webClient = new MuniWebClient()) {
            HtmlSelect selectTeams = (HtmlSelect) fetchElementWhenSportIsSelected(webClient, sport.getId(), "cbxEquipos");
            return TeamSelectParser.parse(selectTeams);
        }
    }

    @Override
    @SneakyThrows
    public List<Competition> fetchCompetitions(Sport sport) {
        try (final MuniWebClient webClient = new MuniWebClient()) {
            HtmlSelect selectCompetitions = (HtmlSelect) fetchElementWhenSportIsSelected(webClient, sport.getId(), "cbxCampeonatos");
            return CompetitionSelectParser.parse(selectCompetitions);
        }
    }

    @Override
    @SneakyThrows
    public List<Week> fetchWeeksFromCompetition(Competition competition) {
        try (final MuniWebClient webClient = new MuniWebClient()) {
            final HtmlSelect selectWeeks = (HtmlSelect) fetchElementWhenCompetitionResultIsSelected(webClient, competition, "cbxJornadas");
            return WeekSelectParser.parse(selectWeeks);
        }
    }

    @Override
    @SneakyThrows
    public Classification fetchClassificationFromCompetition(Competition competition) {
        try (final MuniWebClient webClient = new MuniWebClient()) {
            final HtmlTable classificationTable = (HtmlTable) fetchElementWhenCompetitionResultIsSelected(webClient, competition, "grdClasificaciones");
            return ClassificationAbstractTableParser.parse(classificationTable);
        }
    }

    @Override
    @SneakyThrows
    public List<Match> fetchMatchsResultFromCompetitionAndMatch(Competition competition, Week week) {
        try (final MuniWebClient webClient = new MuniWebClient()) {
            final HtmlSelect selectWeeks = (HtmlSelect) fetchElementWhenCompetitionResultIsSelected(webClient, competition, "cbxJornadas");
            selectWeeks.getOptionByValue(week.getName()).setSelected(true);
            HtmlTable resultsTable = (HtmlTable) webClient.waitUntilLoadedElementWithId(selectWeeks.click(), "grdResultados");
            return ResultAbstractTableParser.parse(resultsTable);
        }
    }

    @Override
    @SneakyThrows
    public List<Match> fetchMatchsScheduleFromCompetitionAndMatch(Competition competition, Week week) {
        try (final MuniWebClient webClient = new MuniWebClient()) {
            final HtmlSelect selectWeeks = (HtmlSelect) fetchElementWhenCompetitionScheduleIsSelected(webClient, competition, "cbxJornadas");
            selectWeeks.getOptionByValue(week.getName()).setSelected(true);
            final HtmlTable scheduleTable = (HtmlTable) webClient.waitUntilLoadedElementWithId(selectWeeks.click(), "grdCalendario");
            return ScheduleAbstractTableParser.parse(scheduleTable);
        }
    }

    private DomElement fetchElementFromMenu(MuniWebClient webClient, String idElement) throws IOException {
        webClient.fetchPage(URL_LOGIN);
        return webClient.fetchPageAndGetElementWithId(URL_MENU, idElement);
    }

    private DomElement fetchElementWhenSportIsSelected(MuniWebClient webClient, String idSport, String idElement) throws IOException {
        HtmlPage page = fetchPageWhenSportIsSelected(webClient, idSport);
        return webClient.waitUntilLoadedElementWithId(page, idElement);
    }

    private HtmlPage fetchPageWhenSportIsSelected(MuniWebClient webClient, String idSport) throws IOException {
        HtmlSelect selectSports = (HtmlSelect) fetchElementFromMenu(webClient, "cbxDeportes");
        selectSports.getOptionByValue(idSport).setSelected(true);
        return selectSports.click();
    }

    private DomElement fetchElementWhenCompetitionResultIsSelected(MuniWebClient webClient, Competition competition, String idElement) throws IOException {
        HtmlForm form = (HtmlForm) fetchElementWhenSportIsSelected(webClient, competition.getSport().getId(), "Form1");
        form.getSelectByName("cbxCampeonatos").getOptionByValue(competition.getId()).setSelected(true);
        final HtmlPage pageClasification = webClient.clickElementAndGetElementWithId(form, "hlClasificacion").click();
        return pageClasification.getElementById(idElement);
    }

    private DomElement fetchElementWhenCompetitionScheduleIsSelected(MuniWebClient webClient, Competition competition, String idElement) throws IOException {
        HtmlForm form = (HtmlForm) fetchElementWhenSportIsSelected(webClient, competition.getSport().getId(), "Form1");
        form.getSelectByName("cbxCampeonatos").getOptionByValue(competition.getId()).setSelected(true);
        final HtmlPage pageClasification = webClient.clickElementAndGetElementWithId(form, "hlCalendario").click();
        return pageClasification.getElementById(idElement);
    }
}
