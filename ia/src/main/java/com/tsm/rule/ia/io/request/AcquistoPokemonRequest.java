package com.tsm.rule.ia.io.request;

import java.time.LocalDateTime;

public record AcquistoPokemonRequest(String nome, LocalDateTime data, String codiceUnivoco, Double prezzoTotale,
                                     Double prezzoSingolo, int quantita) implements AcquistiRequest{
}
