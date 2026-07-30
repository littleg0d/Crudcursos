package com.proyecto.alumnos.controller;
import com.proyecto.alumnos.dto.ProfesorRequestDTO;
import com.proyecto.alumnos.dto.ProfesorResponseDTO;
import com.proyecto.alumnos.service.ProfesorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profesores")
@AllArgsConstructor
@Tag(name = "Profesores", description = "CRUD de profesores")
public class ProfesorController {
    private final ProfesorService profesorService;


    @GetMapping
    @Operation(summary = "Listar profesores", description = "Lista todos los profesores")
    @ApiResponse(responseCode = "200", description = "Lista de profesores")
    public ResponseEntity<List<ProfesorResponseDTO>> listarProfesores(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(profesorService.listar());
    }
    @PostMapping
    @Operation(summary = "Crear profesor", description = "Crea un profesor nuevo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Profesor creado"),
            @ApiResponse(responseCode = "400", description = "Datos del profesor invalidos"),
            @ApiResponse(responseCode = "409", description = "Ya existe un profesor con ese email")
    })
    public ResponseEntity<ProfesorResponseDTO> crearProfesor(@Valid @RequestBody ProfesorRequestDTO dto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(profesorService.agregar(dto));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar profesor", description = "Busca un profesor por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profesor encontrado"),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado")
    })
    public ResponseEntity<ProfesorResponseDTO> obtenerProfesor(@PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(profesorService.consultarPorId(id));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar profesor", description = "Borra un profesor si no tiene cursos")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Profesor borrado"),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado"),
            @ApiResponse(responseCode = "409", description = "El profesor tiene cursos asignados")
    })
    public ResponseEntity<Void> borrarProfesor( @PathVariable Long id){
        profesorService.borrar(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @PutMapping("/{id}")
    @Operation(summary = "Modificar profesor", description = "Modifica los datos de un profesor")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profesor modificado"),
            @ApiResponse(responseCode = "400", description = "Datos del profesor invalidos"),
            @ApiResponse(responseCode = "404", description = "Profesor no encontrado"),
            @ApiResponse(responseCode = "409", description = "El email pertenece a otro profesor")
    })
    public ResponseEntity<ProfesorResponseDTO> modificarProfesor(@Valid @RequestBody ProfesorRequestDTO dto, @PathVariable Long id )
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(profesorService.modificar(dto,id));
    }



}
