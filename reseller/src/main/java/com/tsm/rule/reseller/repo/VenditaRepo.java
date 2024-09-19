package com.tsm.rule.reseller.repo;

import com.tsm.rule.reseller.model.entity.Vendita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VenditaRepo extends JpaRepository<Vendita,Integer> {

    @Query(name = "SELECT c FROM vendite c WHERE c.brandAssociato = :brandAssociato",nativeQuery = true)
    List<Vendita> findByBrand(@Param("brandAssociato") String brandAssociato);

    @Query(name = "SELECT c FROM vendite c WHERE c.chiaveAcquisto = :chiaveAcquisto",nativeQuery = true)
    List<Vendita> findByChiaveAcquisto(@Param("chiaveAcquisto") String chiaveAcquisto);

    @Query(name = "SELECT c FROM vendite c WHERE c.vendutoSu = :vendutoSu",nativeQuery = true)
    List<Vendita> findByVEndutoSu(@Param("vendutoSu") String vendutoSu);

    //TODO: inserire poi altre queery un poco piu complesse
}
