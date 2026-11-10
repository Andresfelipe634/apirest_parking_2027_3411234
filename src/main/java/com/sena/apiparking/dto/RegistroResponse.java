package com.sena.apiparking.dto;

import java.time.LocalDateTime;

import com.sena.apiparking.model.RegistroParqueo;
import com.sena.apiparking.model.TipoVehiculo;

public record RegistroResponse(Long idRegistro, String placa, TipoVehiculo tipovehiculo, Integer numeroEspacio, LocalDateTime fechaEntrada, LocalDateTime fechaSalida, Double valorPagado, CobroDetalle cobro) {

	
	public static RegistroResponse de(RegistroParqueo r, CobroDetalle cobro) {
		return new RegistroResponse(r.getId_registro(), r.getVehiculo().getPlaca(), r.getVehiculo().getTipo(), r.getEspacio().getNumero(), r.getFechaEntrada(), r.getFechaSalida(), r.getValorPagado(), cobro );
		
		  
	}
}
