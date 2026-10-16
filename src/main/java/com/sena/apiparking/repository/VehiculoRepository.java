package com.sena.apiparking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.apiparking.model.Vehiculo;

import jakarta.persistence.LockModeType;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long>{
	
	Optional<Vehiculo> findFirstByPlaca(String placa);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE) 
	@Query("select v from Vehiculo v where v.placa = :placa")
	Optional<Vehiculo> bloquearPorPlaca(@Param("placa") String placa);
	
	


}
