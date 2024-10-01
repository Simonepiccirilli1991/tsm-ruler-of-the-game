package com.tsm.rule.reseller.io.request;

import com.tsm.rule.reseller.utils.PiattaformeVendita;

import java.time.LocalDateTime;

public record VenditaPokemonRequest(String nome, LocalDateTime data, Integer quantita, Double entrataTotale,
                                    Double spese, String chiaveAcquisto, PiattaformeVendita vendutoSu, String note, String set) implements VenditaBaseRequest {
}
