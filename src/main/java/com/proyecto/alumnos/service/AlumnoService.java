package com.proyecto.alumnos.service;

import com.proyecto.alumnos.dto.AlumnoResponseDTO;

import com.proyecto.alumnos.exceptions.YaEncontrado;
import com.proyecto.alumnos.exceptions.NoEncontrado;
import com.proyecto.alumnos.dto.AlumnoRequestDTO;
import com.proyecto.alumnos.mapper.AlumnoMapper;
import com.proyecto.alumnos.model.Alumno;
import com.proyecto.alumnos.repositories.AlumnoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class AlumnoService {
    private final AlumnoRepository alumnoRepository;
    private final AlumnoMapper alumnoMapper;


    public Alumno buscarEntidad(Long id){
        return alumnoRepository.findById(id).orElseThrow(() -> new NoEncontrado("Alumno no encontrado"));
    }


    public AlumnoResponseDTO consultarAlumnoPorId(Long id)
    {
        return alumnoMapper.toResponse(buscarEntidad(id));
    }

    public List<AlumnoResponseDTO> listar() {
        return alumnoMapper.toResponseList(alumnoRepository.findAll());
    }

    @Transactional
    public AlumnoResponseDTO guardar(AlumnoRequestDTO alumno){
        if(alumnoRepository.existsByEmail(alumno.getEmail()))
            throw new YaEncontrado("Ya existe un alumno con este email");
        Alumno alu = alumnoMapper.toEntity(alumno);
        return alumnoMapper.toResponse(alumnoRepository.save(alu));
    }

    @Transactional
    public void borrar(Long id)
    {
        Alumno alu = buscarEntidad(id);
        if(alumnoRepository.existsAlumnoEnCurso(id))
            throw new YaEncontrado("No se puede borrar un alumno que tiene cursos asignados");
        alumnoRepository.delete(alu);

    }
    @Transactional
    public AlumnoResponseDTO modificar(AlumnoRequestDTO dto, Long id)
    {
        Alumno alumnoEncontrado = buscarEntidad(id);
        if(alumnoRepository.existsByEmailAndIdNot(dto.getEmail(),id))
            throw new YaEncontrado("Email ya encontrado en otro alumno ");
        alumnoEncontrado.setNombre(dto.getNombre());
        alumnoEncontrado.setApellido(dto.getApellido());
        alumnoEncontrado.setEmail(dto.getEmail());
        alumnoEncontrado.setFechaNacimiento(dto.getFechaNacimiento());
        return alumnoMapper.toResponse(alumnoRepository.save(alumnoEncontrado));
    }





}
