package com.tsm.rule.reseller.model.entity;

import com.tsm.rule.reseller.model.base.AcquistoBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "oggetto_generico")
@Data
public class OggettoGenerico extends AcquistoBase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String acquistatoPresso;
    private Double speseAccessorie;
    private String tipoOggetto;
    private Boolean sigillato;
    private String note;

}
