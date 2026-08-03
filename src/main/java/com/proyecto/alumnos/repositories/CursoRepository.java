package com.proyecto.alumnos.repositories;

import com.proyecto.alumnos.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CursoRepository extends JpaRepository<Curso,Long> {

    boolean existsByAlumnos_Id(Long alumnoId);
    boolean existsByProfesorId(Long profesorId);
    boolean existsByIdAndAlumnos_Id(Long cursoId, Long alumnoId);
}
