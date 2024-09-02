package com.tsm.rule.reseller.model.base;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public abstract class AcquistoBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String data;
    @Column(unique = true,nullable = false)
    private String chiaveOggetto;
    @Column(nullable = false)
    private Double costoTotale;
    @Column(nullable = false)
    private Double costoSingolo;
    @Column(nullable = false)
    private int quantita;// quantita acquistata
}
