package com.tsm.rule.reseller.controller;

import com.tsm.rule.reseller.io.request.OggettiInteressantiRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
import com.tsm.rule.reseller.model.entity.OggettiInteressati;
import com.tsm.rule.reseller.model.internal.InteressantiUpdate;
import com.tsm.rule.reseller.reactivewrapper.InteressanteWrapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/interessanti")
@RequiredArgsConstructor
public class OgInteressantiController {

    private final InteressanteWrapperService interessanteWrapperService;


    @PostMapping("/add")
    public Mono<OggettiInteressati> saveOggettoInt(@RequestBody OggettiInteressantiRequest request){
            return interessanteWrapperService.saveOggettiInteressanti(request);
    }

    @GetMapping("/get/{nome}")
    public Mono<OggettiInteressati> getOggettiInteressante(@PathVariable String nome){
        return interessanteWrapperService.getOggettiInteressanti(nome);
    }

    @DeleteMapping("/delete/{nome}")
    public Mono<BaseResponse> deleteOggettoInteressante(@PathVariable String nome){
        return interessanteWrapperService.deleteOggettiInteressanti(nome);
    }

    @PostMapping("update/{nome}")
    public Mono<OggettiInteressati> updateInteressanti(@PathVariable String nome, @RequestBody InteressantiUpdate update){
        return interessanteWrapperService.updateOggettiInteressanti(nome,update);
    }
}
