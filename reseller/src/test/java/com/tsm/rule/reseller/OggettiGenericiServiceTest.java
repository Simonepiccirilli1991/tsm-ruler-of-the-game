package com.tsm.rule.reseller;

import com.tsm.rule.reseller.io.request.OggettiGenericiRequest;
import com.tsm.rule.reseller.service.generici.OggettiGenericiServici;
import com.tsm.rule.reseller.service.generici.VenditaGenericoService;
import com.tsm.rule.reseller.utils.BrandAssociati;
import com.tsm.rule.reseller.utils.TipiOggetto;
import com.tsm.rule.reseller.utils.TipiProdotto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;

@SpringBootTest
public class OggettiGenericiServiceTest {

    @Autowired
    OggettiGenericiServici oggettiGenericiServici;
    @Autowired
    VenditaGenericoService venditaGenericoService;



    @Test
    void saveOggettoGenericoTestOK(){
        var request = new OggettiGenericiRequest("Puzzle disney","stitch", LocalDateTime.now(),9.90,
                1, TipiOggetto.GENERICO, TipiProdotto.PRODOTTO_SEALED, BrandAssociati.DISNEY,true,
                "Action");

        var acquisto = oggettiGenericiServici.salvaOggettoGenerico(request);
        Assertions.assertTrue(!ObjectUtils.isEmpty(acquisto.getChiaveOggetto()));

        var acq2 = oggettiGenericiServici.getOggettoGenerico(acquisto.getChiaveOggetto());
        Assertions.assertTrue(!ObjectUtils.isEmpty(acq2));
    }


    @Test
    void saveUpdateAndGetGenericoTestOK(){

    }
}
