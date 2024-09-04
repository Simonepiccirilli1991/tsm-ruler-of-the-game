package com.tsm.rule.reseller.utils;

import lombok.Getter;

@Getter
public enum TipiOggetto {

    COSTRUZIONI("Costruzioni"),
    CARTA("Carta"),
    TAZZE("Tazze"),
    LIBRO("Libro"),
    GENERICO("Generico"),
    CUSTOM("Custom");

    private String value;

    TipiOggetto(String value) {
        this.value = value;
    }


}
