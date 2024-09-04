package com.tsm.rule.reseller.service;

import com.tsm.rule.reseller.io.request.FilteringPokemonRequest;
import com.tsm.rule.reseller.model.entity.CartePokemon;
import com.tsm.rule.reseller.repo.CartaPokemonRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FilteringPokemonService {


    private final CartaPokemonRepo cartaPokemonRepo;


    public List<CartePokemon> filteringCartePokemon(FilteringPokemonRequest request){
        log.info("FilteringCartePokemon service started with raw request: {}",request);

        var resp = switch (request){
            // nome e date
            case FilteringPokemonRequest o when !ObjectUtils.isEmpty(o.nome()) && !ObjectUtils.isEmpty(o.startDate()) && !ObjectUtils.isEmpty(o.endDate()) -> {
               log.info("Filtering acquisti pokemon for nome and dates");
                yield cartaPokemonRepo.findByNomeAndDates(o.nome(), o.startDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), o.endDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }
            // nome e prezzi
            case FilteringPokemonRequest o when !ObjectUtils.isEmpty(o.nome()) && !ObjectUtils.isEmpty(o.totaleMin()) && !ObjectUtils.isEmpty(o.totaleMax()) -> {
                log.info("Filtering acquisti pokemon for nome and prices");
                yield cartaPokemonRepo.findByNomeAndCostoTotale(o.nome(), o.totaleMin(), o.totaleMax());
            }
            // solo x date
            case FilteringPokemonRequest o when !ObjectUtils.isEmpty(o.startDate()) && !ObjectUtils.isEmpty(o.endDate()) -> {
                log.info("Filtering acquisti pokemon for dates");
                yield cartaPokemonRepo.filterByDates(o.startDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME), o.endDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            }
            // return all
            default -> {
                log.info("Filtering all acquisti pokemon");
                yield cartaPokemonRepo.findAll();
            }
        };
        log.info("FilteringCartePokemon service ended successfully");
        return resp;
    }
}
