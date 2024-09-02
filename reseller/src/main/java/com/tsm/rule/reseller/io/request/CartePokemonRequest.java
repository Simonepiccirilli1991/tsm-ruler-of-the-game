package com.tsm.rule.reseller.io.request;

import com.tsm.rule.reseller.utils.TipiProdotto;

import java.time.LocalDateTime;

public record CartePokemonRequest(String nome, LocalDateTime data,Double costoTotale,Integer quantita,
                                  String codice, TipiProdotto tipoProdotto,Boolean sealed,Boolean setSpeciale,
                                  Boolean graded, String gradedValue,String acquistatoPresso) {


    public void validateSave(){
        //TODO: inserire logica di validazione sul save x parametri obbligatori
    }

    public void validatePatch(){
        //TODO: inserire logica di validazione patch
    }
}
