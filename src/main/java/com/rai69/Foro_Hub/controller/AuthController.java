package com.rai69.Foro_Hub.controller;

import com.rai69.Foro_Hub.config.JwtUtil;
import com.rai69.Foro_Hub.dto.JwtResponseDTO;
import com.rai69.Foro_Hub.dto.LoginRequestDTO;
import com.rai69.Foro_Hub.dto.RegistroRequestDTO;
import com.rai69.Foro_Hub.dto.RegistroResponseDTO;
import com.rai69.Foro_Hub.model.UsuarioModel;
import com.rai69.Foro_Hub.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Autenticación", description = "Endpoints para autenticación y registro de usuarios")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    @Operation(
        summary = "Iniciar sesión de usuario",
        description = "Autentica un usuario con email y contraseña, retorna un token JWT válido por 24 horas",
        tags = {"Autenticación"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "✅ Login exitoso - Token JWT generado correctamente",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = JwtResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Login exitoso",
                    value = """
                    {
                        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
                        "correoElectronico": "admin@forhub.com",
                        "nombre": "Administrador"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "❌ Credenciales incorrectas - Email o contraseña inválidos",
            content = @Content(
                mediaType = "text/plain",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Error de credenciales",
                    value = "Credenciales incorrectas"
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "⚠️ Error interno del servidor"
        )
    })
    public ResponseEntity<?> login(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Credenciales de login (email y contraseña)",
            required = true,
            schema = @Schema(implementation = LoginRequestDTO.class),
            example = """
            {
                "correoElectronico": "admin@forhub.com",
                "contrasena": "password123"
            }
            """
        )
        @Valid @RequestBody LoginRequestDTO loginRequest) {
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
    @Operation(
        summary = "Registrar nuevo usuario",
        description = "Crea una nueva cuenta de usuario en el sistema. Por defecto se asigna el rol USER (ID=3)",
        tags = {"Autenticación"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "✅ Usuario registrado exitosamente",
            content = @Content(
                mediaType = "application/json", 
                schema = @Schema(implementation = RegistroResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Registro exitoso",
                    value = """
                    {
                        "id": 1,
                        "nombre": "Juan Pérez",
                        "correoElectronico": "juan@ejemplo.com",
                        "perfil": "USER"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "400", 
            description = "❌ Error en el registro",
            content = @Content(
                mediaType = "text/plain",
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Error de validación",
                    value = "Error en el registro: El correo electrónico ya está registrado"
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "⚠️ Error interno del servidor"
        )
    })
    public ResponseEntity<?> registro(
        @io.swagger.v3.oas.annotations.Parameter(
            description = "Datos del nuevo usuario. PerfilId: 1=ADMIN, 2=MODERADOR, 3=USER",
            required = true,
            schema = @Schema(implementation = RegistroRequestDTO.class),
            example = """
            {
                "nombre": "Juan Pérez",
                "correoElectronico": "juan@ejemplo.com",
                "contrasena": "password123",
                "perfilId": 3
            }
            """
        )
        @Valid @RequestBody RegistroRequestDTO registroRequest) {
        try {
            RegistroResponseDTO respuesta = usuarioService.registrarUsuario(registroRequest);
            return ResponseEntity.ok(respuesta);
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error en el registro: " + e.getMessage());
        }
    }
}
