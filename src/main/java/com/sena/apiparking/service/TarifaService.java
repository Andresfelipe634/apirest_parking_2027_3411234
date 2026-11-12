package com.sena.apiparking.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sena.apiparking.confing.ZonaHoraria;
import com.sena.apiparking.dto.CobroDetalle;
import com.sena.apiparking.dto.TarifaRequest;
import com.sena.apiparking.dto.TarifaResponse;
import com.sena.apiparking.exception.ApiException;
import com.sena.apiparking.model.Tarifa;
import com.sena.apiparking.model.TipoVehiculo;
import com.sena.apiparking.repository.TarifaRepository;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TarifaService {
	private final TarifaRepository tarifaRepo;

	/** Crea una nueva tarifa vigente para el tipo y cierra la anterior. */
	@Transactional
	public TarifaResponse crear(TarifaRequest req) {
		LocalDateTime ahora = ZonaHoraria.ahora();
		tarifaRepo.bloquearVigente(req.tipo()).ifPresent(t -> t.setVigenteHasta(ahora));
		Tarifa nueva = tarifaRepo.save(Tarifa.builder().tipo(req.tipo()).valorHora(req.valorHora())
				.valorFraccion(req.valorFraccion()).fraccionMinutos(req.fraccionMinutos()).vigenteDesde(ahora).build());
		return TarifaResponse.de(nueva);
	}

	@Transactional(readOnly  = true)
	public List<TarifaResponse> listar(TipoVehiculo tipo, Boolean vigentes) {
		return tarifaRepo.findAllByOrderByTipoAscVigenteDesdeDesc().stream()
				.filter(t -> tipo == null || t.getTipo() == tipo)
				.filter(t -> vigentes == null || (t.getVigenteHasta() == null) == vigentes).map(TarifaResponse::de)
				.toList();
	}

	@Transactional(readOnly = true)
	public TarifaResponse obtener(Long id) {
		return TarifaResponse.de(
				tarifaRepo.findById(id).orElseThrow(() -> ApiException.notFound("Tarifa " + id + " no encontrada")));
	}

	/**
	 * Falla si el tipo no tiene tarifa vigente (se usa al registrar la entrada).
	 */
	@Transactional(readOnly = true)
	public void exigirTarifaVigente(TipoVehiculo tipo) {
		if (!tarifaRepo.existsByTipoAndVigenteHastaIsNull(tipo)) {
			throw ApiException.conflict("No hay tarifa vigente para " + tipo);
		}
	}

	@Transactional(readOnly = true)
	public CobroDetalle calcular(TipoVehiculo tipo, LocalDateTime entrada, LocalDateTime salida) {
		Tarifa t = tarifaVigenteEn(tipo, salida);
		int fm = t.getFraccionMinutos();
		long segundos = Math.max(0, Duration.between(entrada, salida).getSeconds());
		long minutos = (segundos + 59) / 60;
		long horas = minutos / 60;
		long resto = minutos % 60;
		long fracciones = (resto + fm -  1) / fm;
		if (horas == 0 && fracciones == 0) {
			fracciones = 1;
		}
		long cobroFracciones = Math.min(fracciones * t.getValorFraccion(), t.getValorHora());
		long total = horas * t.getValorHora() + cobroFracciones;
		return new CobroDetalle(t.getId_tarifa(), minutos, horas, fracciones, t.getValorHora(), t.getValorFraccion(),
				fm, total);
	}

	private Tarifa tarifaVigenteEn(TipoVehiculo tipo, LocalDateTime momento) {
		return tarifaRepo.vigentesEn(tipo, momento).stream().findFirst()
				.or(() -> tarifaRepo.findFirstByTipoAndVigenteHastaIsNull(tipo))
				.orElseThrow(() -> ApiException.conflict("No hay tarifa vigente para " + tipo));
	}
}
