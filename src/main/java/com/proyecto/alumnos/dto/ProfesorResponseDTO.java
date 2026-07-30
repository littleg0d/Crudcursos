package com.proyecto.alumnos.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfesorResponseDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
}
