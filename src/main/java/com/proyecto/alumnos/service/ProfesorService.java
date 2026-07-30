package com.proyecto.alumnos.service;

import com.proyecto.alumnos.dto.ProfesorRequestDTO;
import com.proyecto.alumnos.dto.ProfesorResponseDTO;
import com.proyecto.alumnos.exceptions.NoEncontrado;
import com.proyecto.alumnos.exceptions.YaEncontrado;
import com.proyecto.alumnos.mapper.ProfesorMapper;
import com.proyecto.alumnos.model.Profesor;
import com.proyecto.alumnos.repositories.CursoRepository;
import com.proyecto.alumnos.repositories.ProfesorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ProfesorService {
    private final ProfesorMapper profesorMapper;
    private final ProfesorRepository profesorRepository;
    private final CursoRepository cursoRepository;


    public List<ProfesorResponseDTO> listar()
    {
        return profesorMapper.toResponseList(profesorRepository.findAll());
    }
    public ProfesorResponseDTO agregar(ProfesorRequestDTO dto)
    {
        if(profesorRepository.existsByEmail(dto.getEmail()))
            throw new YaEncontrado("Ya existe un profesor con este email");
        Profesor p = profesorMapper.toEntity(dto);
        return profesorMapper.toResponse(profesorRepository.save(p));
    }
    public void borrar(Long id){
        Profesor p = buscarId(id);
        if(cursoRepository.existsByProfesorId(id))
            throw new YaEncontrado("Ya hay un profesor asignado en este curso");
        profesorRepository.delete(p);
    }

    public ProfesorResponseDTO modificar(ProfesorRequestDTO dto, Long id)
    {

        Profesor p = buscarId(id);
        if(profesorRepository.existsByEmailAndIdNot(dto.getEmail(),p.getId())){
            throw new YaEncontrado("Ya hay un profesor con ese email ");
        }
        p.setNombre(dto.getNombre());
        p.setApellido(dto.getApellido());
        p.setEmail(dto.getEmail());
        return profesorMapper.toResponse(profesorRepository.save(p));
    }

    public ProfesorResponseDTO consultarPorId(Long id)
    {
        return profesorMapper.toResponse(buscarId(id));
    }
    private Profesor buscarId(Long id)
    {
        return profesorRepository.findById(id).orElseThrow(() -> new NoEncontrado("No se encontro el profesor"));
    }


}
