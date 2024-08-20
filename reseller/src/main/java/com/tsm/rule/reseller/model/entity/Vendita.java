package com.tsm.rule.reseller.model.entity;

import com.tsm.rule.reseller.model.base.VenditaBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "vendita")
@Data
public class Vendita extends VenditaBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String oggettoVendita;// se pokemon o oggetto generico
    private String piattaformaVendita; // dove l'ho venduto
    private Integer idAcquisto;
    private String giorniPassati; // quanti giorni sono passati da acquisto a vendita
}
