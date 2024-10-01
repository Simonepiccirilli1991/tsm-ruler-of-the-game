package com.tsm.rule.reseller.controller;


import com.tsm.rule.reseller.io.request.VenditaGenericaRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
import com.tsm.rule.reseller.model.entity.VenditaGenerica;
import com.tsm.rule.reseller.reactivewrapper.VenditeGenericoWrapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("get/{idVendita}")
    public Mono<VenditaGenerica> getVenditaGenerica(@PathVariable ("idVendita") Integer idVendita){
        return genericoWrapperService.getVenditaGenerica(idVendita);
    }

    @DeleteMapping("delete/{idVendita}")
    public Mono<BaseResponse> deleteVenditaGen(@PathVariable ("idVendita") Integer idVendita){
        return genericoWrapperService.deleteVenditaGenerica(idVendita);
    }
}
