package com.tsm.rule.reseller.model.entity;

import com.tsm.rule.reseller.model.base.AcquistoBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "pokemon")
public class Pokemon extends AcquistoBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String codice;
    private String tipo;// carta singola o oggetto
    private Boolean sealed;
    private String espansione; // nome espansione
    private String era; // spada e scudo, scarlatto e violeto etc
    private Double speseAccessorie;
    private Boolean gradata;
    private String valoreGradazione;
    private Integer disponibile;
}
