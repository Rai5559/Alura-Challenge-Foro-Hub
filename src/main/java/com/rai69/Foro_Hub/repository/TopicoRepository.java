package com.rai69.Foro_Hub.repository;

import com.rai69.Foro_Hub.model.TopicoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TopicoRepository extends JpaRepository<TopicoModel, Integer> {
    
    @Query("SELECT COUNT(t) > 0 FROM TopicoModel t WHERE t.titulo = :titulo AND t.mensaje = :mensaje")
    boolean existsByTituloAndMensaje(@Param("titulo") String titulo, @Param("mensaje") String mensaje);
}

