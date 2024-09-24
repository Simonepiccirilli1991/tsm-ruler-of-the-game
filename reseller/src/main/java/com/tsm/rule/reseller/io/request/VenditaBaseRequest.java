package com.tsm.rule.reseller.io.request;

import java.time.LocalDateTime;

public sealed interface VenditaBaseRequest permits VenditaPokemonRequest, VenditaGenericaRequest {

    String nome();
    LocalDateTime data();
    Integer quantita();
    Double entrataTotale();
    Double spese();
    String chiaveAcquisto();
}
