package com.tsm.rule.reseller.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public abstract class VenditaBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String data;
    private String nome;
    @Column(nullable = false)
    private Integer quantita;
    @Column(nullable = false)
    private Double entrataSingola;
    @Column(nullable = false)
    private Double entrataTotale;
    private Double spese;
    @Column(nullable = false)
    private Double nettoTotale;
    @Column(nullable = false)
    private String keyAcquisto;
}
