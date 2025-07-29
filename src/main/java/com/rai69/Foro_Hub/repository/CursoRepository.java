package com.rai69.Foro_Hub.repository;

import com.rai69.Foro_Hub.model.CursoModel;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<CursoModel, Integer> {
   Optional<CursoModel> findByNombreIgnoreCase(String nombre);
}
