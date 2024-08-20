package com.tsm.rule.reseller.model.internal;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class InteressantiUpdate {

    private String data;
    private Double valore;
    private Double ultimoVenduto;
    private String dataVenduto;
    private String valoreAttualeComparato; // per vedere se e salito o sceso, mettiamo percentuale
}
