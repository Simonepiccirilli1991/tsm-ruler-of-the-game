package com.tsm.rule.reseller.repo;

import com.tsm.rule.reseller.model.entity.VenditaGenerica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenditaGenericaRepo extends JpaRepository<VenditaGenerica,Integer> {
}
