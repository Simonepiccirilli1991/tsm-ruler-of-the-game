package com.tsm.rule.reseller.reactivewrapper;

import com.tsm.rule.reseller.io.request.VenditaPokemonRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
import com.tsm.rule.reseller.model.entity.VenditaPokemon;
import com.tsm.rule.reseller.service.generici.VenditaGenericoService;
import com.tsm.rule.reseller.service.pokemon.VenditaPokemonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;

@Service
@Slf4j
@RequiredArgsConstructor
public class VenditePokemonWrapperService {


    private final VenditaPokemonService venditaPokemonService;
    private final VenditaGenericoService venditaGenericoService;
    private final ExecutorService executorService;


    //save vendita pokemon
    public Mono<VenditaPokemon> saveVenditaPokemon(VenditaPokemonRequest request){
        log.info("WrapperInitialize for saveVenditaPokemon");

        return Mono.fromCallable(() -> {
                    log.info("Starting subcribing from callable x save pkm vendita");
                    return venditaPokemonService.saveVenditaPokemon(request);
                })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wrapper for saveVenditaPokemon ended Successfully"))
                .onErrorResume(err -> Mono.error(err));
    }
    // get vendita pokemon
    public Mono<VenditaPokemon> getVenditaPokemon(Integer id){
        log.info("WrapperInitialize for getVenditaPokemon");

        return Mono.fromCallable(() -> {
                    log.info("Starting subcribing from callable x get pkm vendita");
                    return venditaPokemonService.getVendita(id);
                })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wrapper for getVenditaPokemon ended Successfully"))
                .onErrorResume(err -> Mono.error(err));
    }
    // delete vendita pokemon
    public Mono<BaseResponse> deleteVenditaPokemon(Integer id){
        log.info("WrapperInitialize for deleteVenditaPokemon");

        return Mono.fromCallable(() -> {
                    log.info("Starting subcribing from callable x delete pkm vendita");
                    return venditaPokemonService.deleteVenditaPokemon(id);
                })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wrapper for deleteVenditaPokemon ended Successfully"))
                .onErrorResume(err -> Mono.error(err));
    }

}
