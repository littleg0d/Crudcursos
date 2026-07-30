package com.proyecto.alumnos.repositories;

import com.proyecto.alumnos.model.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {
     boolean existsByEmail(String email);
     boolean existsByEmailAndIdNot(String email, Long id);

}
