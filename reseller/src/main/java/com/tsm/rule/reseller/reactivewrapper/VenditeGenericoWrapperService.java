package com.tsm.rule.reseller.reactivewrapper;

import com.tsm.rule.reseller.exception.ResellerException;
import com.tsm.rule.reseller.io.request.VenditaGenericaRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
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

    // save
    public Mono<VenditaGenerica> saveVenditaGenerica(VenditaGenericaRequest request){
        log.info("Save vendita generica wrapper started");
        return Mono.fromCallable(() -> venditaGenericoService.saveVenditaGenerica(request))
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .onErrorResume(err -> {
                    log.error("Error on saveVendita with error: {}",err.getMessage());
                  return Mono.error(err);
                })
                .doOnSuccess(resp -> log.info("SaveVEnditaGenerica executor ended successfully"));
    }
    // get
    public Mono<VenditaGenerica> getVenditaGenerica(Integer idVendita){
        log.info("GetVenditaGenerica wrapper started");
        return Mono.fromCallable(() -> venditaGenericoService.getVenditaGenerica(idVendita))
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .onErrorResume(Mono::error)
                .doOnSuccess(resp -> log.info("GetVenditaGenerica wrapper ended successfully"));
    }
    // delete
    public Mono<BaseResponse> deleteVenditaGenerica(Integer idVendita){
        log.info("DeleteVEnditaGenerica wrapper started");
        return Mono.fromCallable(() -> venditaGenericoService.deleteVenditaGenerica(idVendita))
                .subscribeOn(Schedulers.fromExecutor(executorService))
                .onErrorResume(Mono::error)
                .doOnSuccess(resp -> log.info("DeleteVenditaGenerica wrapper ended successfully"));
    }
}
