package es.munisports.scraper.core.model;

import lombok.Data;

import java.util.List;

/**
 * Created by David Munoz on 22/09/2017.
 */
@Data
public class Competition {
    private String id;
    private String name;

    private Sport sport;
    private Object classification;
    private List<Match> matches;
}
