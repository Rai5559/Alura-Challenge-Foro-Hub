package com.rai69.Foro_Hub.service;

import com.rai69.Foro_Hub.dto.AutorRequestDTO;
import com.rai69.Foro_Hub.dto.AutorResponseDTO;
import com.rai69.Foro_Hub.exception.EntityNotFoundException;
import com.rai69.Foro_Hub.model.UsuarioModel;
import com.rai69.Foro_Hub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AutorService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public AutorResponseDTO crearAutor(AutorRequestDTO autorDTO) {
        UsuarioModel autor = new UsuarioModel();
        autor.setNombre(autorDTO.nombre());
        autor.setCorreoElectronico(autorDTO.email());
        autor.setContrasena(passwordEncoder.encode(autorDTO.contrasena()));
        
        UsuarioModel autorGuardado = usuarioRepository.save(autor);
        
        return new AutorResponseDTO(
            autorGuardado.getId(),
            autorGuardado.getNombre(),
            autorGuardado.getCorreoElectronico()
        );
    }

    public AutorResponseDTO obtenerAutorPorId(Integer id) {
        UsuarioModel autor = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Autor no encontrado con ID: " + id));
        
        return new AutorResponseDTO(
            autor.getId(),
            autor.getNombre(),
            autor.getCorreoElectronico()
        );
    }

    public Page<AutorResponseDTO> listarAutores(Pageable pageable) {
        Page<UsuarioModel> autores = usuarioRepository.findAll(pageable);
        return autores.map(this::convertirAResponseDTO);
    }

    @Transactional
    public AutorResponseDTO actualizarAutor(Integer id, AutorRequestDTO autorDTO) {
        UsuarioModel autor = usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Autor no encontrado con ID: " + id));
        
        autor.setNombre(autorDTO.nombre());
        autor.setCorreoElectronico(autorDTO.email());
        if (autorDTO.contrasena() != null && !autorDTO.contrasena().isEmpty()) {
            autor.setContrasena(passwordEncoder.encode(autorDTO.contrasena()));
        }
        
        UsuarioModel autorActualizado = usuarioRepository.save(autor);
        
        return new AutorResponseDTO(
            autorActualizado.getId(),
            autorActualizado.getNombre(),
            autorActualizado.getCorreoElectronico()
        );
    }

    public UsuarioModel obtenerAutorModel(Integer id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(
                "Usuario no encontrado con ID: " + id));
    }

    public boolean verificarContrasena(String contrasenaIngresada, String contrasenaHasheada) {
        return passwordEncoder.matches(contrasenaIngresada, contrasenaHasheada);
    }

    public boolean verificarLogin(String email, String contrasena) {
        UsuarioModel usuario = usuarioRepository.findByCorreoElectronico(email)
            .orElse(null);
        
        if (usuario == null) {
            return false;
        }
        
        return passwordEncoder.matches(contrasena, usuario.getContrasena());
    }

    private AutorResponseDTO convertirAResponseDTO(UsuarioModel autor) {
        return new AutorResponseDTO(
            autor.getId(),
            autor.getNombre(),
            autor.getCorreoElectronico()
        );
    }
}
