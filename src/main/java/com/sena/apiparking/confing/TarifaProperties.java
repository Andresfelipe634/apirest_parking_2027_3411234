package com.sena.apiparking.confing;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.sena.apiparking.model.TipoVehiculo;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "parking.tarifa")
public class TarifaProperties {

	
	private int fraccionMinutos = 15;
	private Map<TipoVehiculo, Valores> tipos = new HashMap<>();
	
	

	@Setter
	@Getter
	public static class Valores {
		private long hora;
		private long fraccion; 
		
	}
	
	

	@PostConstruct
	void validar(){
		if (fraccionMinutos < 1 ||  fraccionMinutos > 60) {
			throw new IllegalStateException("parking.tarifa.fraccion-minutos debe estar entre 1 y 60");
			
		}
		
		for (TipoVehiculo t : TipoVehiculo.values()) {
			Valores v = tipos.get(t);
			if (v == null || v.getHora() < 0 || v.getFraccion() < 0 ) {
				throw new IllegalStateException("Falta  o es invalida la tarifa parking.tarifa.tipos." + t.name().toLowerCase());
				
			}
			
		}
		
	}
	
}
