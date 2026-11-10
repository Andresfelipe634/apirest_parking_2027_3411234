package com.sena.apiparking.dto;

import com.sena.apiparking.model.TipoVehiculo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EspacioRequest(@NotNull @Positive Integer numero, @NotNull TipoVehiculo tipo) {

}
