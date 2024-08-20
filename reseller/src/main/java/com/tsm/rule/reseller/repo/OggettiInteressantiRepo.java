package com.tsm.rule.reseller.repo;

import com.tsm.rule.reseller.model.entity.OggettiInteressanti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OggettiInteressantiRepo extends JpaRepository<OggettiInteressanti,Integer> {
}
