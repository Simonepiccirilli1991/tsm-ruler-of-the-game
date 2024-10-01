package com.tsm.rule.reseller.io.request;

import com.tsm.rule.reseller.utils.BrandAssociati;
import com.tsm.rule.reseller.utils.PiattaformeVendita;
import com.tsm.rule.reseller.utils.TipiOggetto;

import java.time.LocalDateTime;

public record VenditaGenericaRequest(String nome, LocalDateTime data, Integer quantita, Double entrataTotale,
                                     Double spese, String chiaveAcquisto, PiattaformeVendita vendutoSu, String note,
                                     BrandAssociati brandAssociato, TipiOggetto tipoDiOggetto) implements VenditaBaseRequest {
}
