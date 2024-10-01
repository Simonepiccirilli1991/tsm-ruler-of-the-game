package com.tsm.rule.reseller;

import com.tsm.rule.reseller.exception.ResellerException;
import com.tsm.rule.reseller.io.request.OggettiGenericiRequest;
import com.tsm.rule.reseller.io.request.VenditaGenericaRequest;
import com.tsm.rule.reseller.service.generici.OggettiGenericiServici;
import com.tsm.rule.reseller.service.generici.VenditaGenericoService;
import com.tsm.rule.reseller.utils.BrandAssociati;
import com.tsm.rule.reseller.utils.PiattaformeVendita;
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
        var request =  new OggettiGenericiRequest("Puzzle spiderman","marvel", LocalDateTime.now(),9.90,
                1, TipiOggetto.GENERICO, TipiProdotto.PRODOTTO_SEALED, BrandAssociati.GENERICO,true,
                "Action");

        var acquisto = oggettiGenericiServici.salvaOggettoGenerico(request);
        Assertions.assertTrue(BrandAssociati.GENERICO.getValue().equals(acquisto.getBrandAssociato()));
        Assertions.assertTrue(TipiProdotto.PRODOTTO_SEALED.name().equals(acquisto.getTipoProdotto()));

        var udapteRequest = new OggettiGenericiRequest(null,"Sdighete",null,null,
                null,null,null,null,false,"Sbrinbi");

        var acquistoUpdate = oggettiGenericiServici.patchOggettoGenerico(acquisto.getChiaveOggetto(), udapteRequest);
        Assertions.assertTrue(acquistoUpdate.getNote().equals("Sdighete"));
        Assertions.assertTrue(acquistoUpdate.getAcquistatoDa().equals("Sbrinbi"));
    }

    @Test
    void saveGetAndDeleteGenericoTestOK(){
        var request =  new OggettiGenericiRequest("Puzzle sbirulino","trenino ciuf", LocalDateTime.now(),
                12.90, 2, TipiOggetto.GENERICO, TipiProdotto.PRODOTTO_SEALED, BrandAssociati.GENERICO,
                true, "Cossuto");

        var acquisto = oggettiGenericiServici.salvaOggettoGenerico(request);
        Assertions.assertFalse(ObjectUtils.isEmpty(acquisto.getChiaveOggetto()));

        Assertions.assertTrue(oggettiGenericiServici.getOggettoGenerico(acquisto.getChiaveOggetto()) != null);
        var resp = oggettiGenericiServici.deleteOggettoGenerico(acquisto.getChiaveOggetto());
        Assertions.assertTrue(resp.code().equals("00"));
        // siccome la get se assente l'entity torna eccezzione testiamo pure il ko con una botta sola
        var excp = Assertions.assertThrows(ResellerException.class , () -> {
            oggettiGenericiServici.getOggettoGenerico(acquisto.getChiaveOggetto());
        });
        Assertions.assertTrue(excp.getMessage().equals("Error on GetOggettoGenerico"));
    }

    // -------------------- VENDITE ------------------
    @Test
    void saveVenditaAndGetTestOK(){
        var request =  new OggettiGenericiRequest("Tazza Hallowen","tazza ceramica", LocalDateTime.now(),
                6.90, 4, TipiOggetto.GENERICO, TipiProdotto.PRODOTTO_SEALED, BrandAssociati.GENERICO,
                true, "Cossuto");

        var acquisto = oggettiGenericiServici.salvaOggettoGenerico(request);
        Assertions.assertFalse(ObjectUtils.isEmpty(acquisto.getChiaveOggetto()));

        var vednitaRequest = new VenditaGenericaRequest("Tazza Hallowen",LocalDateTime.now(),2,28.00,
                null, acquisto.getChiaveOggetto(), PiattaformeVendita.VINTED,null,BrandAssociati.GENERICO,TipiOggetto.TAZZE);
        // chiamo salva vendita
        var vendita = venditaGenericoService.saveVenditaGenerica(vednitaRequest);
        Assertions.assertTrue(vendita.getBrandAssociato().equals(BrandAssociati.GENERICO.getValue()));
        Assertions.assertTrue(vendita.getVendutoSu().equals(PiattaformeVendita.VINTED.getValue()));
        //checko calcolo effettivo del singolo
        Assertions.assertEquals(vendita.getEntrataSingola(),14.00);
        // testo scalatura rollback quantita disponibile
        var updateAcq = oggettiGenericiServici.getOggettoGenerico(acquisto.getChiaveOggetto());
        Assertions.assertEquals(2,updateAcq.getQuantitaDisponibile());
    }

    //TODO: c'e da testare la get di vendita e la delete con rollback su acquisto
}
