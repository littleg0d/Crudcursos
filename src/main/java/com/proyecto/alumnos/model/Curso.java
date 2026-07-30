package com.proyecto.alumnos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(
        name = "cursos",
        indexes = {
            @Index(
                name = "index_curso_profesor",
                columnList = "profesor_id")
        }
)

public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private LocalDate fechaInicio;
    @Column(nullable = false)
    private LocalDate fechaFin;
    @Column(nullable = false)
    private int cupoMax;
    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    @ManyToMany
    @JoinTable(
            name = "curso_alumnos",
            joinColumns = @JoinColumn(name = "curso_id"),
            inverseJoinColumns = @JoinColumn(name = "alumno_id"),
            indexes = {
                @Index(
                    name = "index_curso_alumnos_curso",
                    columnList = "curso_id"),
                @Index(
                    name = "index_curso_alumnos_alumno",
                    columnList = "alumno_id")
            })




    Set<Alumno> alumnos = new HashSet<>();



}
