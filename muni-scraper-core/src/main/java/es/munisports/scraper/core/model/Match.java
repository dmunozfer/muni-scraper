package es.munisports.scraper.core.model;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by David Munoz on 22/09/2017.
 */
@Data
public class Match {
    private String id;
    private Integer week;
    private LocalDateTime date;

    private Team localTeam;
    private Team visitorTeam;

    private Score score;

    private String court;
    private String referee;
    private String notes;

    @Data
    public static class Score {
        private String local;
        private String visitor;
        private String partials;
    }
}
