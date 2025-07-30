package com.rai69.Foro_Hub.service;

import com.rai69.Foro_Hub.dto.RegistroRequestDTO;
import com.rai69.Foro_Hub.dto.RegistroResponseDTO;
import com.rai69.Foro_Hub.dto.UsuarioResponseDTO;
import com.rai69.Foro_Hub.exception.DuplicatedTopicException;
import com.rai69.Foro_Hub.exception.EntityNotFoundException;
import com.rai69.Foro_Hub.model.PerfilModel;
import com.rai69.Foro_Hub.model.UsuarioModel;
import com.rai69.Foro_Hub.repository.PerfilRepository;
import com.rai69.Foro_Hub.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public RegistroResponseDTO registrarUsuario(RegistroRequestDTO registroRequest) {
        
        if (usuarioRepository.findByCorreoElectronico(registroRequest.getCorreoElectronico()).isPresent()) {
            throw new DuplicatedTopicException("Ya existe un usuario con este correo electrónico");
        }

        PerfilModel perfil = perfilRepository.findById(registroRequest.getPerfilId())
                .orElseThrow(() -> new EntityNotFoundException("Perfil no encontrado con ID: " + registroRequest.getPerfilId()));

        UsuarioModel nuevoUsuario = new UsuarioModel();
        nuevoUsuario.setNombre(registroRequest.getNombre());
        nuevoUsuario.setCorreoElectronico(registroRequest.getCorreoElectronico());
        nuevoUsuario.setContrasena(passwordEncoder.encode(registroRequest.getContrasena())); // Encriptar contraseña
        nuevoUsuario.setPerfiles(perfil);

        UsuarioModel usuarioGuardado = usuarioRepository.save(nuevoUsuario);

        return new RegistroResponseDTO(
                usuarioGuardado.getId(),
                usuarioGuardado.getNombre(),
                usuarioGuardado.getCorreoElectronico(),
                perfil.getNombre()
        );
    }

    public UsuarioResponseDTO obtenerUsuarioPorId(Integer id) {
        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
        
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getCorreoElectronico(),
                usuario.getPerfiles().getNombre()
        );
    }

    public Page<UsuarioResponseDTO> listarUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable)
                .map(usuario -> new UsuarioResponseDTO(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getCorreoElectronico(),
                        usuario.getPerfiles().getNombre()
                ));
    }

    public UsuarioModel obtenerUsuarioModel(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con ID: " + id));
    }

    public boolean verificarLogin(String email, String password) {
        return usuarioRepository.findByCorreoElectronico(email)
                .map(usuario -> passwordEncoder.matches(password, usuario.getContrasena()))
                .orElse(false);
    }
}
