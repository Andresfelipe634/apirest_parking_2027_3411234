package com.sena.apiparking.dto;

import com.sena.apiparking.model.TipoVehiculo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record EntradaRrquest(@NotBlank String placa, @NotNull TipoVehiculo tipo, @Size(max =1000) String descripcion,@Positive Integer numeroEspacio) {

}
