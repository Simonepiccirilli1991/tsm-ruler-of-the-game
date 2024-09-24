package com.tsm.rule.reseller.controller;

import com.tsm.rule.reseller.io.request.VenditaPokemonRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
import com.tsm.rule.reseller.model.entity.VenditaPokemon;
import com.tsm.rule.reseller.reactivewrapper.VenditePokemonWrapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/vendita/pkm/")
@RequiredArgsConstructor
public class VenditePokemonController {

    private final VenditePokemonWrapperService venditePokemonWrapperService;


    @PostMapping("save")
    public Mono<VenditaPokemon> saveVenditaPokemon(@RequestBody VenditaPokemonRequest request){
        return venditePokemonWrapperService.saveVenditaPokemon(request);
    }

    @GetMapping("get/{id}")
    public Mono<VenditaPokemon> getVenditaPokemon(@PathVariable ("id") Integer id){
        return venditePokemonWrapperService.getVenditaPokemon(id);
    }
    @DeleteMapping("delete/{id}")
    public Mono<BaseResponse> deleteVenditaPokemon(@PathVariable ("id") Integer id){
        return venditePokemonWrapperService.deleteVenditaPokemon(id);
    }

    //TODO: fare gemello x oggetto generico, junit , finire i todo e passare a orc
}
