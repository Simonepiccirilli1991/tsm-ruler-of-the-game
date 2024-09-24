package com.tsm.rule.reseller.io.request;

import java.time.LocalDateTime;

public record VenditaGenericaRequest(String nome, LocalDateTime data, Integer quantita, Double entrataTotale,
                                     Double spese,String chiaveAcquisto,String vendutoSu, String note,
                                     String brandAssociato, String tipoDiOggetto) implements VenditaBaseRequest {
}
