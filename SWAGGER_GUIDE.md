# Swagger/OpenAPI Documentation

## 📚 Swagger UI Integrado

Tu aplicación Foro Hub ahora incluye documentación interactiva de la API usando Swagger/OpenAPI 3.0.

### 🔗 Acceso a Swagger UI

Una vez que la aplicación esté ejecutándose, puedes acceder a la documentación interactiva en:

- **Swagger UI**: http://localhost:8081/swagger-ui.html
- **Documentación JSON**: http://localhost:8081/v3/api-docs

### 🔐 Autenticación JWT en Swagger

Para probar endpoints que requieren autenticación:

1. **Obtener JWT Token:**
   - Ve al endpoint `POST /auth/login` en Swagger
   - Ingresa tus credenciales válidas
   - Copia el token JWT de la respuesta

2. **Configurar Autenticación:**
   - Haz clic en el botón **"Authorize"** (🔒) en la parte superior de Swagger UI
   - Ingresa el token en el formato: `Bearer tu-jwt-token-aqui`
   - Haz clic en "Authorize"

3. **Probar Endpoints Protegidos:**
   - Ahora puedes probar endpoints que requieren autenticación
   - El icono de candado (🔒) indica endpoints que requieren autenticación

### 📋 Endpoints Documentados

#### Autenticación
- `POST /auth/login` - Iniciar sesión y obtener JWT token
- `POST /auth/registro` - Registrar nuevo usuario

#### Tópicos
- `POST /topicos` - Crear nuevo tópico (requiere autenticación)
- `GET /topicos/{id}` - Obtener tópico por ID
- `GET /topicos` - Listar todos los tópicos (con paginación)
- `PUT /topicos/{id}` - Actualizar tópico (requiere autenticación)
- `DELETE /topicos/{id}` - Eliminar tópico (requiere rol ADMIN)

#### Cursos
- `POST /cursos` - Crear nuevo curso (requiere rol ADMIN o MODERADOR)
- `GET /cursos` - Listar todos los cursos
- `GET /cursos/{id}` - Obtener curso por ID

#### Usuarios
- `GET /usuarios/{id}` - Obtener usuario por ID

#### Perfiles/Roles
- `GET /perfiles` - Listar perfiles disponibles

### 🎯 Características de Swagger UI

- **Interfaz Interactiva**: Prueba endpoints directamente desde el navegador
- **Documentación Automática**: Los esquemas de respuesta se generan automáticamente
- **Validación de Parámetros**: Swagger valida los parámetros antes de enviar requests
- **Ejemplos de Respuesta**: Cada endpoint muestra ejemplos de respuestas exitosas y errores
- **Soporte JWT**: Configuración integrada para autenticación Bearer Token

### 🛠️ Configuración Técnica

#### Dependencias Maven
```xml
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.2.0</version>
</dependency>
```

#### Configuración Spring Boot
```properties
# application.properties
springdoc.api-docs.path=/v3/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
```

#### Spring Security
Los endpoints de Swagger están configurados como públicos en `SecurityConfiguration`:
```java
.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
```

### 💡 Consejos de Uso

1. **Testing de API**: Usa Swagger como herramienta de testing para validar respuestas
2. **Documentación para Equipos**: Comparte la URL de Swagger UI con otros desarrolladores
3. **Validación de Esquemas**: Verifica que los DTOs se muestren correctamente
4. **Depuración**: Usa los códigos de respuesta HTTP para identificar problemas

### 📝 Notas Importantes

- Los endpoints requieren autenticación JWT donde se especifica con el icono 🔒
- Los roles ADMIN, MODERADOR y USER tienen diferentes permisos
- Para registrar un usuario, usa `perfilId`: 1=ADMIN, 2=MODERADOR, 3=USER
- La paginación está disponible en endpoints de listado

---

¡Ahora tienes documentación completa y interactiva de tu API! 🚀
