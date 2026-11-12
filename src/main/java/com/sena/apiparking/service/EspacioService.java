package com.sena.apiparking.service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.sena.apiparking.dto.EspacioLoteRequest;
import com.sena.apiparking.dto.EspacioRequest;
import com.sena.apiparking.dto.EspacioResponse;
import com.sena.apiparking.model.EspacioParqueo;
import com.sena.apiparking.model.EstadoEspacio;
import com.sena.apiparking.model.TipoVehiculo;
import com.sena.apiparking.repository.EspacioParqueoRepository;
import com.sena.apiparking.repository.RegistroParqueoRepository;
import com.sena.apiparking.exception.ApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EspacioService {

	private static final int MAX_LOTE = 500;

	private final EspacioParqueoRepository espacioRepo;
	private final RegistroParqueoRepository registroRepo;
	

	@Transactional
	public EspacioResponse crear(EspacioRequest req) {
		if (espacioRepo.existsByNumero(req.numero())) {
			throw ApiException.conflict("Ya existe el espacio numero " + req.numero());
		}
		EspacioParqueo e = EspacioParqueo.builder().numero(req.numero()).tipo(req.tipo()).build();
		return EspacioResponse.de(espacioRepo.save(e));
		
	}
	
	@Transactional 
	public List<EspacioResponse> crearLote (EspacioLoteRequest req){
		if (req.desde() < req.hasta()) {
			throw ApiException.badRequest("'desde' no puede ser mayor 'hasta'");
			
		}
		if (req.hasta()- req.desde() + 1 >  MAX_LOTE) {
			throw ApiException.badRequest("Maximo "+ MAX_LOTE + "espacios por lote");
			
		}
		List<EspacioParqueo> nuevos = new ArrayList<>();
		for (int n = req.desde(); n < req.hasta(); n++) {
			if (espacioRepo.existsByNumero(n)) {
				throw ApiException.conflict("Ya existe el espacio numero "+n+"; no se creo ninguno");
				
			}
			nuevos.add(EspacioParqueo.builder().numero(n).tipo(req.tipo()).build());
		}
		return espacioRepo.saveAll(nuevos).stream().map(EspacioResponse::de).toList();
		
		
	}
	
	
	@Transactional(readOnly = true)
	public List<EspacioResponse> listar(TipoVehiculo tipo, EstadoEspacio estado){
		return espacioRepo.findAll(Sort.by(EspacioParqueo::getNumero)).stream()
				.filter(e -> tipo == null || e.getTipo()== tipo)
				.filter(e -> estado == null || e.getEstado()== estado)
				.map(EspacioResponse::de).toList();
		
		
	}
	
	@Transactional(readOnly = true)
	
	public EspacioResponse obtener(Long id) {
		return EspacioResponse.de(buscar(id));
		
	}
	
	@Transactional
	public EspacioResponse actualizar(Long id, EspacioRequest req) {
		EspacioParqueo e = buscar(id);
		if (e.getEstado()== EstadoEspacio.OCUPADO) {
			throw ApiException.conflict("No se puede modificar un espacio ocupado");
			
		}
		if (!e.getNumero().equals(req.numero()) && espacioRepo.existsByNumero(req.numero())) {
			throw ApiException.conflict("Ya existe el espacio numero "+ req.numero());
			
		}
		e.setNumero(req.numero());
		e.setTipo(req.tipo());
		return EspacioResponse.de(e);
	}
	
	@Transactional
	public void eliminar(Long id) {
		EspacioParqueo e = buscar(id);
		if (e.getEstado()== EstadoEspacio.OCUPADO) {
			throw ApiException.conflict("No se puede eleminar un espacio ocupado");
			
		}
		if (registroRepo.existsByEspacio(e)) {
			throw ApiException.conflict("El espacio tiene historial de registros y no puede eliminarse ");
			
			
		}
		espacioRepo.delete(e);
		
	}
	
	
	private EspacioParqueo buscar(Long id) {
		return espacioRepo.findById(id).orElseThrow(() -> ApiException.notFound("Espacio " +id + "no encontrado"));
	}
	
	

}