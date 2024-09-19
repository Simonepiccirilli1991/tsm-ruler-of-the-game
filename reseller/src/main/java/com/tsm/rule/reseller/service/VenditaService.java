package com.tsm.rule.reseller.service;

import com.tsm.rule.reseller.exception.ResellerException;
import com.tsm.rule.reseller.io.request.VenditaRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
import com.tsm.rule.reseller.model.base.AcquistoBase;
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
                        log.error("Error on salvaVendita service , missing entity on db: {}, with this chiave: {}",request.tipoOggetto(),request.chiaveAcquisto());
                        return new ResellerException("Error on salvaVendita service pkm",null,"Missing entity pkm");
                    });
        else
            acqusito =  oggettoGenericoRepo.findByChiaveOggetto(request.chiaveAcquisto())
                    .orElseThrow(() -> {
                        log.error("Error on salvaVendita service , missing entity on db: {}, with this chiave: {}",request.tipoOggetto(),request.chiaveAcquisto());
                        return new ResellerException("Error on salvaVendita service generico",null,"Missing entity generico");
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
                    return new ResellerException("Error on salvaVendita service pkm",null,"Missing entity");
                });
                if(ObjectUtils.isEmpty(entity.getNome()))
                    entity.setNome(acq.getNome());
            }
            else{
                var task2 = scope.fork(() -> oggettoGenericoRepo.findByChiaveOggetto(request.chiaveAcquisto()));
                var acq = task2.get().orElseThrow(() -> {
                    log.error("Error on salvaVendita service, missing entity on db: {}, with this chiave: {}",request.tipoOggetto(),request.chiaveAcquisto());
                    return new ResellerException("Error on salvaVendita service generico",null,"Missing entity");
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
    @Transactional
    public BaseResponse deleteVendita(Integer idVendita){
        log.info("DeleteVendita service started for idVendita: {}",idVendita);
        // retrivo vendita
        var vendita = venditaRepo.findById(idVendita).orElseThrow(() -> {
            log.error("Error on DeleteVendita service, missing entity on db: ");
            return new ResellerException("Error on DeleteVendita service find",null,"Missing vendita");
        });
        Object acquisto = null;
        if(OggettiTrattati.POKEMON.toString().equals(vendita.getTipoOggetto()))
            acquisto = cartaPokemonRepo.findByChiaveOggetto(vendita.getChiaveAcquisto())
                    .orElseThrow(() -> {
                        log.error("Error on deleteVendita service, missing entity acquisto pokemon");
                        return new ResellerException("Error on deleteVendita service",null,"Missing acqusto pokemon");
                    });
        else
            acquisto =  oggettoGenericoRepo.findByChiaveOggetto(vendita.getChiaveAcquisto())
                    .orElseThrow(() -> {
                        log.error("Error on deleteVendita service, missing entity acquisto generico");
                        return new ResellerException("Error on deleteVendita service",null,"Missing acqusto generico");
                    });
        // risetto quantia originale
        var quantitaRollback = ((AcquistoBase) acquisto).getQuantitaDisponibile() + vendita.getQuantita();
        // setto
        ((AcquistoBase) acquisto).setQuantitaDisponibile(quantitaRollback);
        try(var scope = new StructuredTaskScope.ShutdownOnFailure()){
        if(OggettiTrattati.POKEMON.toString().equals(vendita.getTipoOggetto())) {
            var finalAcquisto = (CartePokemon) acquisto;
            scope.fork(() ->cartaPokemonRepo.save(finalAcquisto));
        }
        else {
            var finalAcquisto = (OggettoGenerico) acquisto;
            scope.fork(() -> oggettoGenericoRepo.save(finalAcquisto));
        }
        // chiamo metodo che torna oggetto se no si incazza malamente, scoprire perche, ma contando che euna previuw
        var finalTask = scope.fork(() -> deleteVenditaCall(vendita));
        // joino e twowo se faila
        scope.join().throwIfFailed();
        // prendo resp, loggo e torno
        var resp = finalTask.get();
        log.info("DeleteVendita service ended successfully");
        return resp;
        }catch (InterruptedException e){
            log.error("Error on DeleteVendita service, with err: {}",e.getMessage());
            throw new ResellerException("Error on DeleteVendita service",e,e.getMessage());
        }
        catch (ExecutionException e){
            log.error("Error on DeleteVendita service with error: {}",e.getMessage());
            throw new ResellerException("Error on DeleteVendita service",e,e.getMessage());
        }
    }

    public Vendita getVenditaById(Integer id){
        log.info("GetVenditaById service started");

        var vendita = venditaRepo.findById(id).orElseThrow(() -> {
            log.error("Error on getVenditaById service, missing entity on db: ");
            return new ResellerException("Error on getVenditaById service",null,"Missing vendita");
        });

        log.info("GetVenditaById ended Successfully");
        return vendita;
    }

    // ho dovuto mettere sta cosa qua perche deve tornare per foza un cazzo di oggetto se uso scope
    private BaseResponse deleteVenditaCall(Vendita vendita){
        venditaRepo.delete(vendita);
        return new BaseResponse("Deleted successfully","00");
    }


    //TODO: fare test dei junit di sta roba se funziona tutta la parte vendita
}
