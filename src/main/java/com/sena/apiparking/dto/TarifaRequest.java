package com.sena.apiparking.dto;

import com.sena.apiparking.model.TipoVehiculo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TarifaRequest(@NotNull TipoVehiculo tipo @NotNull @PositiveOrZero Long valorHora @NotNull @PositiveOrZero Long valorFraccion,
		@NotNull @Min(1) @Max(60) Integer fraccionMinnutos) {

}
