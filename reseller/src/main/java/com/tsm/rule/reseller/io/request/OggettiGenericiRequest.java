package com.tsm.rule.reseller.io.request;

import com.tsm.rule.reseller.utils.BrandAssociati;
import com.tsm.rule.reseller.utils.TipiOggetto;
import com.tsm.rule.reseller.utils.TipiProdotto;

import java.time.LocalDateTime;

public record OggettiGenericiRequest(String nome, String note, LocalDateTime data, Double costoTotale, Integer quantita,
                                     TipiOggetto tipoOggetto, TipiProdotto tipiProdotto, BrandAssociati brandAssociato,
                                     Boolean sealed,String acquistatoDa) {


    public void validateSave(){
        //TODO: creare metodo x validazione
    }
}
