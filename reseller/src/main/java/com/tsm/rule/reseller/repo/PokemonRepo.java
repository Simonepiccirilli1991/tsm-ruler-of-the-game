package com.tsm.rule.reseller.repo;

import com.tsm.rule.reseller.model.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepo extends JpaRepository<Pokemon,Integer> {
}
