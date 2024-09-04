package com.tsm.rule.reseller.model.entity;

import com.tsm.rule.reseller.model.internal.InteressantiUpdate;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "OggettiInteressanti")
@Data
public class OggettiInteressati {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(nullable = false,unique = true)
    private String nome;
    private String note;
    @Column(nullable = false)
    private String tipoOggetto;
    @Column(nullable = false)
    private String brandAssociato;
    @Column(nullable = false)
    private String dataIniziale;
    @Column(nullable = false)
    private Double prezzoIniziale;
    @Embedded
    private List<InteressantiUpdate> updates = new ArrayList<>();

}
