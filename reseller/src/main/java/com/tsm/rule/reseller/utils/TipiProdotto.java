package com.tsm.rule.reseller.utils;


public enum TipiProdotto {

    CARTA_SINGOLA("Carta singola"),
    PRODOTTO_SEALED("Prodotto sealed"),
    PRODOTTO_NO_SEALED("Prodotto non sealed"),
    PRODOTTO_USATO("Prodotto usato");


    private String value;

    TipiProdotto(String value) {
        this.value = value;
    }

}
