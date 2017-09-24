package es.munisports.scraper.core.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by David Munoz on 22/09/2017.
 */
@Data
@NoArgsConstructor
public class Category {
    private String id;
    private String name;

    public Category(String name) {
        this.name = name;
    }
}
