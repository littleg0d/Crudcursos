package com.proyecto.alumnos.exceptions;


import lombok.Getter;

@Getter
public class CursoNoVacio extends RuntimeException{
    public CursoNoVacio(String message)
    {
        super(message);
    }
}
