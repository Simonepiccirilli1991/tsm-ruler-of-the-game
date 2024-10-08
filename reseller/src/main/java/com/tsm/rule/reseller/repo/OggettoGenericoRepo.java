package com.tsm.rule.reseller.repo;

import com.tsm.rule.reseller.model.entity.OggettoGenerico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OggettoGenericoRepo extends JpaRepository<OggettoGenerico,Integer> {

    Optional<OggettoGenerico> findByChiaveOggetto(String chiave);
}
