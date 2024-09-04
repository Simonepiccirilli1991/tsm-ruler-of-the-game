package com.tsm.rule.reseller.repo;

import com.tsm.rule.reseller.model.entity.OggettiInteressati;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OggettiInteressantiRepo extends JpaRepository<OggettiInteressati,Integer> {

    Optional<OggettiInteressati> findByNome(String nome);
}
