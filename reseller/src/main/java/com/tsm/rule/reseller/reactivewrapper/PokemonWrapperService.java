package com.tsm.rule.reseller.reactivewrapper;

import com.tsm.rule.reseller.io.request.CartePokemonRequest;
import com.tsm.rule.reseller.io.request.FilteringPokemonRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
import com.tsm.rule.reseller.model.entity.CartePokemon;
import com.tsm.rule.reseller.service.CartePokemonService;
import com.tsm.rule.reseller.service.FilteringPokemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.ExecutorService;


@Service
@Slf4j
@RequiredArgsConstructor
public class PokemonWrapperService {

    private final CartePokemonService cartePokemonService;
    private final FilteringPokemonService filteringPokemonService;
    private final ExecutorService executorService;


    //save
    public Mono<CartePokemon> saveCartePokemon(CartePokemonRequest request){
        log.info("WrapperInitialize for saveCartePokemon");

        return Mono.fromCallable(() -> {
            log.info("Starting subcribing from callable x save pkm");
            return cartePokemonService.saveCartaPokemon(request);
        })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wrapper for saveCartePokemon ended Successfully"))
                .onErrorResume(err -> Mono.error(err));
    }
    //get
    public Mono<CartePokemon> getCartePokemon(String codiceOggetto){
        log.info("WrapperInitialize for getCartaPokemon");

        return Mono.fromCallable(() -> {
                    log.info("Starting subcribing  get from callable x get pkm");
                    return cartePokemonService.getCartaPokemonChiave(codiceOggetto);
                })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wrapper for getCartaPokemon ended Successfully"))
                .onErrorResume(err -> Mono.error(err));
    }
    // patch
    public Mono<CartePokemon> patchCartePokemon(CartePokemonRequest request,String chiaveOggetto){
        log.info("WrapperInitialize for patchCartePokemon");

        return Mono.fromCallable(() -> {
                    log.info("Starting subcribing from callable x patch pkm");
                    return cartePokemonService.patchCartePokemon(request,chiaveOggetto);
                })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wrapper for patchCartePokemon ended Successfully"))
                .onErrorResume(Mono::error);
    }
    // delete
    public Mono<BaseResponse> deleteCartePokemon(String chiaveOggetto){
        log.info("WrapperInitialize for deleteCartePokemon");

        return Mono.fromCallable(() -> {
                    log.info("Starting subcribing from callable x delete pkm");
                    return cartePokemonService.deleteCartaPokemon(chiaveOggetto);
                })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wrapper for deleteCartePokemon ended Successfully"))
                .onErrorResume(Mono::error);
    }

    // filtering acquisti pokemon
    public Mono<List<CartePokemon>> filteringCartePokemonAcquisti(FilteringPokemonRequest request){

        log.info("WrapperInitialize for filteringCartePokemon");
        return Mono.fromCallable(() -> {
            log.info("Starting subscribing from callable x filtering pkm");
            return filteringPokemonService.filteringCartePokemon(request);
        })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wreapper for filteringPokemon ended successfully"))
                .onErrorResume(Mono::error);
    }
}
