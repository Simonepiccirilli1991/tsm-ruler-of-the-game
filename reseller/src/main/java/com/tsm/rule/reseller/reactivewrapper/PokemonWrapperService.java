package com.tsm.rule.reseller.reactivewrapper;

import com.tsm.rule.reseller.io.request.CartePokemonRequest;
import com.tsm.rule.reseller.model.entity.CartePokemon;
import com.tsm.rule.reseller.service.CartePokemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;


@Service
@Slf4j
@RequiredArgsConstructor
public class PokemonWrapperService {

    private final CartePokemonService cartePokemonService;
    private final ExecutorService executorService;
    //save
    public Mono<CartePokemon> saveCartePokemon(CartePokemonRequest request){
        log.info("WrapperInitialize for saveCartePokemon");

        return Mono.fromCallable(() -> {
            log.info("Starting subcribing from callable");
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
                    log.info("Starting subcribing  get from callable");
                    return cartePokemonService.getCartaPokemonChiave(codiceOggetto);
                })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wrapper for getCartaPokemon ended Successfully"))
                .onErrorResume(err -> Mono.error(err));
    }
}
