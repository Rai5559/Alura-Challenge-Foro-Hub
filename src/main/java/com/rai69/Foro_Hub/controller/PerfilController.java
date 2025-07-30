package com.rai69.Foro_Hub.controller;

import com.rai69.Foro_Hub.model.PerfilModel;
import com.rai69.Foro_Hub.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/perfiles")
public class PerfilController {

    @Autowired
    private PerfilRepository perfilRepository;

    @GetMapping
    public ResponseEntity<List<PerfilModel>> listarPerfiles() {
        List<PerfilModel> perfiles = perfilRepository.findAll();
        return ResponseEntity.ok(perfiles);
    }
}
