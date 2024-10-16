package com.tsm.rule.ia.service;

import com.tsm.rule.ia.io.request.AcquistoPokemonRequest;
import com.tsm.rule.ia.io.request.VenditaPokemonRequest;
import com.tsm.rule.ia.io.response.AcquistiPokemonResponse;
import com.tsm.rule.ia.io.response.VenditePokemonResponse;
import com.tsm.rule.ia.llminteraction.GenericiInteraction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnalizzaAcquistiService {

    private final GenericiInteraction genericiInteraction;

    public AcquistiPokemonResponse analizzaAcquistiPokemon(List<AcquistoPokemonRequest> request) {
        log.info("analizzaAcquistiPokemon service started with raw request: {}", request);
        // creo prompt
        var prompt = " analyze the json i provided to fid the most valuable item";
        var llmResp = genericiInteraction.analizzaAcquisti(prompt, request);
        var resp = new AcquistiPokemonResponse("00", llmResp);

        log.info("analizzaAcquistiPokemon service ended successfully");
        return resp;
    }
}
