package com.tsm.rule.reseller.model.base;

import jakarta.persistence.*;
import lombok.Data;


@MappedSuperclass// usato per far mappare la classe astratta da l'entity
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
