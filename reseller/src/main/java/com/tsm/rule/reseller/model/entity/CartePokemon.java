package com.tsm.rule.reseller.model.entity;

import com.tsm.rule.reseller.model.base.AcquistoBase;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity(name = "cartePokemon")
@Data
public class CartePokemon extends AcquistoBase {


    private String codice;
    private String tipoProdotto; // se carta singola o prodotto
    private Boolean sealed;
    private Boolean setSpeciale;
    // sezione grading
    private Boolean graded;
    private String gradeValue; // psa 10 bgs 3 etc
    // dove ho acquistato oggetto
    private String acquistatoPresso;
    private Integer quantitaDisponibile;
}
