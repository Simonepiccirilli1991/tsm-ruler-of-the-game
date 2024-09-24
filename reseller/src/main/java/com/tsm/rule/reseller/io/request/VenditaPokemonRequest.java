package com.tsm.rule.reseller.io.request;

import java.time.LocalDateTime;

public record VenditaPokemonRequest(String nome, LocalDateTime data, Integer quantita, Double entrataTotale,
                                    Double spese,String chiaveAcquisto,String vendutoSu,String note,String set) implements VenditaBaseRequest {
}
