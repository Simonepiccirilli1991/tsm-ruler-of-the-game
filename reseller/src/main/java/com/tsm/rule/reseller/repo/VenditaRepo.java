package com.tsm.rule.reseller.repo;

import com.tsm.rule.reseller.model.entity.Vendita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenditaRepo extends JpaRepository<Vendita,Integer> {
}
