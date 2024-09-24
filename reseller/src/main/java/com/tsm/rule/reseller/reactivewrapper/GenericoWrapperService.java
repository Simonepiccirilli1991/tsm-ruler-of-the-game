package com.tsm.rule.reseller.reactivewrapper;

import com.tsm.rule.reseller.io.request.OggettiGenericiRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
import com.tsm.rule.reseller.model.entity.OggettoGenerico;
import com.tsm.rule.reseller.service.generici.OggettiGenericiServici;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenericoWrapperService {


    private final OggettiGenericiServici oggettiGenericiServici;
    private final ExecutorService executorService;

    // save
    public Mono<OggettoGenerico> saveOggettoGenerico(OggettiGenericiRequest request){
        log.info("WrapperInitialize for saveOggettoGenerico");

        return Mono.fromCallable(() -> {
                    log.info("Starting subcribing from callable x save oggettoGenerico");
                    return oggettiGenericiServici.salvaOggettoGenerico(request);
                })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wrapper for saveOggettoGenerico ended Successfully"))
                .onErrorResume(err -> Mono.error(err));
    }
    // get
    public Mono<OggettoGenerico> getOggettoGenerico(String chiave){
        log.info("WrapperInitialize for getOggettoGenerico");

        return Mono.fromCallable(() -> {
            log.info("Starting subscribing for getOggettoGenerico");
            return oggettiGenericiServici.getOggettoGenerico(chiave);
        })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wrapper for getOggettoGenerico ended successfully"))
                .onErrorResume(Mono::error);
    }
    // patch
    public Mono<OggettoGenerico> patchOggettoGenerico(String chiave, OggettiGenericiRequest request){
        log.info("WrapperInitialize for patchOggettoGenerico");

        return Mono.fromCallable(() -> {
            log.info("Starting subscribing for patchOggettoGenerico");
            return oggettiGenericiServici.patchOggettoGenerico(chiave,request);
        })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wrapper for patchOggettoGenerico ended successfully"))
                .onErrorResume(Mono::error);
    }
    // delete
    public Mono<BaseResponse> deleteOggettoGenerico(String chiave){
        log.info("WrapperInitialize for deleteOggettoGenerico");

        return Mono.fromCallable(() -> {
            log.info("Starting subscribing for deleteOggettoGenerico");
            return oggettiGenericiServici.deleteOggettoGenerico(chiave);
        })
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .doOnSuccess(resp -> log.info("Wrapper for deleteOggettoGenerico ended successfully"))
                .onErrorResume(Mono::error);
    }
}
