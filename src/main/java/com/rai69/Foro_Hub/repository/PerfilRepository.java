package com.rai69.Foro_Hub.repository;

import com.rai69.Foro_Hub.model.PerfilModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepository extends JpaRepository<PerfilModel, Integer> {
}
