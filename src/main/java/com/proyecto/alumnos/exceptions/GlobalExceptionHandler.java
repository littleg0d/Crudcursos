package com.proyecto.alumnos.exceptions;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoEncontrado.class)
    @ApiResponse(
            responseCode = "404",
            description = "Recurso no encontrado"
    )
    public ResponseEntity<String> noEncontradoHandler(RuntimeException ex)
    {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
    @ExceptionHandler({YaEncontrado.class, CupoLleno.class, CursoNoVacio.class})
    @ApiResponse(
            responseCode = "409",
            description = "Conflicto (Ya existe, cupo lleno, no esta vacio)"
    )
    public ResponseEntity<String> conflictoHandler(RuntimeException ex)
    {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidaciones(
            MethodArgumentNotValidException ex
    ) {
        Map<String, String> errores = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String campo = error.getField();
            String mensaje = error.getDefaultMessage();

            errores.put(campo, mensaje);
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errores);
    }
}



