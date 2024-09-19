package com.tsm.rule.reseller.model.entity;

import com.tsm.rule.reseller.model.base.VenditaBase;
import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "vendite")
@Data
public class Vendita extends VenditaBase {


    @Column(nullable = false)
    private String vendutoSu;
    // indica se e un oggetto generico o carte pokemon
    @Column(nullable = false)
    private String tipoOggetto;
    private String brandAssociato;
}
