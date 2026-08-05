package com.proyecto.alumnos.controller;

import com.proyecto.alumnos.dto.AlumnoResponseDTO;
import com.proyecto.alumnos.dto.CursoRequestDTO;
import com.proyecto.alumnos.dto.CursoResponseDTO;
import com.proyecto.alumnos.service.CursoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
@RequiredArgsConstructor
@Tag(name = "Cursos", description = "CRUD de cursos y relaciones con profesores y alumnos")
public class CursoController {
    private final CursoService cursoService;

    @PostMapping
    @Operation(summary = "Crear curso", description = "Crea un curso sin profesor ni alumnos")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Curso creado"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en los datos enviados")
    })
    public ResponseEntity<CursoResponseDTO> crearCurso(@Valid @RequestBody CursoRequestDTO dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(cursoService.agregar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar cursos", description = "Lista todos los cursos")
    @ApiResponse(responseCode = "200", description = "Lista de cursos")

    public ResponseEntity<List<CursoResponseDTO>> listarCursos() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cursoService.listar());
    }

    @GetMapping("/{id}/alumnos")
    @Operation(summary = "Listar alumnos de un curso", description = "Lista los alumnos del curso")
    @ApiResponse(responseCode = "200", description = "Lista de alumnos")
    public ResponseEntity<List<AlumnoResponseDTO>> listarAlumnos(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cursoService.listarAlumnos(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar curso", description = "Busca un curso por id")
    @ApiResponse(responseCode = "200", description = "Curso encontrado")

    public ResponseEntity<CursoResponseDTO> buscarCursoPorId(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cursoService.consultarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar curso", description = "Modifica los datos del curso")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Curso modificado"),
            @ApiResponse(responseCode = "400", description = "Error de validacion en los datos enviados")
    })
    public ResponseEntity<CursoResponseDTO> modificarCurso(
            @Valid @RequestBody CursoRequestDTO dto,
            @PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cursoService.modificar(dto,id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar curso", description = "Borra un curso si no tiene alumnos")
    @ApiResponse(responseCode = "204", description = "Curso borrado")
    public ResponseEntity<Void> borrarCurso(@PathVariable Long id) {
        cursoService.borrar(id);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("/{cursoId}/alumnos/{alumnoId}")
    @Operation(summary = "Agregar alumno al curso", description = "Agrega un alumno si hay cupo")
    @ApiResponse(responseCode = "201", description = "Alumno agregado al curso")
    public ResponseEntity<Void> agregarAlumno(@PathVariable Long cursoId, @PathVariable Long alumnoId) {
        cursoService.agregarAlumno(alumnoId, cursoId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }
    @DeleteMapping("/{cursoId}/alumnos/{alumnoId}")
    @Operation(summary = "Quitar alumno del curso", description = "Quita un alumno del curso")
    @ApiResponse(responseCode = "204", description = "Alumno quitado del curso")

    public ResponseEntity<Void> borrarAlumno(@PathVariable Long cursoId, @PathVariable Long alumnoId) {
        cursoService.quitarAlumno(alumnoId, cursoId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }





    @DeleteMapping("/{cursoId}/profesores")
    @Operation(summary = "Quitar profesor del curso", description = "Quita el profesor del curso")
    @ApiResponse(responseCode = "204", description = "Profesor quitado del curso")

    public ResponseEntity<Void> borrarProfesor(@PathVariable Long cursoId) {
        cursoService.quitarProfesor(cursoId);
        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostMapping("/{cursoId}/profesores/{profesorId}")
    @Operation(summary = "Agregar profesor al curso", description = "Agrega un profesor si el curso no tiene uno")
    @ApiResponse(responseCode = "201", description = "Profesor asignado al curso")

    public ResponseEntity<Void> agregarProfesor(@PathVariable Long cursoId, @PathVariable Long profesorId) {
        cursoService.agregarProfesor(profesorId, cursoId);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @PutMapping("/{cursoId}/profesores/{profesorId}")
    @Operation(summary = "Cambiar profesor del curso", description = "Cambia el profesor del curso")
    @ApiResponse(responseCode = "200", description = "Profesor cambiado")

    public ResponseEntity<Void> cambiarProfesor(@PathVariable Long cursoId, @PathVariable Long profesorId) {
        cursoService.cambiarProfesor(cursoId, profesorId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
