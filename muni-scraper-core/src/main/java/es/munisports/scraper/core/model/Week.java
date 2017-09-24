package es.munisports.scraper.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by David Munoz on 22/09/2017.
 */
@Data
@NoArgsConstructor
public class Week {
    private String id;
    private String name;

    public Week(String name) {
        this.name = name;
    }
}
