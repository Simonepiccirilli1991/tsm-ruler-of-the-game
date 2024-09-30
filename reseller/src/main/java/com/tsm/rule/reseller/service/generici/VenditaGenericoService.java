package com.tsm.rule.reseller.service.generici;

import com.tsm.rule.reseller.exception.ResellerException;
import com.tsm.rule.reseller.io.request.VenditaGenericaRequest;
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
}
    //TODO: INSERIRE GET E DELETE POI TEST