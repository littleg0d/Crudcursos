package com.proyecto.alumnos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
public class CursoRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;
    @NotNull(message = "La fecha de inicio no puede estar vacia")
    private LocalDate fechaInicio;
    @NotNull(message = "La fecha de fin no puede estar vacia")
    private LocalDate fechaFin;
    @Positive(message = "El cupo maximo debe ser un numero positivo")
    @NotNull(message = "El cupo maximo no puede estar vacio")
    private Integer cupoMax;
}
