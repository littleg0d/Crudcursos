package com.proyecto.alumnos.service;


import com.proyecto.alumnos.dto.*;
import com.proyecto.alumnos.exceptions.CupoLleno;
import com.proyecto.alumnos.exceptions.CursoNoVacio;
import com.proyecto.alumnos.exceptions.NoEncontrado;
import com.proyecto.alumnos.exceptions.YaEncontrado;
import com.proyecto.alumnos.mapper.AlumnoMapper;
import com.proyecto.alumnos.mapper.CursoMapper;

import com.proyecto.alumnos.model.Alumno;
import com.proyecto.alumnos.model.Curso;
import com.proyecto.alumnos.model.Profesor;
import com.proyecto.alumnos.repositories.CursoRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CursoService {
    private final CursoRepository cursoRepository;
    private final CursoMapper cursoMapper;
    private final AlumnoMapper alumnoMapper;
    private final AlumnoService alumnoService;
    private final ProfesorService profesorService;
    @Transactional
    public CursoResponseDTO agregar(CursoRequestDTO dto){
        Curso c = cursoMapper.toEntity(dto);
        c.setProfesor(null);
        return cursoMapper.toResponse(cursoRepository.save(c));
    }
    @Transactional
    public void borrar(Long id){
        Curso c = buscarEntidad(id);
        if(!c.getAlumnos().isEmpty())
            throw new CursoNoVacio("El curso no esta vacio para eliminarlo ");
        cursoRepository.delete(c);
    }

    @Transactional
    public CursoResponseDTO modificar(CursoRequestDTO dto, Long id)
    {
        Curso c = buscarEntidad(id);
        c.setNombre(dto.getNombre());
        c.setFechaInicio(dto.getFechaInicio());
        c.setFechaFin(dto.getFechaFin());
        if(dto.getCupoMax() < c.getAlumnos().size())
            throw new CupoLleno("El cupo maximo no puede ser menor a la cantidad de alumnos actuales");
        c.setCupoMax(dto.getCupoMax());
        return cursoMapper.toResponse(cursoRepository.save(c));
    }


    public List<CursoResponseDTO> listar()
    {
        return cursoMapper.toResponseList(cursoRepository.findAll());
    }

    public CursoResponseDTO consultarPorId(Long id)
    {
        Curso c = buscarEntidad(id);
        return cursoMapper.toResponse(c);
    }




    private Curso buscarEntidad(Long id){
        return cursoRepository.findById(id).orElseThrow(() -> new NoEncontrado("No se encontro el curso"));
    }

    @Transactional
    public void agregarProfesor(Long profesorId, Long cursoId)
        {
            Curso c = buscarEntidad(cursoId);
            if(c.getProfesor() != null)
            {
                if(c.getProfesor().getId().equals(profesorId))
                    throw new YaEncontrado("El profesor ya esta asignado a este curso");

                throw new YaEncontrado("El curso ya tiene un profesor asignado");


            }
            Profesor p = profesorService.buscarEntidad(profesorId);
            c.setProfesor(p);
            cursoRepository.save(c);

        }
    @Transactional
    public void quitarProfesor(Long cursoId)
    {
        Curso c = buscarEntidad(cursoId);
        if (c.getProfesor() == null)
            throw new NoEncontrado("El curso no tiene profesor asignado");
        c.setProfesor(null);
        cursoRepository.save(c);
    }
    @Transactional
    public void cambiarProfesor(Long cursoId, Long profesorId)
    {
        Curso c = buscarEntidad(cursoId);
        Profesor profesorActual = c.getProfesor();

        if (profesorActual == null)
            throw new NoEncontrado("El curso no tiene profesor asignado ");
        if (profesorActual.getId().equals(profesorId))
            throw new YaEncontrado("El profesor ya esta asignado a este curso");
        Profesor profesorNuevo = profesorService.buscarEntidad(profesorId);
        c.setProfesor(profesorNuevo);
        cursoRepository.save(c);
    }

    @Transactional
    public void agregarAlumno(Long alumnoId, Long cursoId)
    {
        Curso c = buscarEntidad(cursoId);
        Alumno alu = alumnoService.buscarEntidad(alumnoId);
        if(cursoRepository.existsByIdAndAlumnos_Id(cursoId,alumnoId))
            throw new YaEncontrado("El alumno ya esta asignado a este curso");
        if(c.getCupoMax() <= c.getAlumnos().size())
            throw new CupoLleno("El curso ya esta lleno");
        c.getAlumnos().add(alu);
        cursoRepository.save(c);
    }
    @Transactional
    public void quitarAlumno(Long alumnoId, Long cursoId)
    {
        Curso c = buscarEntidad(cursoId);
        boolean encontrado = c.getAlumnos().removeIf(a -> a.getId().equals(alumnoId));

        if (!encontrado)
            throw new NoEncontrado("El alumno no esta asignado a este curso");
        cursoRepository.save(c);
    }

    @Transactional(readOnly = true)
    public List<AlumnoResponseDTO> listarAlumnos(Long cursoId)
    {
        Curso c = buscarEntidad(cursoId);
        return alumnoMapper.toResponseSet(c.getAlumnos());
    }


}
