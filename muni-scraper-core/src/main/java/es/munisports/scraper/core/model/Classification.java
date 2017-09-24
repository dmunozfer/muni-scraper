package es.munisports.scraper.core.model;

import lombok.Data;

import java.util.List;

/**
 * Created by David Munoz on 23/09/2017.
 */
@Data
public class Classification {
    private List<Entry> entries;

    @Data
    public static class Entry {
        private String position;
        private Team team;
        private Stat stats;
    }

    @Data
    public static class Stat {
        private Game games;
        private Point points;
        private Score scores;
        private String sanction;
    }

    @Data
    public static class Game {
        private String played;
        private String winned;
        private String losed;
        private String tied;
    }

    @Data
    public static class Point {
        private String scored;
        private String against;
    }

    @Data
    public static class Score {
        private String scored;
        private String against;
        private String sanction;
        private String total;

    }
}
