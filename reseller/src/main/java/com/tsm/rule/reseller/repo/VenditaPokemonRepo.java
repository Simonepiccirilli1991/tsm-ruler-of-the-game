package com.tsm.rule.reseller.repo;

import com.tsm.rule.reseller.model.entity.VenditaPokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenditaPokemonRepo extends JpaRepository<VenditaPokemon,Integer> {
}
