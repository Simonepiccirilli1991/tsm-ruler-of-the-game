package com.tsm.rule.reseller.io.request;

import com.tsm.rule.reseller.exception.ResellerException;
import com.tsm.rule.reseller.utils.OggettiTrattati;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

public record VenditaRequest(String vendutoSu, LocalDateTime data, String nome, Integer quantita, Double entrataTotale,
                             String chiaveAcquisto, OggettiTrattati tipoOggetto,Double spese) {

    //TODO: da fare
    public void validateSave(){
        if(ObjectUtils.isEmpty(vendutoSu) || ObjectUtils.isEmpty(quantita) || ObjectUtils.isEmpty(quantita)
        || ObjectUtils.isEmpty(entrataTotale) || ObjectUtils.isEmpty(chiaveAcquisto) || ObjectUtils.isEmpty(tipoOggetto))
            throw new ResellerException("Error on saveVendita, BadRequest provided",null,"Bad Request");
    }


    public void validatePatch(){

    }
}
