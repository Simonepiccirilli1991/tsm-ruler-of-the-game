package com.tsm.rule.reseller.model.entity;

import com.tsm.rule.reseller.model.internal.InteressantiUpdate;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class OggettiInteressanti {

    // oggetti interessanti non acquistati, per vedere il prezzo overtime
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nome;
    private String tipoOggetto;
    private String dataPrimoinserimento;
    private Double valore;
    private Double ultimoVenduto;
    @Embedded
    private List<InteressantiUpdate> updates;
}
