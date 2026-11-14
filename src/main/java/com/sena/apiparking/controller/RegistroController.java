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

import com.sena.apiparking.dto.EntradaRrquest;
import com.sena.apiparking.dto.RegistroResponse;
import com.sena.apiparking.service.RegistroService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/registros")
@RequiredArgsConstructor
public class RegistroController {
	
	private final RegistroService service;
	
	
	@PostMapping("/entrada")
	@ResponseStatus(HttpStatus.CREATED)	
	public RegistroResponse entrada(@Valid @RequestBody EntradaRrquest req) {
		return service.registrarEntrada(null);
	}
	
	
	@PostMapping("/{id}salida")
	public RegistroResponse salida(@PathVariable Long id) {
		return service.registrarSalida(id);
		
	}
	
	@GetMapping("/{id}")
	public RegistroResponse obtener(@PathVariable Long id) {
		return service.obtener(id);
	}
	@PostMapping("/placa/{placa}/salida")
	public RegistroResponse salidaPorPlaca(@PathVariable String placa) {
		return service.registrarSalidaPorPlaca(placa);
	}
	
	@GetMapping("/placa{placa}/cobro")
	public RegistroResponse cobroPorPlaca(@PathVariable String placa) {
		return service.consultarCobroPorPlaca(placa);
		
	}
	
	@GetMapping("/placa/{placa}")
	public RegistroResponse activoPorPlaca (@PathVariable String placa) {
		return service.obtenerActivoPorPlaca(placa);
	}
	
	@GetMapping
	public List<RegistroResponse> listar(@RequestParam(required = false) Boolean activos){
		return service.listar(activos);
	}

}
