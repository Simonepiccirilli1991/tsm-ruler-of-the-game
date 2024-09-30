package com.tsm.rule.reseller.reactivewrapper;

import com.tsm.rule.reseller.exception.ResellerException;
import com.tsm.rule.reseller.io.request.VenditaGenericaRequest;
import com.tsm.rule.reseller.model.entity.VenditaGenerica;
import com.tsm.rule.reseller.service.generici.VenditaGenericoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.ExecutorService;

@Service
@Slf4j
@RequiredArgsConstructor
public class VenditeGenericoWrapperService {


    private final VenditaGenericoService venditaGenericoService;
    private final ExecutorService executorService;


    public Mono<VenditaGenerica> saveVenditaGenerica(VenditaGenericaRequest request){

        return Mono.fromCallable(() -> venditaGenericoService.saveVenditaGenerica(request))
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .onErrorMap(err -> {
                    log.error("Error on saveVendita with error: {}",err.getMessage());
                    throw new ResellerException("Error on SaveVenditaOggetto",err,err.getMessage());
                })
                .doOnSuccess(resp -> log.info("SaveVEnditaGenerica executor ended successfully"));
    }
}
