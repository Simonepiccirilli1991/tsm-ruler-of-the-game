package com.tsm.rule.ia.io.request;

import java.time.LocalDateTime;

public sealed interface AcquistiRequest permits AcquistoGenericoRequest , AcquistoPokemonRequest{

    String nome();
    LocalDateTime data();
    String codiceUnivoco();
    Double prezzoTotale();
    Double prezzoSingolo();
    int quantita();
}
