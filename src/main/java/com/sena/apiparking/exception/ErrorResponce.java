package com.sena.apiparking.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponce(LocalDateTime timestamp, int status, String error, String mensaje, List<String> detalles) {

}
