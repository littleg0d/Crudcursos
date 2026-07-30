package com.proyecto.alumnos.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AlumnoRequestDTO {
    @NotBlank(message = "El nombre no puede estar vacio")
    private String nombre;
    @NotBlank(message = "El apellido no puede estar vacio")
    private String apellido;
    @Email(message = "El email no es valido")
    @NotBlank(message = "El email no puede estar vacio")
    private String email;
    @NotNull(message = "La fecha de nacimiento no puede estar vacia")
    private LocalDate fechaNacimiento;
}
