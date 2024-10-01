package com.tsm.rule.reseller.utils;

import lombok.Getter;

@Getter
public enum PiattaformeVendita {

    VINTED("Vinted"),
    EBAY("Ebay"),
    WALLAPOP("Wallapop"),
    FACEBOOK("Facebook"),
    PASSAPAROlA("Passaparola"),
    CONOSCENTI("Conoscenti"),
    ALTRO("Altro");


    private String value;

    PiattaformeVendita(String value) {
        this.value = value;
    }
}
