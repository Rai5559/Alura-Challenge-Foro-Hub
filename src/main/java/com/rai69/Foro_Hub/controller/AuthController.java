package com.rai69.Foro_Hub.controller;

import com.rai69.Foro_Hub.config.JwtUtil;
import com.rai69.Foro_Hub.dto.JwtResponseDTO;
import com.rai69.Foro_Hub.dto.LoginRequestDTO;
import com.rai69.Foro_Hub.dto.RegistroRequestDTO;
import com.rai69.Foro_Hub.dto.RegistroResponseDTO;
import com.rai69.Foro_Hub.model.UsuarioModel;
import com.rai69.Foro_Hub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getCorreoElectronico(), 
                    loginRequest.getContrasena()
                )
            );

            UsuarioModel usuario = (UsuarioModel) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(usuario);

            return ResponseEntity.ok(new JwtResponseDTO(jwt, usuario.getCorreoElectronico(), usuario.getNombre()));
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Credenciales incorrectas");
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registro(@Valid @RequestBody RegistroRequestDTO registroRequest) {
        try {
            RegistroResponseDTO respuesta = usuarioService.registrarUsuario(registroRequest);
            return ResponseEntity.ok(respuesta);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en el registro: " + e.getMessage());
        }
    }
}
