package com.tsm.rule.reseller.repo;

import com.tsm.rule.reseller.model.entity.CartePokemon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartaPokemonRepo extends JpaRepository<CartePokemon,Integer> {

    Optional<CartePokemon> findByChiaveOggetto(String chiaveOggetto);

    @Query(value = "SELECT c FROM cartePokemon c WHERE c.nome = :nome AND c.data >= :startDate AND c.date <= :endDate",nativeQuery = true)
    List<CartePokemon> findByNomeAndDates(@Param("nome") String nome,
                                          @Param("startDate") String startDate,
                                          @Param("endDate") String endDate);


    @Query(value = "SELECT c FROM cartePokemon c WHERE c.nome = :nome AND c.costo_totale >= :costoMin AND c.costo_totale <= :costoMax",nativeQuery = true)
    List<CartePokemon> findByNomeAndCostoTotale(@Param("nome") String nome,
                                                @Param("costoMin") Double costoMin,
                                                @Param("costoMax") Double costoMax);

    @Query(value = "SELECT c FROM cartePokemon c WHERE c.date >= :startDate AND c.date <= :endDate",nativeQuery = true)
    List<CartePokemon> filterByDates( @Param("startDate") String startDate,
                                      @Param("endDate") String endDate);
}
