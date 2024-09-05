package com.tsm.rule.reseller.controller;

import com.tsm.rule.reseller.io.request.OggettiGenericiRequest;
import com.tsm.rule.reseller.io.response.BaseResponse;
import com.tsm.rule.reseller.model.entity.OggettoGenerico;
import com.tsm.rule.reseller.reactivewrapper.GenericoWrapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/v1/generico/")
@RequiredArgsConstructor
public class OgGenericoController {

    private final GenericoWrapperService genericoWrapperService;

    @PostMapping("/add")
    public Mono<OggettoGenerico> saveOggettoGenerico(@RequestBody OggettiGenericiRequest request){
        return genericoWrapperService.saveOggettoGenerico(request);
    }

    @GetMapping("/get/{chiave}")
    public Mono<OggettoGenerico> getOggettoGenerico(@PathVariable String chiave){
        return genericoWrapperService.getOggettoGenerico(chiave);
    }

    @PatchMapping("/patch/{chiave}")
    public Mono<OggettoGenerico> patchOggettoGenerico(@PathVariable String chiave,@RequestBody OggettiGenericiRequest request){
        return genericoWrapperService.patchOggettoGenerico(chiave,request);
    }

    @DeleteMapping("/delete/{chiave}")
    public Mono<BaseResponse> deleteOggetto(@PathVariable String chiave){
        return genericoWrapperService.deleteOggettoGenerico(chiave);
    }
}
