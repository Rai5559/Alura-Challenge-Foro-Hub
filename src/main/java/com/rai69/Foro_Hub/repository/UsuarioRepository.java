package com.rai69.Foro_Hub.repository;

import com.rai69.Foro_Hub.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {
    Optional<UsuarioModel> findByCorreoElectronico(String correoElectronico);
}
