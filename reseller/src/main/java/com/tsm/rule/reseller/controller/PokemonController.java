package com.tsm.rule.reseller.controller;

import com.tsm.rule.reseller.io.request.CartePokemonRequest;
import com.tsm.rule.reseller.io.request.FilteringPokemonRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
import com.tsm.rule.reseller.model.entity.CartePokemon;
import com.tsm.rule.reseller.reactivewrapper.PokemonWrapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

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

    @PatchMapping("/patch/{chiaveOggetto}")
    public Mono<CartePokemon> patchCartaPokemon(@PathVariable String chiaveOggetto,@RequestBody CartePokemonRequest request){
        return pokemonService.patchCartePokemon(request,chiaveOggetto);
    }

    @DeleteMapping("delete/{chiaveOggetto}")
    public Mono<BaseResponse> deleteCartaPokemon(@PathVariable String chiaveOggetto){
        return pokemonService.deleteCartePokemon(chiaveOggetto);
    }

    @PostMapping("/filtering")
    public Mono<List<CartePokemon>> filteringAcquistiPokemon(@RequestBody FilteringPokemonRequest request){
        return pokemonService.filteringCartePokemonAcquisti(request);
    }
}
