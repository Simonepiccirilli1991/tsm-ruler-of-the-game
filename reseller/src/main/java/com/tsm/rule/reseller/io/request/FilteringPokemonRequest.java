package com.tsm.rule.reseller.io.request;

import java.time.LocalDateTime;

public record FilteringPokemonRequest(String nome, LocalDateTime startDate,LocalDateTime endDate,Double totaleMin,Double totaleMax,
                                      String acquistatoPresso) {
}
