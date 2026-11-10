package com.sena.apiparking.dto;

import com.sena.apiparking.model.EspacioParqueo;
import com.sena.apiparking.model.EstadoEspacio;
import com.sena.apiparking.model.TipoVehiculo;

public record EspacioResponse(Long idEspacio, Integer numero, TipoVehiculo tipo, EstadoEspacio estado) {
	
	public static EspacioResponse de(EspacioParqueo e) {
		return new EspacioResponse(e.getId_espacio(), e.getNumero(), e.getTipo(), e.getEstado());
		
	}

}
