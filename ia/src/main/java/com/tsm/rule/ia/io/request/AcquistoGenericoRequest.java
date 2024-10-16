package com.tsm.rule.ia.io.request;

import java.time.LocalDateTime;

public record AcquistoGenericoRequest(String nome, LocalDateTime data,String codiceUnivoco, Double prezzoTotale,
                                      Double prezzoSingolo, int quantita) implements AcquistiRequest{
}
