package com.tsm.rule.reseller.service;

import com.tsm.rule.reseller.exception.ResellerException;
import com.tsm.rule.reseller.io.request.VenditaRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
import com.tsm.rule.reseller.model.entity.CartePokemon;
import com.tsm.rule.reseller.model.entity.OggettoGenerico;
import com.tsm.rule.reseller.model.entity.Vendita;
import com.tsm.rule.reseller.repo.CartaPokemonRepo;
import com.tsm.rule.reseller.repo.OggettoGenericoRepo;
import com.tsm.rule.reseller.repo.VenditaRepo;
import com.tsm.rule.reseller.utils.OggettiTrattati;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructureViolationException;
import java.util.concurrent.StructuredTaskScope;

@Service
@Slf4j
@RequiredArgsConstructor
public class VenditaService {

    private final VenditaRepo venditaRepo;
    private final CartaPokemonRepo cartaPokemonRepo;
    private final OggettoGenericoRepo oggettoGenericoRepo;
    // classica
    @Transactional
    public Vendita salvaVendita(VenditaRequest request){
        log.info("SalvaVendita service started with raw request: {}",request);
        // valido request
        request.validateSave();
        Object acqusito = null;
        // devo capire dove retrivare l'entity di acquisto in base al tipo prodotto
        if(OggettiTrattati.POKEMON.equals(request.tipoOggetto()))
            acqusito = cartaPokemonRepo.findByChiaveOggetto(request.chiaveAcquisto())
                    .orElseThrow(() -> {
                        log.error("Error on salvaVendita service, missing entity on db: {}, with this chiave: {}",request.tipoOggetto(),request.chiaveAcquisto());
                        return new ResellerException("Error on salvaVendita service",null,"Missing entity");
                    });
        else
            acqusito =  oggettoGenericoRepo.findByChiaveOggetto(request.chiaveAcquisto())
                    .orElseThrow(() -> {
                        log.error("Error on salvaVendita service, missing entity on db: {}, with this chiave: {}",request.tipoOggetto(),request.chiaveAcquisto());
                        return new ResellerException("Error on salvaVendita service",null,"Missing entity");
                    });
        var entity = new Vendita();
        var nome = "";
        // vedo che tipo di istance è x fare cast
        if(acqusito instanceof CartePokemon) {
            entity.setBrandAssociato("Pokemon");
            nome = (ObjectUtils.isEmpty(request.nome())) ? "Oggetto Pokemon" : request.nome();
        }
        else{
            var ogg = (OggettoGenerico) acqusito;
            entity.setBrandAssociato(ogg.getBrandAssociato());
            nome = (ObjectUtils.isEmpty(request.nome())) ? ogg.getNome() : request.nome();
        }
        // setto data
        entity.setData(request.data().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        entity.setNome(nome);
        entity.setTipoOggetto(request.tipoOggetto().toString());
        entity.setVendutoSu(request.vendutoSu());
        entity.setQuantita(request.quantita());
        entity.setEntrataTotale(request.entrataTotale());
        //se ci sono delle spese le sottrago al totale e calcolo totale netto
        var totaleNetto = (ObjectUtils.isEmpty(request.spese())) ? request.entrataTotale() : request.entrataTotale() - request.spese();
        // divido il totale netto per la quantita
        if(1 == request.quantita())
            entity.setEntrataSingola(totaleNetto);
        else// altrimenti divito totale x quantita
            entity.setEntrataSingola((totaleNetto / request.quantita()));
        // setto netto totale
        entity.setNettoTotale(totaleNetto);
        // se ci sono spese le setto
        if(!ObjectUtils.isEmpty(request.spese()))
            entity.setSpese(request.spese());
        // salvo entity
        var resp = venditaRepo.save(entity);
        log.info("SaveVendita service ended successfully");
        return resp;
    }
    // con subtread
    @Transactional
    public Vendita subTaskSaveVendita(VenditaRequest request){
        log.info("SubTaskSaveVendita service started");
        request.validateSave();

        var entity = new Vendita();
        // setto data
        entity.setData(request.data().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        entity.setNome(request.nome());
        entity.setTipoOggetto(request.tipoOggetto().toString());
        entity.setVendutoSu(request.vendutoSu());
        entity.setQuantita(request.quantita());
        entity.setEntrataTotale(request.entrataTotale());
        entity.setChiaveAcquisto(request.chiaveAcquisto());
        //se ci sono delle spese le sottrago al totale e calcolo totale netto
        var totaleNetto = (ObjectUtils.isEmpty(request.spese())) ? request.entrataTotale() : request.entrataTotale() - request.spese();
        // divido il totale netto per la quantita
        if(1 == request.quantita())
            entity.setEntrataSingola(totaleNetto);
        else// altrimenti divito totale x quantita
            entity.setEntrataSingola((totaleNetto / request.quantita()));
        // setto netto totale
        entity.setNettoTotale(totaleNetto);
        // se ci sono spese le setto
        if(!ObjectUtils.isEmpty(request.spese()))
            entity.setSpese(request.spese());
        // feature sub task on thread
        try(var scope = new StructuredTaskScope.ShutdownOnFailure()){
            if(OggettiTrattati.POKEMON.equals(request.tipoOggetto())){
                var task1 = scope.fork(() -> cartaPokemonRepo.findByChiaveOggetto(request.chiaveAcquisto()));
                var acq = task1.get().orElseThrow(() -> {
                    log.error("Error on salvaVendita service, missing entity on db: {}, with this chiave: {}",request.tipoOggetto(),request.chiaveAcquisto());
                    return new ResellerException("Error on salvaVendita service",null,"Missing entity");
                });
                if(ObjectUtils.isEmpty(entity.getNome()))
                    entity.setNome(acq.getNome());
            }
            else{
                var task2 = scope.fork(() -> oggettoGenericoRepo.findByChiaveOggetto(request.chiaveAcquisto()));
                var acq = task2.get().orElseThrow(() -> {
                    log.error("Error on salvaVendita service, missing entity on db: {}, with this chiave: {}",request.tipoOggetto(),request.chiaveAcquisto());
                    return new ResellerException("Error on salvaVendita service",null,"Missing entity");
                });

                if(ObjectUtils.isEmpty(entity.getNome()))
                    entity.setNome(acq.getNome());
            }
            // salvo entity
            var saveTask = scope.fork(() ->venditaRepo.save(entity));
            // joino i vari subtask x vedere se c'e da lanciare eccezzione
            scope.join().throwIfFailed();
            var resp = saveTask.get();
            log.info("SaveVendita service ended successfully");
            return resp;

        }catch (InterruptedException e){
            log.error("Error on salvaVendita service, with err: {}",e.getMessage());
            throw new ResellerException("Error on salvaVendita service",e,e.getMessage());
        }
        catch (ExecutionException e){
            log.error("Error on salvaVendita service with error: {}",e.getMessage());
            throw new ResellerException("Error on salvaVendita service",e,e.getMessage());
        }
    }

    // una vendita non si puo modificare, ma solo cancellare e ricreare
    public BaseResponse deleteVendita(Integer idVendita){
        log.info("DeleteVendita service started for idVendita: {}",idVendita);
    }
}
