package com.tsm.rule.reseller.io.request;

import com.tsm.rule.reseller.utils.BrandAssociati;
import com.tsm.rule.reseller.utils.TipiOggetto;

import java.time.LocalDateTime;

public record OggettiInteressantiRequest(String nome, String note, TipiOggetto tipoOggetto, BrandAssociati brandAssociato,
                                         LocalDateTime dataIniziale,Double prezzoFinale) {


    public void validateSave(){

    }
}
