package com.proyecto.alumnos.exceptions;

import lombok.Getter;

@Getter
public class NoEncontrado extends RuntimeException{

    public NoEncontrado(String message)
    {
        super(message);
    }

}
