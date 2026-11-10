package com.sena.apiparking.dto;

import com.sena.apiparking.model.TipoVehiculo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EspacioLoteRequest(@NotNull @Positive Integer desde, @NotNull @Positive Integer hasta, @NotNull TipoVehiculo tipo) {

}
