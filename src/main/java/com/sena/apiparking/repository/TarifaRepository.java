package com.sena.apiparking.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.apiparking.model.Tarifa;
import com.sena.apiparking.model.TipoVehiculo;

import jakarta.persistence.LockModeType;

public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
	
	@Query("select count(t) > 0 from Tarife t where t.tipo = :tipo and t.vigenteHasta is null")
	boolean existsByTipoAndVigenteHastaIsNull(@Param("Tipo") TipoVehiculo tipo);
	
	Optional<Tarifa> findFirstByTipoAndVigenteHastaIsNull(TipoVehiculo tipo);
	
	List<Tarifa> findAllByTipoAscVigenteDesdeDesc();
	
	@Lock(LockModeType.PESSIMISTIC_WRITE) 
	@Query("select t from Tarifa t where t.tipo = :tipo and t.vigenteHasta Is null")
	Optional<Tarifa> bloquearVigente(@Param("tipo") TipoVehiculo tipo);
	
	@Query("select t from Tarifa t where t.tipo = :tipo and t.vigenteDesde <= :momento and (t.vigenteHasta is null or t.vigenteHasta > :momento ) order by t.vigenteDesde desc")
	
	
	List<Tarifa> vigentesEn(@Param ("tipo") TipoVehiculo tipo, @Param("momento") LocalDateTime momento);
	
	
}
