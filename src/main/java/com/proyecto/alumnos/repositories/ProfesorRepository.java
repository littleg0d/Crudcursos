package com.proyecto.alumnos.repositories;

import com.proyecto.alumnos.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProfesorRepository extends JpaRepository<Profesor,Long> {
    boolean existsByEmail(String email);
    boolean existsByEmailAndIdNot(String email, Long id);
    @Query("SELECT COUNT(curso) > 0 FROM Curso curso WHERE curso.profesor.id = :profesorId")
    boolean existsProfesorEnCurso(@Param("profesorId") Long profesorId);
}
