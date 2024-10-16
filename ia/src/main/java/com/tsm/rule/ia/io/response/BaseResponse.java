package com.tsm.rule.ia.io.response;

public sealed interface BaseResponse permits AcquistiGenericoResponse, AcquistiPokemonResponse, VenditeGenericoResponse, VenditePokemonResponse {


    String code();
    String messaggio();
}
