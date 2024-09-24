package com.tsm.rule.reseller.service.generici;

import com.tsm.rule.reseller.repo.VenditaGenericaRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VenditaGenericoService {


    private final VenditaGenericaRepo venditaGenericaRepo;
}
