package com.tsm.rule.reseller.service.generici;

import com.tsm.rule.reseller.exception.ResellerException;
import com.tsm.rule.reseller.io.request.VenditaGenericaRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
import com.tsm.rule.reseller.model.entity.OggettoGenerico;
import com.tsm.rule.reseller.model.entity.VenditaGenerica;
import com.tsm.rule.reseller.repo.OggettoGenericoRepo;
import com.tsm.rule.reseller.repo.VenditaGenericaRepo;
import com.tsm.rule.reseller.utils.ResellesUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.StructuredTaskScope;

@Service
@Slf4j
@RequiredArgsConstructor
public class VenditaGenericoService {

    private final OggettoGenericoRepo oggettoGenericoRepo;
    private final VenditaGenericaRepo venditaGenericaRepo;


    @Transactional
    public VenditaGenerica saveVenditaGenerica(VenditaGenericaRequest request){
        log.info("SaveVenditaGenerica service started with raw request: {}",request);
        //retrivo acquisto generico
        var acquisto = oggettoGenericoRepo.findByChiaveOggetto(request.chiaveAcquisto())
                .orElseThrow(() -> {
                    log.error("Error on saveVenditaGenerica, missing entity acquisto generico");
                    return new ResellerException("Error on saveVenditaGenerica",null,"Missing acquisto generico");
                });
        // se quantita non disponibile stoppo
        if(request.quantita() > acquisto.getQuantitaDisponibile()){
            log.error("Error on saveVEnditaGenerica, no quantity aviable");
            throw new ResellerException("Error on saveVenditaGenerica",null,"Quantity not aviable");
        }
        // mappo request su entity
        var vendita =(VenditaGenerica) ResellesUtils.mapperObject(request, VenditaGenerica.class);
        //calcolare netto totale e singolo
        var nettoTotale = (ObjectUtils.isEmpty(request.spese())) ? request.entrataTotale() : (request.entrataTotale() - request.spese());
        var nettoSingolo = (ObjectUtils.isEmpty(request.quantita())) ? nettoTotale : (nettoTotale / request.quantita());
        // salvo netto toale e singolo
        vendita.setNettoTotale(nettoTotale);
        vendita.setEntrataSingola(nettoSingolo);
        // updato l'aviable del acquisto
        acquisto.setQuantitaDisponibile((acquisto.getQuantitaDisponibile()) - request.quantita());
        //salvo la vendigta e l'update dell'acquisto
        try(var scope = new StructuredTaskScope.ShutdownOnFailure()){
            // updato acquisto
            var taskUpdate = scope.fork(() -> oggettoGenericoRepo.save(acquisto));
            // salvo vendita
            var taskSave = scope.fork(() -> venditaGenericaRepo.save(vendita));
            scope.join().throwIfFailed();
            var resp = taskSave.get();
            log.info("Save VenditaOggettoGenerico service ended successfully");
            return resp;
        }catch (Exception e){
            log.error("Error on saveVEnditaOggettoGenerico with error: {}",e.getMessage());
            throw new ResellerException("Error on saveVeneditaGenerico",e,e.getMessage());
        }
    }


    public VenditaGenerica getVenditaGenerica(Integer idVenditaGen){
        log.info("GetVenditaGenerica service started");
        var vendita = venditaGenericaRepo.findById(idVenditaGen)
                .orElseThrow(() -> {
                    log.error("Error on GetVenditaGenerica service, missing vendita");
                    return new ResellerException("Error on GEtVenditaGenerica",null,"Missing vendita generica");
                });
        log.info("GetVenditaGenerica service ended successfully");
        return vendita;
    }

    public BaseResponse deleteVenditaGenerica(Integer idVenditaGen){
        log.info("DeleteVenditaGenerica service started");
        // retrivo vendita
        var vendita = venditaGenericaRepo.findById(idVenditaGen)
                .orElseThrow(() -> {
                    log.error("Error on deleteVenditaGenerica service, missing vendita");
                    return new ResellerException("Error on deleteVenditaGenerica",null,"Missing vendita generica");
                });
        // retrivo acquisto con key vendita
        var acquisto = oggettoGenericoRepo.findByChiaveOggetto(vendita.getChiaveAcquisto())
                .orElseThrow(() ->{
                    log.error("Error on deleteVenditaGenerica service, missing acquisto");
                    return new ResellerException("Error on deleteVenditaGenerica",null,"Missing acquisto generica");
                });
        // restoro la quantita
        acquisto.setQuantitaDisponibile((acquisto.getQuantitaDisponibile()) + vendita.getQuantita());
        //updato e delite con subtask
        try(var scope = new StructuredTaskScope.ShutdownOnFailure()){
            // uodato
            scope.fork(() -> oggettoGenericoRepo.save(acquisto));
            //deleto
            var deleteTask = scope.fork(() -> deleteVendita(vendita));
            // joino
            scope.join().throwIfFailed();
            //torno resp
            var resp = deleteTask.get();
            log.info("DeleteVenditaGenerica service ended successfully");
            return resp;
        }catch (Exception e){
            log.error("Error on deleteVenditaGenerica with errir: {}",e.getMessage());
            throw new ResellerException("Error on deleteVenditaGenerica",e,e.getMessage());
        }
    }

    private BaseResponse deleteVendita(VenditaGenerica venditaGen){
        venditaGenericaRepo.delete(venditaGen);
        return new BaseResponse("Deleted successuflly","00");
    }
}
