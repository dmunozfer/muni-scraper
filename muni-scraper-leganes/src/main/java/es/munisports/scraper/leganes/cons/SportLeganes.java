package es.munisports.scraper.leganes.cons;

import es.munisports.scraper.core.model.Sport;

/**
 * Created by David Munoz on 22/09/2017.
 */
public enum SportLeganes {

    BALONCESTO("60"),
    FUTBOL("180"),
    FUTBOL_7("190"),
    FUTBOL_SALA("200"),
    PADEL("300"),
    SQUASH("340"),
    TENIS("360"),
    UNIHOCKEY("400"),
    VOLEIBOL("410");

    private String code;

    SportLeganes(String code) {
        this.code = code;
    }

    public Sport toSport() {
        Sport sport = new Sport();
        sport.setId(code);
        sport.setName(name());
        return sport;
    }
}
