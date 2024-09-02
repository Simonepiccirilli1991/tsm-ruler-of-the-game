package com.tsm.rule.reseller.service;

import com.tsm.rule.reseller.exception.ResellerException;
import com.tsm.rule.reseller.io.request.CartePokemonRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
import com.tsm.rule.reseller.model.entity.CartePokemon;
import com.tsm.rule.reseller.repo.CartaPokemonRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;

import static com.tsm.rule.reseller.utils.ResellesUtils.generaChiaveOggetto;
import static com.tsm.rule.reseller.utils.ResellesUtils.updateCartePokemonEntity;

@Service
@Slf4j
@RequiredArgsConstructor
public class CartePokemonService {

    private final CartaPokemonRepo cartaPokemonRepo;

    @Transactional
    public CartePokemon saveCartaPokemon(CartePokemonRequest request){
        log.info("Save cartaPokemon service started with raw request: {}",request);
        request.validateSave();
        // monto entity
        var entity = new CartePokemon();
        entity.setData(request.data().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        entity.setNome(request.nome());
        entity.setCodice(request.codice()); // codice carta o codice a barre prodtto
        // controllo se e sealed
        var sealed = Boolean.TRUE.equals(request.sealed());
        entity.setSealed(sealed);
        //controllo se e gradata
        var graded = Boolean.TRUE.equals(request.graded());
        entity.setGraded(graded);
        if(Boolean.TRUE.equals(graded))
            entity.setGradeValue(request.gradedValue());
        // setto quantita
        var quantita = ObjectUtils.isEmpty(request.quantita()) || request.quantita() == 0 ? 1 : request.quantita();
        entity.setQuantita(quantita);
        entity.setQuantitaDisponibile(quantita);
        // setto costo totale e singolo
        entity.setCostoTotale(request.costoTotale());
        entity.setCostoSingolo((entity.getCostoTotale() / quantita));
        // provenienza e tipoProdtto
        entity.setAcquistatoPresso(request.acquistatoPresso());
        entity.setTipoProdotto(request.tipoProdotto().name());
        entity.setChiaveOggetto(generaChiaveOggetto(entity.getNome(),entity.getData()));
        // salvo entity
        var resp = cartaPokemonRepo.save(entity);
        log.info("Save cartaPokemon service ended successfully");
        return resp;
    }
    //get
    @Transactional
    public CartePokemon getCartaPokemonChiave(String chiaveOggetto){
        log.info("GetCartaPokemon con chiave started with chiaveOggetto: {}",chiaveOggetto);
        var entity = cartaPokemonRepo.findByChiaveOggetto(chiaveOggetto)
                .orElseThrow(() -> {
                    log.error("Error on getCartaPokemonService, missing entity");
                    return new ResellerException("Error on getCartaPokemon",null,"Missing entity");
                });

        log.info("GetCartaPokemon service ended successfully");
        return entity;
    }
    //patch
    @Transactional
    public CartePokemon patchCartePokemon(CartePokemonRequest request,String chiaveOggetto){

        log.info("PatchCartePokemon service started with raw request: {} , and chiave: {}",request,chiaveOggetto);

        request.validatePatch();

        var entity = cartaPokemonRepo.findByChiaveOggetto(chiaveOggetto)
                .orElseThrow(() -> {
                    log.error("Errror on PatchCartePokemonService , missing entity");
                    return new ResellerException("Error on patch cartePokemon",null,"Missing entity");
                });

        var originalCosto = entity.getCostoTotale();
        var quantitaOriginale = entity.getQuantita();
        //updato entity
        updateCartePokemonEntity(request,entity);
        // se e cambiata la quantita o il totale devo aggiornate il costo singolo
        if(originalCosto != entity.getCostoTotale() || quantitaOriginale != entity.getQuantita())
            entity.setCostoSingolo((entity.getCostoTotale() / entity.getQuantita()));

        //NOTA BENE, per il momento non  tocco il disponibile se modifico quantita
        var resp = cartaPokemonRepo.save(entity);
        log.info("PatchCartaPokemonService ended successfully");
        return resp;
    }
    //delete
    @Transactional
    public BaseResponse deleteCartaPokemon(String chiaveOggetto){
        log.info("DeleteCartaPokemon service started for chiave: {}",chiaveOggetto);

        var entity = cartaPokemonRepo.findByChiaveOggetto(chiaveOggetto)
                .orElseThrow(() -> {
                    log.error("Error on deleteCartaPokemon, missing entity");
                    return new ResellerException("Error on deleteCartaPokemon service",null,"Missing entity");
                });

        cartaPokemonRepo.delete(entity);
        log.info("DeleteCartaPokemon service ended successfully");
        return new BaseResponse("Deleted successfully","00");
    }
}
