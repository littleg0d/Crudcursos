package com.proyecto.alumnos.exceptions;

import lombok.Getter;

@Getter
public class YaEncontrado extends RuntimeException {
    public YaEncontrado(String message)
    {
        super(message);
    }
}
