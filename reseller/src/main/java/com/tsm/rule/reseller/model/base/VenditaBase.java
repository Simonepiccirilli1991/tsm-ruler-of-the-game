package com.tsm.rule.reseller.model.base;

import lombok.Data;

@Data
public abstract class VenditaBase {

    private String data;
    private String nome;
    private Integer quantita;
    private Double entrataSingola;
    private Double entrataTotale;
    private Double spese;
    private Double nettoTotale;
}
