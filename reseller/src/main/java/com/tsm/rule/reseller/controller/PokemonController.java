package com.tsm.rule.reseller.controller;

import com.tsm.rule.reseller.io.request.CartePokemonRequest;
import com.tsm.rule.reseller.model.entity.CartePokemon;
import com.tsm.rule.reseller.reactivewrapper.PokemonWrapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/pokemon")
public class PokemonController {

    private final PokemonWrapperService pokemonService;


    @PostMapping("/add")
    public Mono<CartePokemon> saveCartaPokemon(@RequestBody CartePokemonRequest request){
        return pokemonService.saveCartePokemon(request);
    }

    @GetMapping("/get/{codiceOggetto}")
    public Mono<CartePokemon> getCartaPokemon(@PathVariable("codiceOggetto") String codiceOggetto){
        return pokemonService.getCartePokemon(codiceOggetto);
    }
}
