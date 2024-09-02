package com.tsm.rule.reseller.utils;

import com.tsm.rule.reseller.io.request.CartePokemonRequest;
import com.tsm.rule.reseller.model.entity.CartePokemon;

import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.Consumer;

public class ResellesUtils {

    public static String generaChiaveOggetto(String nome, String data){
        return String.valueOf((nome + data).hashCode());
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
