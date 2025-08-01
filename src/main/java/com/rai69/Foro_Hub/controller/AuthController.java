package com.rai69.Foro_Hub.controller;

import com.rai69.Foro_Hub.config.JwtUtil;
import com.rai69.Foro_Hub.dto.ErrorResponseDTO;
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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            responseCode = "401", 
            description = "🔐 Credenciales incorrectas - Email o contraseña inválidos",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Error de credenciales",
                    value = """
                    {
                        "error": "Unauthorized",
                        "message": "Credenciales incorrectas. Verifica tu email y contraseña",
                        "timestamp": "2024-12-23T10:30:00Z",
                        "status": 401,
                        "path": "/auth/login"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "⚠️ Error interno del servidor",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Error interno",
                    value = """
                    {
                        "error": "Internal Server Error",
                        "message": "Error interno del servidor durante la autenticación",
                        "timestamp": "2024-12-23T10:30:00Z",
                        "status": 500,
                        "path": "/auth/login"
                    }
                    """
                )
            )
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
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getCorreoElectronico(), 
                loginRequest.getContrasena()
            )
        );

        UsuarioModel usuario = (UsuarioModel) authentication.getPrincipal();
        String jwt = jwtUtil.generateToken(usuario);

        return ResponseEntity.ok(new JwtResponseDTO(jwt, usuario.getCorreoElectronico(), usuario.getNombre()));
    }

    @PostMapping("/registro")
    @Operation(
        summary = "Registrar nuevo usuario",
        description = "Crea una nueva cuenta de usuario en el sistema. Por defecto se asigna el rol USER (ID=3)",
        tags = {"Autenticación"}
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
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
            description = "❌ Datos de entrada inválidos",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Error de validación",
                    value = """
                    {
                        "error": "Bad Request",
                        "message": "El nombre es requerido",
                        "timestamp": "2024-12-23T10:30:00Z",
                        "status": 400,
                        "path": "/auth/registro"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "409", 
            description = "⚠️ Email ya registrado",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Email duplicado",
                    value = """
                    {
                        "error": "Conflict",
                        "message": "Ya existe un usuario registrado con este email",
                        "timestamp": "2024-12-23T10:30:00Z",
                        "status": 409,
                        "path": "/auth/registro"
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "⚠️ Error interno del servidor",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponseDTO.class),
                examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                    name = "Error interno",
                    value = """
                    {
                        "error": "Internal Server Error",
                        "message": "Error interno del servidor durante el registro",
                        "timestamp": "2024-12-23T10:30:00Z",
                        "status": 500,
                        "path": "/auth/registro"
                    }
                    """
                )
            )
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
        RegistroResponseDTO respuesta = usuarioService.registrarUsuario(registroRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }
}
