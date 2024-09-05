package com.tsm.rule.reseller.service;

import com.tsm.rule.reseller.exception.ResellerException;
import com.tsm.rule.reseller.io.request.OggettiGenericiRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
import com.tsm.rule.reseller.model.entity.OggettoGenerico;
import com.tsm.rule.reseller.repo.OggettoGenericoRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

import static com.tsm.rule.reseller.utils.ResellesUtils.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class OggettiGenericiServici {

    private final OggettoGenericoRepo oggettoGenericoRepo;

    // save
    @Transactional
    public OggettoGenerico salvaOggettoGenerico(OggettiGenericiRequest request){
        log.info("SavaOggettoGenerico service started with raw request: {}",request);

        request.validateSave();

        var entity =(OggettoGenerico) mapperObject(request,OggettoGenerico.class);
        //TODO: non so se il mapper mappa in automatico anche gli enum, controllare

        // setto chiave oggetto
        var chiave = generaChiaveOggetto(request.nome(), request.data().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        entity.setChiaveOggetto(chiave);

        var resp = oggettoGenericoRepo.save(entity);
        log.info("SavaOggettoGenerico service ended successfully");
        return resp;
    }
    // get
    @Transactional
    public OggettoGenerico getOggettoGenerico(String chiaveOggetto){
        log.info("GetOggettoGenerico service started with raw chiave: {}",chiaveOggetto);

        var entity = oggettoGenericoRepo.findByChiaveOggetto(chiaveOggetto)
                .orElseThrow(() -> {
                    log.error("Error on GetOggettoGenerico, missing entity");
                    return new ResellerException("Error on GetOggettoGenerico",null,"Missing entity");
                });

        log.info("GetOggettoGenerico service ended successfully");
        return entity;
    }
    // patch
    @Transactional
    public OggettoGenerico patchOggettoGenerico(String chiaveOggetto, OggettiGenericiRequest request){
        log.info("PatchOggettoGenerico service started with raw request: {}",request);

        var entity = oggettoGenericoRepo.findByChiaveOggetto(chiaveOggetto)
                .orElseThrow(() -> {
                    log.info("Error on PatchOggettoGenerico, missing entity");
                    return new ResellerException("Error on PatchOggettoGenerico",null,"Missing entity");
                });
        // updato entity
        updateOggettiGenerici(request,entity);
        // salvo entity updata
        var resp = oggettoGenericoRepo.save(entity);

        log.info("PatchOggettoGenerico service ended successfully");
        return resp;
    }
    //delete
    @Transactional
    public BaseResponse deleteOggettoGenerico(String chiaveOggetto){
        log.info("DeleteOggettoGenerico service started with chiave: {}",chiaveOggetto);

        var entity = oggettoGenericoRepo.findByChiaveOggetto(chiaveOggetto)
                .orElseThrow(() -> {
                    log.error("Error on DeleteOggettoGenerico, missing entity");
                    return new ResellerException("Error on DeleteOggettoGenerico",null,"Missing entity");
                });

        oggettoGenericoRepo.delete(entity);

        log.info("DeleteOggettoGenerico service ended successfully");
        return new BaseResponse("Deleted successfully","00");
    }
}
