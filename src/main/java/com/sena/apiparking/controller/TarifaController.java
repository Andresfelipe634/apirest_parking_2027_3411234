package com.sena.apiparking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sena.apiparking.dto.TarifaRequest;
import com.sena.apiparking.dto.TarifaResponse;
import com.sena.apiparking.model.TipoVehiculo;
import com.sena.apiparking.service.TarifaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tarifas")
@RequiredArgsConstructor
public class TarifaController {
	
	private final TarifaService service;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TarifaResponse crear(@Valid @RequestBody TarifaRequest req) {
		
		return service.crear(req);
	}
	
	public List<TarifaResponse> listar (@RequestParam(required = false) TipoVehiculo tipo,
			@RequestParam(required = false) Boolean vigentes){
		
		return service.listar(tipo, vigentes);
	}
	
	
	@GetMapping("/{id}")
	public TarifaResponse obtener(@PathVariable Long id) {
		
		return service.obtener(id);
	}
}
