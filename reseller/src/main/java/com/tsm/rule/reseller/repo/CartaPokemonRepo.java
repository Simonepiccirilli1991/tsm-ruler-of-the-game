package com.tsm.rule.reseller.repo;

import com.tsm.rule.reseller.model.entity.CartePokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaPokemonRepo extends JpaRepository<CartePokemon,Integer> {

    Optional<CartePokemon> findByChiaveOggetto(String chiaveOggetto);
}
