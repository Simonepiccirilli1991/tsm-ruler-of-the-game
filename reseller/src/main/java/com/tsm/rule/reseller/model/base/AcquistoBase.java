package com.tsm.rule.reseller.model.base;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public abstract class AcquistoBase {

    private String nome;
    private String data;
    @Column(unique = true)
    private String chiaveOggetto;
    private Double costoTotale;
    private Double costoSingolo;
    private int quantita;
}
