package com.proyecto.alumnos.mapper;

import com.proyecto.alumnos.dto.CursoRequestDTO;
import com.proyecto.alumnos.dto.CursoResponseDTO;
import com.proyecto.alumnos.model.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    @Mapping(source = "profesor.id", target = "profesorId")
    CursoResponseDTO toResponse(Curso curso);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "profesor", ignore = true)
    @Mapping(target = "alumnos", ignore = true)
    Curso toEntity(CursoRequestDTO cursoDTO);

    List<CursoResponseDTO> toResponseList(List<Curso> lista);
}
