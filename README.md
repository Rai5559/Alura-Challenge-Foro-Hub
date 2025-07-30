<div align="center">

# 🚀 Foro Hub API

### *Una plataforma completa de foro de discusión con autenticación JWT y sistema de roles*

---

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen?style=for-the-badge&logo=spring)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17+-orange?style=for-the-badge&logo=openjdk)](https://openjdk.org/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17.5-blue?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)
[![JWT](https://img.shields.io/badge/JWT-Authentication-red?style=for-the-badge&logo=jsonwebtokens)](https://jwt.io/)

[API en Vivo](http://localhost:8081) • [Reportar Bug](https://github.com/Rai5559/Alura-Challenge-Foro-Hub/issues)

</div>

---

## 🎯 Descripción del Proyecto

**Foro Hub** es una API REST robusta y escalable diseñada para gestionar un foro de discusión moderno. Construida con Spring Boot 3.5.3, ofrece un sistema completo de autenticación JWT, manejo de roles granular y una arquitectura limpia y mantenible.

### ✨ ¿Por qué Foro Hub?

- 🔐 **Seguridad de primera clase** - Autenticación JWT con roles jerárquicos
- 🏗️ **Arquitectura limpia** - Separación clara de responsabilidades y buenas prácticas
- 🚀 **Escalable y mantenible** - Diseño modular con Spring Boot y PostgreSQL
- 🔄 **Migraciones automáticas** - Control de versiones de base de datos con Flyway
- 📊 **API REST completa** - Endpoints bien estructurados y documentados

## 🌟 Características Principales

<table>
<tr>
<td width="50%">

### 🔐 Autenticación & Seguridad
- ✅ **JWT Tokens** con expiración configurable
- ✅ **Roles jerárquicos** (ADMIN, MODERADOR, USER)
- ✅ **Spring Security 6+** con configuración moderna
- ✅ **Encriptación BCrypt** para contraseñas
- ✅ **Autorización por endpoint** granular

</td>
<td width="50%">

### 📊 Gestión de Contenido
- ✅ **CRUD completo** para tópicos y cursos
- ✅ **Sistema de usuarios** con perfiles
- ✅ **Paginación** automática en listados
- ✅ **Validación de datos** robusta
- ✅ **Prevención de duplicados** inteligente

</td>
</tr>
<tr>
<td width="50%">

### 🛠️ Experiencia de Desarrollo
- ✅ **Variables de entorno** seguras
- ✅ **Hot reload** con Spring DevTools
- ✅ **Manejo de excepciones** robusto
- ✅ **Logs estructurados** para debugging
- ✅ **Arquitectura modular** fácil de extender

</td>
<td width="50%">

### 🏗️ Arquitectura & Base de Datos
- ✅ **PostgreSQL** como base de datos principal
- ✅ **Flyway** para migraciones automáticas
- ✅ **JPA/Hibernate** para persistencia
- ✅ **Transacciones** manejadas correctamente
- ✅ **Pool de conexiones** optimizado

</td>
</tr>
</table>

## 🛠️ Stack Tecnológico

<div align="center">

| Categoría | Tecnología | Versión | Propósito |
|:---------:|:----------:|:-------:|-----------|
| **Backend** | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=flat&logo=spring&logoColor=white) | 3.5.3 | Framework principal |
| **Seguridad** | ![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat&logo=springsecurity&logoColor=white) | 6+ | Autenticación y autorización |
| **Base de Datos** | ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=flat&logo=postgresql&logoColor=white) | 17.5 | Base de datos relacional |
| **Persistencia** | ![JPA](https://img.shields.io/badge/JPA/Hibernate-59666C?style=flat&logo=hibernate&logoColor=white) | - | ORM y persistencia |
| **Autenticación** | ![JWT](https://img.shields.io/badge/JWT-000000?style=flat&logo=jsonwebtokens&logoColor=white) | 0.12.3 | Tokens de autenticación |
| **Migraciones** | ![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=flat&logo=flyway&logoColor=white) | - | Control de versiones BD |
| **Build** | ![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apache-maven&logoColor=white) | 3.6+ | Gestión de dependencias |

</div>

## � Quick Start

### �📋 Prerrequisitos

Asegúrate de tener instalado:

- ☕ **Java 17+** - [Descargar OpenJDK](https://openjdk.org/)
- 🐘 **PostgreSQL 17.5+** - [Descargar PostgreSQL](https://www.postgresql.org/download/)
- 📦 **Maven 3.6+** - [Descargar Maven](https://maven.apache.org/download.cgi)

### ⚡ Instalación Rápida

```bash
# 1. Clonar el repositorio
git clone https://github.com/Rai5559/Alura-Challenge-Foro-Hub.git
cd Alura-Challenge-Foro-Hub

# 2. Configurar variables de entorno
cp .env.template .env
# Editar .env con tus valores (ver guía abajo)

# 3. Crear base de datos
createdb foro-hub

# 4. Ejecutar la aplicación
mvn spring-boot:run
```

🎉 **¡Listo!** La aplicación estará disponible en: http://localhost:8081

### 🔧 Configuración Detallada

<details>
<summary><b>📁 Variables de Entorno (.env)</b></summary>

```bash
# Base de datos PostgreSQL
DB_URL=jdbc:postgresql://localhost:5432/foro-hub
DB_USER=postgres
DB_PASSWORD=tu_password

# JWT Configuration (¡Genera una clave segura!)
JWT_SECRET=tu_clave_secreta_jwt_minimo_32_caracteres_muy_segura
JWT_EXPIRATION=86400000  # 24 horas en milisegundos
```

> 💡 **Tip:** Para generar una clave JWT segura:
> ```bash
> openssl rand -hex 32
> ```

📖 **Guía completa:** [ENVIRONMENT_SETUP.md](ENVIRONMENT_SETUP.md)

</details>

<details>
<summary><b>🗄️ Configuración de PostgreSQL</b></summary>

```sql
-- Conectar como superusuario
psql -U postgres

-- Crear base de datos
CREATE DATABASE "foro-hub";

-- Crear usuario (opcional)
CREATE USER foro_user WITH ENCRYPTED PASSWORD 'secure_password';
GRANT ALL PRIVILEGES ON DATABASE "foro-hub" TO foro_user;
```

> 📝 **Nota:** Las tablas se crean automáticamente con Flyway al iniciar la aplicación.

</details>

## 📚 API Endpoints

### � Endpoints Principales

La aplicación está disponible en: **http://localhost:8081**

Puedes probar los endpoints usando herramientas como:
- 🔧 **Postman** - Para testing interactivo
- �️ **cURL** - Para testing desde terminal  
- 🌐 **Navegador** - Para endpoints GET públicos

## 🔐 Sistema de Autenticación

### 🎯 Flujo de Autenticación

```mermaid
sequenceDiagram
    participant C as Cliente
    participant A as API
    participant DB as Base de Datos
    
    C->>A: POST /auth/login (email, password)
    A->>DB: Verificar credenciales
    DB-->>A: Usuario válido
    A-->>C: JWT Token + datos usuario
    
    Note over C: Token guardado para requests futuros
    
    C->>A: GET /topicos (Authorization: Bearer <token>)
    A->>A: Validar JWT + roles
    A-->>C: Respuesta autorizada
```

### 🏷️ Sistema de Roles

<div align="center">

| 🎭 Rol | 🆔 ID | 🔑 Permisos | 📝 Descripción |
|:------:|:-----:|-------------|----------------|
| **👑 ADMIN** | `1` | **Acceso Total** | Control completo sobre todos los recursos |
| **🛡️ MODERADOR** | `2` | **Gestión Contenido** | Crear cursos, moderar tópicos |
| **👤 USER** | `3` | **Participación** | Crear y editar sus propios tópicos |

</div>

### 📝 Ejemplo de Autenticación

```bash
# 1. Obtener token JWT
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "correoElectronico": "admin@forhub.com",
    "contrasena": "password123"
  }'

# Respuesta:
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "correoElectronico": "admin@forhub.com",
  "nombre": "Administrador"
}

# 2. Usar token en requests
curl -X POST http://localhost:8081/topicos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  -d '{
    "titulo": "Nuevo Tópico",
    "mensaje": "Contenido del tópico",
    "cursoId": 1,
    "usuarioId": 1
  }'
```

## 🌐 API Endpoints

### 📊 Resumen de Endpoints

<div align="center">

| 🏷️ Categoría | 📊 Total | 🔓 Públicos | 🔐 Protegidos | 👑 Solo Admin |
|:-------------:|:--------:|:-----------:|:-------------:|:-------------:|
| **🔑 Autenticación** | 2 | 2 | 0 | 0 |
| **📝 Tópicos** | 5 | 2 | 2 | 1 |
| **📚 Cursos** | 3 | 2 | 0 | 1 |
| **👥 Usuarios** | 1 | 1 | 0 | 0 |
| **🎭 Perfiles** | 1 | 1 | 0 | 0 |
| **📈 Total** | **12** | **8** | **2** | **2** |

</div>

### 🔑 Autenticación
| Método | Endpoint | Descripción | 🔐 Auth |
|:------:|----------|-------------|:-------:|
| `POST` | `/auth/login` | Iniciar sesión y obtener JWT | ❌ |
| `POST` | `/auth/registro` | Registrar nuevo usuario | ❌ |

### 📝 Tópicos
| Método | Endpoint | Descripción | 🔐 Auth |
|:------:|----------|-------------|:-------:|
| `GET` | `/topicos` | Listar tópicos (paginado) | ❌ |
| `GET` | `/topicos/{id}` | Obtener tópico específico | ❌ |
| `POST` | `/topicos` | Crear nuevo tópico | ✅ JWT |
| `PUT` | `/topicos/{id}` | Actualizar tópico | ✅ JWT |
| `DELETE` | `/topicos/{id}` | Eliminar tópico | 👑 ADMIN |

### 📚 Cursos
| Método | Endpoint | Descripción | 🔐 Auth |
|:------:|----------|-------------|:-------:|
| `GET` | `/cursos` | Listar todos los cursos | ❌ |
| `GET` | `/cursos/{id}` | Obtener curso específico | ❌ |
| `POST` | `/cursos` | Crear nuevo curso | 🛡️ ADMIN/MOD |

### 👥 Usuarios & Perfiles
| Método | Endpoint | Descripción | 🔐 Auth |
|:------:|----------|-------------|:-------:|
| `GET` | `/usuarios/{id}` | Obtener información de usuario | ❌ |
| `GET` | `/perfiles` | Listar roles disponibles | ❌ |

## 🎭 Sistema de Roles

| Rol | ID | Permisos |
|-----|----|---------| 
| **ADMIN** | 1 | Acceso completo (CRUD en todos los recursos) |
| **MODERADOR** | 2 | Crear cursos, gestionar tópicos |
| **USER** | 3 | Crear y editar sus propios tópicos |

## 🏗️ Arquitectura del Proyecto

<details>
<summary><b>📁 Estructura de Directorios</b></summary>

```
🎯 Foro-Hub/
├── 📊 src/main/java/com/rai69/Foro_Hub/
│   ├── ⚙️  config/              # Configuraciones (Security, JWT, Swagger)
│   │   ├── 🔐 SecurityConfiguration.java
│   │   ├── 🔑 JwtUtil.java
│   │   ├── 🛡️  JwtAuthenticationFilter.java
│   │   └── 🔒 PasswordConfig.java
│   ├── 🎮 controller/           # Controladores REST
│   │   ├── 🔑 AuthController.java
│   │   ├── 📝 TopicoController.java
│   │   ├── 📚 CursoController.java
│   │   ├── 👥 UsuarioController.java
│   │   └── 🎭 PerfilController.java
│   ├── 📦 dto/                 # Data Transfer Objects
│   │   ├── 🔑 LoginRequestDTO.java
│   │   ├── 📝 TopicoRequestDTO.java
│   │   ├── 📋 TopicoResponseDTO.java
│   │   └── 👤 UsuarioResponseDTO.java
│   ├── ⚠️  exception/          # Manejo de excepciones
│   │   ├── 🚫 EntityNotFoundException.java
│   │   └── 🔄 DuplicatedTopicException.java
│   ├── 🏛️  model/              # Entidades JPA
│   │   ├── 👤 UsuarioModel.java
│   │   ├── 📝 TopicoModel.java
│   │   ├── 📚 CursoModel.java
│   │   ├── 🎭 PerfilModel.java
│   │   └── 💬 RespuestaModel.java
│   ├── 🗃️  repository/         # Repositorios Spring Data
│   │   ├── 👤 UsuarioRepository.java
│   │   ├── 📝 TopicoRepository.java
│   │   ├── 📚 CursoRepository.java
│   │   └── 🎭 PerfilRepository.java
│   ├── 🔧 service/             # Lógica de negocio
│   │   ├── 👤 UsuarioService.java
│   │   ├── 📝 TopicoService.java
│   │   ├── 📚 CursoService.java
│   │   └── 🔐 UserDetailsServiceImpl.java
│   └── 🛠️  util/               # Utilidades
│       └── 🌍 EnvLoader.java
├── 📁 src/main/resources/
│   ├── 🗃️  db/migration/        # Scripts Flyway
│   │   └── V1__create_initial_schema.sql
│   └── ⚙️  application.properties
└── 🧪 src/test/                # Tests unitarios
```

</details>

<details>
<summary><b>�️ Esquema de Base de Datos</b></summary>

```sql
-- 👥 Tabla de Perfiles/Roles
perfiles
├── id (PK)
└── nombre (ADMIN, MODERADOR, USER)

-- 👤 Tabla de Usuarios  
usuarios
├── id (PK)
├── nombre
├── correo_electronico (UNIQUE)
├── contrasena (BCrypt)
└── perfil_id (FK → perfiles.id)

-- 📚 Tabla de Cursos
cursos
├── id (PK)
├── nombre
└── categoria

-- 📝 Tabla de Tópicos
topicos
├── id (PK)
├── titulo
├── mensaje
├── fecha_creacion
├── estado
├── usuario_id (FK → usuarios.id)
└── curso_id (FK → cursos.id)

-- 💬 Tabla de Respuestas (Futura implementación)
respuestas
├── id (PK)
├── mensaje
├── fecha_creacion
├── topico_id (FK → topicos.id)
└── usuario_id (FK → usuarios.id)
```

</details>

## 🧪 Testing y Ejemplos

### 🚀 Testing Rápido con cURL

<details>
<summary><b>🔑 Autenticación</b></summary>

```bash
# 📥 Registrar nuevo usuario
curl -X POST http://localhost:8081/auth/registro \
  -H "Content-Type: application/json" \
  -d '{
    "nombre": "Juan Pérez",
    "correoElectronico": "juan@ejemplo.com",
    "contrasena": "password123",
    "perfilId": 3
  }'

# 🔐 Iniciar sesión
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "correoElectronico": "juan@ejemplo.com",
    "contrasena": "password123"
  }'
```

</details>

<details>
<summary><b>📝 Gestión de Tópicos</b></summary>

```bash
# 📋 Listar todos los tópicos
curl -X GET "http://localhost:8081/topicos?page=0&size=10"

# 👁️ Ver tópico específico
curl -X GET http://localhost:8081/topicos/1

# ✏️ Crear nuevo tópico (requiere JWT)
curl -X POST http://localhost:8081/topicos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "titulo": "¿Cómo usar Spring Security?",
    "mensaje": "Tengo dudas sobre la configuración de Spring Security...",
    "cursoId": 1,
    "usuarioId": 1
  }'

# 🔄 Actualizar tópico (requiere JWT)
curl -X PUT http://localhost:8081/topicos/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "titulo": "Título actualizado",
    "mensaje": "Mensaje actualizado"
  }'
```

</details>

<details>
<summary><b>📚 Gestión de Cursos</b></summary>

```bash
# 📚 Listar todos los cursos
curl -X GET http://localhost:8081/cursos

# 👁️ Ver curso específico  
curl -X GET http://localhost:8081/cursos/1

# ➕ Crear nuevo curso (requiere rol ADMIN/MODERADOR)
curl -X POST http://localhost:8081/cursos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "nombre": "Spring Boot Avanzado",
    "categoria": "Backend"
  }'
```

</details>

### 🎯 Casos de Uso Comunes

| 🎯 Escenario | 📝 Descripción | 🔗 Endpoint |
|-------------|----------------|-------------|
| **Usuario nuevo** | Registro + Login + Crear tópico | `POST /auth/registro` → `POST /auth/login` → `POST /topicos` |
| **Moderador** | Login + Crear curso + Gestionar tópicos | `POST /auth/login` → `POST /cursos` → `PUT /topicos/{id}` |
| **Admin** | Login + Control total | `POST /auth/login` → Cualquier endpoint |
| **Visitante** | Ver contenido público | `GET /topicos`, `GET /cursos` |

### ⚠️ Códigos de Respuesta HTTP

| Código | Significado | 🔍 Cuándo ocurre |
|:------:|-------------|------------------|
| `200` | ✅ **OK** | Operación exitosa |
| `201` | ✅ **Created** | Recurso creado correctamente |
| `400` | ❌ **Bad Request** | Datos inválidos o faltantes |
| `401` | 🔐 **Unauthorized** | Token JWT inválido o faltante |
| `403` | 🚫 **Forbidden** | Permisos insuficientes |
| `404` | 🔍 **Not Found** | Recurso no encontrado |
| `409` | 🔄 **Conflict** | Recurso duplicado |
| `500` | ⚠️ **Internal Error** | Error interno del servidor |

## 🐛 Troubleshooting

### 🔧 Problemas Comunes y Soluciones

<details>
<summary><b>❌ Error de Conexión a Base de Datos</b></summary>

**🚨 Error:**
```
Failed to configure a DataSource: 'url' attribute is not specified
```

**✅ Solución:**
1. Verificar que PostgreSQL esté ejecutándose
2. Revisar credenciales en el archivo `.env`
3. Confirmar que la base de datos `foro-hub` existe

```bash
# Verificar PostgreSQL
sudo systemctl status postgresql  # Linux
brew services list | grep postgresql  # macOS

# Crear base de datos si no existe
psql -U postgres -c "CREATE DATABASE \"foro-hub\";"
```

</details>

<details>
<summary><b>🔐 Error de JWT Secret</b></summary>

**🚨 Error:**
```
The specified key byte array is 64 bits which is not secure enough
```

**✅ Solución:**
- `JWT_SECRET` debe tener al menos 32 caracteres (256 bits)

```bash
# Generar clave segura
openssl rand -hex 32
# Ejemplo: a1b2c3d4e5f6789012345678901234567890abcdef1234567890abcdef123456
```

</details>

<details>
<summary><b>🚫 Error 403 Forbidden</b></summary>

**🚨 Error:**
```
Access Denied
```

**✅ Solución:**
1. Verificar que el token JWT sea válido y no haya expirado
2. Confirmar que el usuario tenga los permisos necesarios
3. Revisar que el header `Authorization` esté bien formateado:
   ```
   Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   ```

</details>

<details>
<summary><b>📦 Error de Maven</b></summary>

**🚨 Error:**
```
Could not resolve dependencies
```

**✅ Solución:**
```bash
# Limpiar y reinstalar dependencias
mvn clean install -U

# Si persiste, borrar repositorio local
rm -rf ~/.m2/repository/com/rai69
mvn clean install
```

</details>

### 🆘 Obtener Ayuda

- 📖 **Documentación**: Revisa [ENVIRONMENT_SETUP.md](ENVIRONMENT_SETUP.md) para configuración
- 🐛 **Issues**: [Reportar problema](https://github.com/Rai5559/Alura-Challenge-Foro-Hub/issues)
- 💬 **Discusiones**: [GitHub Discussions](https://github.com/Rai5559/Alura-Challenge-Foro-Hub/discussions)

## 🤝 Contribución

¡Las contribuciones son bienvenidas! Sigue estos pasos:

### 🔄 Proceso de Contribución

1. **🍴 Fork** del proyecto
2. **🌿 Crear rama** para tu feature
   ```bash
   git checkout -b feature/nueva-funcionalidad
   ```
3. **💾 Commit** tus cambios
   ```bash
   git commit -m "✨ Agregar nueva funcionalidad increíble"
   ```
4. **📤 Push** a tu rama
   ```bash
   git push origin feature/nueva-funcionalidad
   ```
5. **🔃 Pull Request** al repositorio principal

### 📝 Convenciones de Commits

Usamos [Conventional Commits](https://www.conventionalcommits.org/):

- `✨ feat:` nueva funcionalidad
- `🐛 fix:` corrección de bug
- `📚 docs:` cambios en documentación
- `🎨 style:` formato, espacios en blanco, etc.
- `♻️ refactor:` refactorización de código
- `🧪 test:` agregar o corregir tests
- `🔧 chore:` tareas de mantenimiento

### 🎯 Áreas de Contribución

- 🔐 **Seguridad**: Mejoras en autenticación/autorización
- 📊 **Rendimiento**: Optimizaciones de base de datos y consultas
- 🧪 **Testing**: Tests unitarios e integración
- 📚 **Documentación**: Guías y ejemplos
-  **Internacionalización**: Soporte multi-idioma
- 🔧 **DevOps**: Configuración de CI/CD

## 📄 Licencia

Este proyecto está licenciado bajo la **MIT License** - ver el archivo [LICENSE](LICENSE) para más detalles.

### 📋 Resumen de la Licencia

- ✅ **Uso comercial** permitido
- ✅ **Modificación** permitida  
- ✅ **Distribución** permitida
- ✅ **Uso privado** permitido
- ❌ **Sin garantía** incluida
- ❌ **Sin responsabilidad** del autor

## 👨‍💻 Autor y Reconocimientos

<div align="center">

### 🎯 **Desarrollado por**

**Rai69** 

[![GitHub](https://img.shields.io/badge/GitHub-Rai5559-black?style=for-the-badge&logo=github)](https://github.com/Rai5559)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Perfil-blue?style=for-the-badge&logo=linkedin)](https://linkedin.com/in/tu-perfil)
[![Email](https://img.shields.io/badge/Email-Contacto-red?style=for-the-badge&logo=gmail)](mailto:tu-email@ejemplo.com)

---

### 🙏 **Agradecimientos**

- **☕ Spring Boot Team** - Por el excelente framework
- **🔐 JWT.io** - Por la documentación y herramientas JWT
- **📚 Alura** - Por el desafío y la oportunidad de aprender
- **🌟 Comunidad Open Source** - Por las librerías y herramientas

---

### ⭐ **¿Te gusta el proyecto?**

**¡Dale una estrella!** ⭐ Me ayuda mucho a seguir mejorando

[⭐ Star en GitHub](https://github.com/Rai5559/Alura-Challenge-Foro-Hub)

</div>

---

<div align="center">

**📢 ¿Encontraste algún problema o tienes una sugerencia?**

[![Report Bug](https://img.shields.io/badge/🐛-Reportar%20Bug-red?style=for-the-badge)](https://github.com/Rai5559/Alura-Challenge-Foro-Hub/issues)
[![Request Feature](https://img.shields.io/badge/✨-Solicitar%20Feature-green?style=for-the-badge)](https://github.com/Rai5559/Alura-Challenge-Foro-Hub/issues)

**¡Gracias por usar Foro Hub API!** 🚀

</div>
