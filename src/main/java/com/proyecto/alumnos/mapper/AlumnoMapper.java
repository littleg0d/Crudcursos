package com.proyecto.alumnos.mapper;

import com.proyecto.alumnos.dto.AlumnoRequestDTO;
import com.proyecto.alumnos.dto.AlumnoResponseDTO;
import com.proyecto.alumnos.model.Alumno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")

public interface AlumnoMapper {

    @Mapping(target = "id", ignore = true)
    Alumno toEntity(AlumnoRequestDTO dto);

    AlumnoResponseDTO toResponse(Alumno alumno);

    List<AlumnoResponseDTO> toResponseList(List<Alumno> alumnos);

    List<AlumnoResponseDTO> toResponseSet(Set<Alumno> alumnos);
}
