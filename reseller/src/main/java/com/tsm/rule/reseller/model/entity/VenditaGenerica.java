package com.tsm.rule.reseller.model.entity;

import com.tsm.rule.reseller.model.base.VenditaBase;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity(name = "vendita_generica")
@Data
public class VenditaGenerica extends VenditaBase {


    private String vendutoSu;
    private String note;
    private String brandAssociato;
    private String tipoDiOggetto;
}
