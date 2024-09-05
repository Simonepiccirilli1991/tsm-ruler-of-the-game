package com.tsm.rule.reseller.reactivewrapper;

import com.tsm.rule.reseller.io.request.OggettiInteressantiRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
import com.tsm.rule.reseller.model.entity.OggettiInteressati;
import com.tsm.rule.reseller.model.internal.InteressantiUpdate;
import com.tsm.rule.reseller.service.OggettiInteressantiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;

@Service
@Slf4j
@RequiredArgsConstructor
public class InteressanteWrapperService {

    private final OggettiInteressantiService oggettiInteressantiService;
    private final ExecutorService executorService;

    // save
    public Mono<OggettiInteressati> saveOggettiInteressanti(OggettiInteressantiRequest request){
        log.info("WrapperInitialize for saveOggettiInteressanti");

        return Mono.fromCallable(() -> {
                    log.info("Starting subscribing for saveOggettiInteressanti");
                    return oggettiInteressantiService.saveOggettiInteressanti(request);
                })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wrapper for saveOggettiInteressanti ended successfully"))
                .onErrorResume(Mono::error);
    }
    // get
    public Mono<OggettiInteressati> getOggettiInteressanti(String nome) {
        log.info("WrapperInitialize for getOggettiInteressanti");

        return Mono.fromCallable(() -> {
                    log.info("Starting subscribing for getOggettiInteressanti");
                    return oggettiInteressantiService.getOggettiInteressanti(nome);
                })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wrapper for getOggettiInteressanti ended successfully"))
                .onErrorResume(Mono::error);
    }
    // delete
    public Mono<BaseResponse> deleteOggettiInteressanti(String nome){
        log.info("WrapperInitialize for deleteOggettiInteressanti");

        return Mono.fromCallable(() -> {
                    log.info("Starting subscribing for deleteOggettiInteressanti");
                    return oggettiInteressantiService.deleteOggettoInteressante(nome);
                })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wrapper for deleteOggettiInteressanti ended successfully"))
                .onErrorResume(Mono::error);
    }
    //update price
    public Mono<OggettiInteressati> updateOggettiInteressanti(String nome, InteressantiUpdate update){
        log.info("WrapperInitialize for updateOggettiInteressanti");

        return Mono.fromCallable(() -> {
                    log.info("Starting subscribing for updateOggettiInteressanti");
                    return oggettiInteressantiService.addUpdatesInteressante(nome,update);
                })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wrapper for updateOggettiInteressanti ended successfully"))
                .onErrorResume(Mono::error);
    }

}
