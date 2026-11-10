package com.sena.apiparking.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import com.sena.apiparking.exception.ErrorResponce;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ErrorResponce> api(ApiException ex) {
		return build(ex.getStatus(), ex.getMessage(), List.of());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponce> validation(MethodArgumentNotValidException ex) {
		List<String> detalles = ex.getBindingResult().getFieldErrors().stream()
				.map(f -> f.getField() + ": " + f.getDefaultMessage()).toList();
		return build(HttpStatus.BAD_REQUEST, "Datos de entrada invalidos", detalles);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponce> jsonInvalido(HttpMessageNotReadableException ex) {
		return build(HttpStatus.BAD_REQUEST,
				"JSON mal formado o con valores no permitidos (tipo debe ser CARRO, MOTO o BICICLETA)", List.of());
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorResponce> parametro(MethodArgumentTypeMismatchException ex) {
		return build(HttpStatus.BAD_REQUEST, "Valor invalido para el parametro '" + ex.getName() + "'", List.of());
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponce> integridad(DataIntegrityViolationException ex) {
		return build(HttpStatus.CONFLICT, "Operacion rechazada por integridad de datos", List.of());
	}

	private ResponseEntity<ErrorResponce> build(HttpStatus s, String msg, List<String> det) {
		return ResponseEntity.status(s)
				.body(new ErrorResponce(LocalDateTime.now(), s.value(), s.getReasonPhrase(), msg, det));
	}

}