package com.proyecto.alumnos.mapper;

import com.proyecto.alumnos.dto.ProfesorRequestDTO;
import com.proyecto.alumnos.dto.ProfesorResponseDTO;
import com.proyecto.alumnos.model.Profesor;
import org.mapstruct.Mapper;

import java.util.List;



@Mapper(componentModel = "spring")
public interface ProfesorMapper {
    List<ProfesorResponseDTO> toResponseList(List<Profesor> profesores);

    Profesor toEntity(ProfesorRequestDTO dto);
    ProfesorResponseDTO toResponse(Profesor p);


}