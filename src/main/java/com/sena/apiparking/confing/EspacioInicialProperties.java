package com.sena.apiparking.confing;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "parking.espacios.iniciales")
public class EspacioInicialProperties {
	
	
	private int carro = 25;
	private int moto =20;
	private int bicicleta = 5;
	
	@PostConstruct
	void validar() {
		if (carro<0 || moto <0 || bicicleta <0) {
			throw new IllegalStateException("parking.espacios.iniciales.* no puede ser negativo");
			
		}
	}
	

}
