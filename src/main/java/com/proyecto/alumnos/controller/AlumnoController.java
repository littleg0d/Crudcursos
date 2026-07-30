package com.proyecto.alumnos.controller;

import com.proyecto.alumnos.dto.AlumnoRequestDTO;
import com.proyecto.alumnos.dto.AlumnoResponseDTO;
import com.proyecto.alumnos.service.AlumnoService;
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
@RequestMapping("/alumnos")
@AllArgsConstructor
@Tag(name = "Alumnos", description = "CRUD de alumnos")
public class AlumnoController {
    private final AlumnoService alumnoService;


    @GetMapping
    @Operation(summary = "Listar alumnos", description = "Lista todos los alumnos")
    @ApiResponse(responseCode = "200", description = "Lista de alumnos")
    public ResponseEntity<List<AlumnoResponseDTO>> listarAlumnos(){
       return ResponseEntity
                .status(HttpStatus.OK)
                .body(alumnoService.listar());
    }
    @PostMapping
    @Operation(summary = "Crear alumno", description = "Crea un alumno nuevo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alumno creado"),
            @ApiResponse(responseCode = "400", description = "Datos del alumno invalidos"),
            @ApiResponse(responseCode = "409", description = "Ya existe un alumno con ese email")
    })
    public ResponseEntity<AlumnoResponseDTO> crearAlumno (@Valid @RequestBody AlumnoRequestDTO dto){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(alumnoService.guardar(dto));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Buscar alumno", description = "Busca un alumno por id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alumno encontrado"),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado")
    })
    public ResponseEntity<AlumnoResponseDTO> buscarAlumnoPorId( @PathVariable  Long id){
       return ResponseEntity
                .status(HttpStatus.OK)
                .body(alumnoService.consultarAlumnoPorId(id));
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar alumno", description = "Borra un alumno si no tiene cursos")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Alumno borrado"),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado"),
            @ApiResponse(responseCode = "409", description = "El alumno tiene cursos asignados")
    })
    public ResponseEntity<Void> borrarAlumno( @PathVariable Long id){
        alumnoService.borrar(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    @PutMapping("/{id}")
    @Operation(summary = "Modificar alumno", description = "Modifica los datos de un alumno")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alumno modificado"),
            @ApiResponse(responseCode = "400", description = "Datos del alumno invalidos"),
            @ApiResponse(responseCode = "404", description = "Alumno no encontrado"),
            @ApiResponse(responseCode = "409", description = "El email pertenece a otro alumno")
    })
    public ResponseEntity<AlumnoResponseDTO> modificarAlumno(@Valid @RequestBody AlumnoRequestDTO dto,  @PathVariable Long id )
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(alumnoService.modificar(dto,id));
    }



}
