package com.rai69.Foro_Hub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.rai69.Foro_Hub.dto.CursoDTO;
import com.rai69.Foro_Hub.exception.EntityNotFoundException;
import com.rai69.Foro_Hub.model.CursoModel;
import com.rai69.Foro_Hub.repository.CursoRepository;

import jakarta.transaction.Transactional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional
    public CursoDTO crearCurso(CursoDTO cursoDTO) {
        CursoModel curso = new CursoModel();
        curso.setNombre(cursoDTO.nombre());
        curso.setCategoria(cursoDTO.categoria());
        cursoRepository.save(curso);
        return new CursoDTO(curso.getId(), curso.getNombre(), curso.getCategoria());
    }

    public Page<CursoDTO> listarCursos(Pageable pageable) {
        return cursoRepository.findAll(pageable)
            .map(curso -> new CursoDTO(curso.getId(), curso.getNombre(), curso.getCategoria()));
    }

    public CursoDTO obtenerCursoPorNombre (String nombre) {
        CursoModel curso = cursoRepository.findByNombreIgnoreCase(nombre)
            .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado con nombre: " + nombre));
        return new CursoDTO(curso.getId(), curso.getNombre(), curso.getCategoria());
    }

    @Transactional
    public CursoDTO actualizarCurso(CursoDTO cursoDTO) {
        CursoModel curso = cursoRepository.findById(cursoDTO.id())
            .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado con ID: " + cursoDTO.id()));

        curso.setNombre(cursoDTO.nombre());
        curso.setCategoria(cursoDTO.categoria());
        cursoRepository.save(curso);
         return new CursoDTO(curso.getId(), curso.getNombre(), curso.getCategoria());
    }

    @Transactional
    public CursoModel obtenerOCrearCursoModel(String nombre, String categoria) {
        return cursoRepository.findByNombreIgnoreCase(nombre)
            .orElseGet(() -> {
                CursoModel nuevoCurso = new CursoModel();
                nuevoCurso.setNombre(nombre);
                nuevoCurso.setCategoria(categoria != null ? categoria : "General");
                return cursoRepository.save(nuevoCurso);
            });
    }
}
