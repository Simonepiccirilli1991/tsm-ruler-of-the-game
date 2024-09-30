package com.tsm.rule.reseller.controller;


import com.tsm.rule.reseller.io.request.VenditaGenericaRequest;
import com.tsm.rule.reseller.model.entity.VenditaGenerica;
import com.tsm.rule.reseller.reactivewrapper.VenditeGenericoWrapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/vendite/generici")
@RequiredArgsConstructor
public class VenditeOggettiGenController {

    private final VenditeGenericoWrapperService genericoWrapperService;


    @PostMapping("save")
    public Mono<VenditaGenerica> saveVengitaGenerica(@RequestBody VenditaGenericaRequest request){
        return genericoWrapperService.saveVenditaGenerica(request);
    }
}
