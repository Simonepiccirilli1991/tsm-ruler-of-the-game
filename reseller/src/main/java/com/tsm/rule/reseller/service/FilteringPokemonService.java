package com.tsm.rule.reseller.service;

import com.tsm.rule.reseller.io.request.FilteringPokemonRequest;
import com.tsm.rule.reseller.model.entity.CartePokemon;
import com.tsm.rule.reseller.repo.CartaPokemonRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilteringPokemonService {


    private final CartaPokemonRepo cartaPokemonRepo;


    public List<CartePokemon> filteringCartePokemon(FilteringPokemonRequest request){
        log.info("FilteringCartePokemon service started with raw request: {}",request);


    }
}
