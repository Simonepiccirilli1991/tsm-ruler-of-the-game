package com.tsm.rule.reseller.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.tsm.rule.reseller.exception.ResellerException;
import com.tsm.rule.reseller.io.request.CartePokemonRequest;
import com.tsm.rule.reseller.io.request.OggettiGenericiRequest;
import com.tsm.rule.reseller.model.entity.CartePokemon;
import com.tsm.rule.reseller.model.entity.OggettoGenerico;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Consumer;

public class ResellesUtils {

    private static final Logger log = LoggerFactory.getLogger(ResellesUtils.class);
    private static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule().addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));

    public static String generaChiaveOggetto(String nome, String data){
        return String.valueOf((nome + data).hashCode());
    }

    public static Object mapperObject(Object o, Class<?> c){
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        try{
            return mapper.convertValue(o,c);
        }catch (Exception e){
            log.error("Error on mapperObject with err: {}",e.getMessage());
            throw new ResellerException("Error on mapperObject",e,e.getMessage());
        }
    }

    public static void updateOggettiGenerici(OggettiGenericiRequest request, OggettoGenerico oggetto){

        setIfPresent(request.nome(), oggetto::setNome);
        setIfPresent(request.data(), data -> oggetto.setData(data.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))); // Convert LocalDateTime to String
        setIfPresent(request.costoTotale(), oggetto::setCostoTotale);
        setIfPresent(request.quantita(), oggetto::setQuantita);
        // Update CartePokemon specific fields
        setIfPresent(request.tipiProdotto(), tipoProdotto -> oggetto.setTipoProdotto(tipoProdotto.toString()));
        setIfPresent(request.sealed(), oggetto::setSealed);
        setIfPresent(request.acquistatoDa(), oggetto::setAcquistatoDa);
        setIfPresent(request.quantita(), oggetto::setQuantitaDisponibile);
        setIfPresent(request.note(), oggetto::setNote);
        setIfPresent(request.brandAssociato(),brandAssociato ->  oggetto.setBrandAssociato(brandAssociato.getValue()));
    }

    public static void updateCartePokemonEntity(CartePokemonRequest request, CartePokemon entity) {
        // Update inherited fields from AcquistoBase
        setIfPresent(request.nome(), entity::setNome);
        setIfPresent(request.data(), data -> entity.setData(data.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))); // Convert LocalDateTime to String
        setIfPresent(request.costoTotale(), entity::setCostoTotale);
        setIfPresent(request.quantita(), entity::setQuantita);

        // Update CartePokemon specific fields
        setIfPresent(request.codice(), entity::setCodice);
        setIfPresent(request.tipoProdotto(), tipoProdotto -> entity.setTipoProdotto(tipoProdotto.toString()));
        setIfPresent(request.sealed(), entity::setSealed);
        setIfPresent(request.setSpeciale(), entity::setSetSpeciale);
        setIfPresent(request.graded(), entity::setGraded);
        setIfPresent(request.gradedValue(), entity::setGradeValue);
        setIfPresent(request.acquistatoPresso(), entity::setAcquistatoPresso);
        setIfPresent(request.quantita(), entity::setQuantitaDisponibile);
    }

    private static <T> void setIfPresent(T value, Consumer<T> setter) {
        Optional.ofNullable(value).ifPresent(setter);
    }
}
