package es.munisports.scraper.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by David Munoz on 22/09/2017.
 */
@Data
@NoArgsConstructor
public class Sport {
    private String id;
    private String name;

    public Sport(String name) {
        this.name = name;
    }
}
