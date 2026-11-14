package com.sena.apiparking.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sena.apiparking.dto.EspacioLoteRequest;
import com.sena.apiparking.dto.EspacioRequest;
import com.sena.apiparking.dto.EspacioResponse;
import com.sena.apiparking.dto.TarifaRequest;
import com.sena.apiparking.service.EspacioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/espacios")
@RequiredArgsConstructor
public class EspacioController {
	
	
	private final EspacioService service;
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EspacioResponse crear(@Valid @RequestBody EspacioRequest req) {
		return service.crear(req);
		
	}
	

	@PostMapping("/lote")
	@ResponseStatus(HttpStatus.CREATED)
	public List<EspacioResponse> crearLote(@Valid @RequestBody EspacioLoteRequest req) {
		return service.crearLote(req);
		
	}

}
