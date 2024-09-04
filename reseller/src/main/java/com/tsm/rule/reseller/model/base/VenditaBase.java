package com.tsm.rule.reseller.model.base;

import jakarta.persistence.*;
import lombok.Data;

@MappedSuperclass// usato per far mappare la classe astratta da l'entity
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
