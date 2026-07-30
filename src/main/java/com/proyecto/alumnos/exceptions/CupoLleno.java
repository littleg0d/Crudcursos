package com.proyecto.alumnos.exceptions;

import lombok.Getter;

@Getter
public class CupoLleno extends RuntimeException{
    public CupoLleno(String message)
    {
        super(message);
    }
}
