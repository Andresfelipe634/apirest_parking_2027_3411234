package com.sena.apiparking.confing;

import java.time.LocalDateTime;
import java.time.ZoneId;

public final class ZonaHoraria {

	public static final ZoneId ZONA = ZoneId.of("America/Bogota");

	private ZonaHoraria() {

	}

	public static LocalDateTime ahora() {
		return LocalDateTime.now(ZONA);
	}

}
