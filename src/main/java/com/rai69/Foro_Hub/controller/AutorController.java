package com.rai69.Foro_Hub.controller;

import com.rai69.Foro_Hub.dto.AutorRequestDTO;
import com.rai69.Foro_Hub.dto.AutorResponseDTO;
import com.rai69.Foro_Hub.exception.EntityNotFoundException;
import com.rai69.Foro_Hub.service.AutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autores")
public class AutorController {

    @Autowired
    private AutorService autorService;

    @PostMapping
    public ResponseEntity<AutorResponseDTO> crearAutor(@Valid @RequestBody AutorRequestDTO autorDTO) {
        try {
            AutorResponseDTO nuevoAutor = autorService.crearAutor(autorDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAutor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> obtenerAutor(@PathVariable Integer id) {
        try {
            AutorResponseDTO autor = autorService.obtenerAutorPorId(id);
            return ResponseEntity.ok(autor);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<AutorResponseDTO>> listarAutores(
            @PageableDefault(size = 10, page = 0, sort = "nombre", direction = Sort.Direction.ASC) 
            Pageable pageable) {
        try {
            Page<AutorResponseDTO> autores = autorService.listarAutores(pageable);
            return ResponseEntity.ok(autores);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AutorResponseDTO> actualizarAutor(
            @PathVariable Integer id,
            @Valid @RequestBody AutorRequestDTO autorDTO) {
        try {
            AutorResponseDTO autorActualizado = autorService.actualizarAutor(id, autorDTO);
            return ResponseEntity.ok(autorActualizado);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
