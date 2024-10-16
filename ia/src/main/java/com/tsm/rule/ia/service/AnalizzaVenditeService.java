package com.tsm.rule.ia.service;


import com.tsm.rule.ia.io.request.VenditaPokemonRequest;
import com.tsm.rule.ia.io.response.VenditePokemonResponse;
import com.tsm.rule.ia.llminteraction.GenericiInteraction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnalizzaVenditeService {

    private final GenericiInteraction genericiInteraction;



    public VenditePokemonResponse analizzaVenditaPokemon(VenditaPokemonRequest request){
        log.info("AnalizzaVenditePokemon service started with raw request: {}",request);
        // creo prompt
        var prompt = "U are gona act like a selling analyzer, i'm gona provided a json with a list of" +
                "object that i have selled. I want u to give me some info of the item i'm selling the most";

        var llmResp = genericiInteraction.analizzaVendite(prompt,request);

        var resp = new VenditePokemonResponse("00",llmResp);
        log.info("AnalizzaVenditePoemon service ended successfully");
        return resp;
    }
}
