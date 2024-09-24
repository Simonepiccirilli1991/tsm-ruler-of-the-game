package com.tsm.rule.reseller.service.pokemon;

import com.tsm.rule.reseller.exception.ResellerException;
import com.tsm.rule.reseller.io.request.VenditaPokemonRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
import com.tsm.rule.reseller.model.entity.VenditaPokemon;
import com.tsm.rule.reseller.repo.CartaPokemonRepo;
import com.tsm.rule.reseller.repo.VenditaPokemonRepo;
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
public class VenditaPokemonService {


    private final VenditaPokemonRepo venditaPokemonRepo;
    private final CartaPokemonRepo cartaPokemonRepo;

    // salvo vendita
    @Transactional
    public VenditaPokemon saveVenditaPokemon(VenditaPokemonRequest request){
        log.info("SaveVenditaPokemon service started with raw request: {}",request);
        // retrivo acquisto
        var acquisto = cartaPokemonRepo.findByChiaveOggetto(request.chiaveAcquisto())
                .orElseThrow(() -> {
                    log.error("Error on saveVenditaPokemon service, missing acquisto with this key");
                    return new ResellerException("Error on saveVenditaPokemon",null,"Missing entity acquisto");
                });
        // se quantita disponibile e minore di vendita eccezzione
        if(acquisto.getQuantitaDisponibile() < request.quantita()){
            log.error("Error on saveVenditaPokemon service, quantity not avieable");
            throw new ResellerException("Error on saveVenditaPokemon Service",null,"Quantity not aviable");
        }
        // mappo request su entity
        var vendita = (VenditaPokemon) ResellesUtils.mapperObject(request,VenditaPokemon.class);
        // calcolo totale e scalo quantita disponibile
        var totaleNetto = (ObjectUtils.isEmpty(request.spese())) ? request.entrataTotale() : (request.entrataTotale() - request.spese());
        var entrataSingola = (request.quantita() > 1) ? (totaleNetto / request.quantita()) : totaleNetto;
        vendita.setNettoTotale(totaleNetto);
        vendita.setEntrataSingola(entrataSingola);
        // sccalo quantita disponibile
        var quantitaDisponibile = acquisto.getQuantitaDisponibile() - request.quantita();
        acquisto.setQuantitaDisponibile(quantitaDisponibile);
        // salvo il tutto su fork thread
        try(var scope = new StructuredTaskScope.ShutdownOnFailure()){
            // udpato acquisto
            scope.fork(() -> cartaPokemonRepo.save(acquisto));
            // salvo vendita
            var taskVendita = scope.fork(() -> venditaPokemonRepo.save(vendita));
            //njoino fork
            scope.join().throwIfFailed();
            // torno
            log.info("SaveVenditaPokemon service ended successfully");
            return taskVendita.get();
        }catch (Exception e){
            log.error("Error on saveVEnditaPokemon service with err: {}",e.getMessage());
            throw new ResellerException("Error on saveVenditaPokemon service",e, e.getMessage());
        }
    }
    // get classica
    public VenditaPokemon getVendita(Integer id){
        log.info("GetVEnditaPokemon service started");
        var vendita = venditaPokemonRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Error on getVenditaPokemon service, missing vendita");
                    return new ResellerException("Error on getVenditaPokemon service",null,"Missing entity");
                });
        log.info("GetVenditaPokemon service ended successfully");
        return vendita;
    }
    // delete con rollback
    @Transactional
    public BaseResponse deleteVenditaPokemon(Integer id){
        log.info("DeleteVenditaPokemon service started");
        var vendita = venditaPokemonRepo.findById(id)
                .orElseThrow(() -> {
                    log.error("Error on DeleteVenditaPokemon service, missing vendita");
                    return new ResellerException("Error on DeleteVenditaPokemon service",null,"Missing entity vendita");
                });
        // retrivo acquisto x restorare la quantita disponibile
        var acquisto = cartaPokemonRepo.findByChiaveOggetto(vendita.getChiaveAcquisto())
                .orElseThrow(() -> {
                    log.error("Error on DeleteVenditaPokemon service, missing acquisto");
                    return new ResellerException("Error on DeleteVenditaPokemon service",null,"Missing entity acquisto");
                });
        // calcolo e setto quantita disponibile restorata
        var quantitaDisponibile = acquisto.getQuantitaDisponibile() + vendita.getQuantita();
        acquisto.setQuantitaDisponibile(quantitaDisponibile);
        // e ora di forkare x salvare e deletare
        try(var scope = new StructuredTaskScope.ShutdownOnFailure()){
            //deleto vendita
            scope.fork(() -> {
                venditaPokemonRepo.delete(vendita);
                return null;
            });
            // updato acquisto x restore
            scope.fork(() ->cartaPokemonRepo.save(acquisto));
            // re joino scope
            scope.join().throwIfFailed();
            var resp = new BaseResponse("Deleted successfully","00");
            log.info("DeleteVenditaPokemon service ended successfully");
            return resp;

        }catch (Exception e){
            log.error("Error on DeleteVenditaPokemon service with err: {}",e.getMessage());
            throw new ResellerException("Error on DeleteVenditaPokemon service",e, e.getMessage());
        }
    }
}
