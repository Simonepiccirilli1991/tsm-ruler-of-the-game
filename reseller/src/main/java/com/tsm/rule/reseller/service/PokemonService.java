package com.tsm.rule.reseller.service;

import com.tsm.rule.reseller.model.entity.Pokemon;
import com.tsm.rule.reseller.repo.PokemonRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PokemonService {


    private final PokemonRepo pokemonRepo;

    // save acquisto pokemon
    public Pokemon saveAcquistoPokemon(){
        //TODO: finire di implementare il tutto
    }
}
