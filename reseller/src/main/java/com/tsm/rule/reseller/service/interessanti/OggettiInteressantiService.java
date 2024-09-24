package com.tsm.rule.reseller.service.interessanti;

import com.tsm.rule.reseller.exception.ResellerException;
import com.tsm.rule.reseller.io.request.OggettiInteressantiRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
import com.tsm.rule.reseller.model.entity.OggettiInteressati;
import com.tsm.rule.reseller.model.internal.InteressantiUpdate;
import com.tsm.rule.reseller.repo.OggettiInteressantiRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.tsm.rule.reseller.utils.ResellesUtils.mapperObject;

@Service
@Slf4j
@RequiredArgsConstructor
public class OggettiInteressantiService {

    private final OggettiInteressantiRepo oggettiInteressantiRepo;


    //save
    @Transactional
    public OggettiInteressati saveOggettiInteressanti(OggettiInteressantiRequest request){
        log.info("SaveOggettiInteressanti service started with raw request: {}");

        request.validateSave();

        var entity = (OggettiInteressati) mapperObject(request,OggettiInteressati.class);

        var resp = oggettiInteressantiRepo.save(entity);

        log.info("SaveOggettiInteressanti service ended successfully");
        return resp;
    }
    //update interessanti
    @Transactional
    public OggettiInteressati addUpdatesInteressante(String nome, InteressantiUpdate interessantiUpdate){
        log.info("AddUpdatesInteressante service started with raw request: {}, and nome: {}",interessantiUpdate,nome);
        // valido request
        interessantiUpdate.validateUpdate();
        // retrivo entity
        var entity = oggettiInteressantiRepo.findByNome(nome)
                .orElseThrow(() -> {
                    log.info("Error on AddUpdatesInteressante, Missing entity");
                    return new ResellerException("Error on AddUpdatesInteressante",null,"Missing entity");
                });
        // devo calcolare la percentuale di aumento o diminuzione rispetto l'ultimo
        var ultimoCosto = (entity.getUpdates().isEmpty()) ? entity.getPrezzoIniziale() : entity.getUpdates().getLast().getUltimoVenduto();
        // calcolo percentuale
        var percentuale = ((ultimoCosto - interessantiUpdate.getValore()) / interessantiUpdate.getValore()) * 100;
        // setto percentuale
        interessantiUpdate.setValoreAttualeComparato(String.valueOf(percentuale));
        //updato
        entity.getUpdates().add(interessantiUpdate);
        //salvo update
        var resp = oggettiInteressantiRepo.save(entity);

        log.info("AddUpdatesInteressante service ended successfully");
        return resp;
    }
    //get by nome
    @Transactional
    public OggettiInteressati getOggettiInteressanti(String nome){
        log.info("GetOggettiInteressanti service started for nome: {}",nome);

        var entity = oggettiInteressantiRepo.findByNome(nome)
                .orElseThrow(() -> {
                    log.error("Error on getOggettiInteressanti, missing entity");
                    return new ResellerException("Error on getOggettiInteressanti",null,"missing entity");
                });

        log.info("GetOggettiInteressanti service ended successuflly");
        return entity;
    }
    //delete
    @Transactional
    public BaseResponse deleteOggettoInteressante(String nome){

        log.info("DeleteOggettoInteressante service started with raw name: {}",nome);

        var entity = oggettiInteressantiRepo.findByNome(nome)
                .orElseThrow(() -> {
                    log.error("Error on DeleteOggettoInteressante, missing entity");
                    return new ResellerException("Error on DeleteOggettoInteressante",null,"Missing entity");
                });

        oggettiInteressantiRepo.delete(entity);
        log.info("DeleteOggettoInteressante service ended successfully");
        return new BaseResponse("Deleted successfully","00");
    }
}
