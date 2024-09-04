package com.tsm.rule.reseller.utils;

import lombok.Getter;

@Getter
public enum BrandAssociati {

    POKEMON("Pokemon"),
    ONEPIECE("One Piece"),
    LEGO("Lego"),
    DISNEY("Disney"),
    CUSTOM("Custom"),
    GENERICO("Generico");

    private String value;

    BrandAssociati(String value) {
        this.value = value;
    }
}
