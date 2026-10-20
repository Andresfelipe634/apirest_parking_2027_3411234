package com.sena.apiparking.repository;

import java.awt.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sena.apiparking.model.EspacioParqueo;
import com.sena.apiparking.model.RegistroParqueo;

public interface RegistroParqueoRepository extends JpaRepository<RegistroParqueo, Long>{
	
	@Query("select count (r) > 0 from RegistroParqueo r join r.vehiculo v where v.placa = :placa  and r.fechaSalida is null"
	boolean existsByVehiculoPlacaAndFechaSalidaIsNull (@Param ("espacio") EspacioParqueo espacio);
	
	
	@Query("selecct count (r) > 0 from RegistroParqueo r where r.espacio = :espacio")
	boolean existsByEspacio(@Param("espacio") EspacioParqueo espacio);
	
	Optional<RegistroParqueo> findByVehiculoPlacaAndFechaSalidaIsNull (String placa);
	
	List <RegistroParqueo> findByFechaSalidaIsNullOrderByFechaEntradaDesc();
	
	
	List <RegistroParqueo> findByFechaSalidaIsNullOrderByFechaEntradaDesc();
	
	
	Optional<RegistroParqueo> findByVehiculoPlacaAndFechaEntradaIsNull (String placa);
	
	@Lock(LockMadeType.PESSIMISTIC_WRITE)
	@Query("select r from RegistroParqueo r where r.id_registro = :id")
	
	
	
	
	
	
	
	
	
	
	
	
	
	Optional<RegistroParqueo> bloquearActivoPorPlaca(@Param("placa") String placa);
	
}
