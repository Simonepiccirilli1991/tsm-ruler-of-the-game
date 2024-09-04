package com.tsm.rule.reseller.model.entity;

import com.tsm.rule.reseller.model.base.AcquistoBase;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "OggettoGenerico")
public class OggettoGenerico extends AcquistoBase {


    private String tipoOggetto;
    private String acquistatoDa;
    private Boolean sealed;
    private String brandAssociato;
    // se e sealed , usato , aperto
    private String tipoProdotto;
}
