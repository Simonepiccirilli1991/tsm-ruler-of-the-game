package com.tsm.rule.reseller.model.entity;

import com.tsm.rule.reseller.model.base.VenditaBase;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity(name = "vendita_pokemon")
public class VenditaPokemon extends VenditaBase {

    private String vendutoSu;
    private String note;

    private String set;

}
