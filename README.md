# 🏛️ Foro Hub - API REST

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.3-brightgreen)
![Java](https://img.shields.io/badge/Java-17+-orange)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-17.5-blue)
![JWT](https://img.shields.io/badge/JWT-Authentication-red)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI%203.0-green)

API REST para la gestión de un foro de discusión con autenticación JWT, sistema de roles y documentación interactiva.

## 🚀 Características

- ✅ **Autenticación JWT** con sistema de roles (ADMIN, MODERADOR, USER)
- ✅ **CRUD completo** para tópicos, cursos y usuarios
- ✅ **Base de datos PostgreSQL** con migraciones Flyway
- ✅ **Documentación Swagger** interactiva
- ✅ **Seguridad Spring Security** 6+
- ✅ **Validación de datos** con Bean Validation
- ✅ **Paginación** en endpoints de listado
- ✅ **Manejo de excepciones** personalizado

## 🛠️ Tecnologías

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Spring Boot | 3.5.3 | Framework principal |
| Spring Security | 6+ | Autenticación y autorización |
| Spring Data JPA | - | Persistencia de datos |
| PostgreSQL | 17.5 | Base de datos |
| JWT (JJWT) | 0.12.3 | Tokens de autenticación |
| Flyway | - | Migraciones de BD |
| Swagger/OpenAPI | 3.0 | Documentación API |
| Maven | - | Gestión de dependencias |

## 📋 Prerrequisitos

- ☕ **Java** 17 o superior
- 🐘 **PostgreSQL** 17.5 o superior
- 📦 **Maven** 3.6 o superior

## ⚙️ Configuración del Entorno

### 1. Variables de Entorno

```bash
# Copiar template de variables de entorno
cp .env.template .env
```

Edita el archivo `.env` con tus valores:

```bash
# Base de datos PostgreSQL
DB_URL=jdbc:postgresql://localhost:5432/foro-hub
DB_USER=tu_usuario
DB_PASSWORD=tu_password

# JWT Configuration
JWT_SECRET=tu_clave_secreta_jwt_minimo_32_caracteres
JWT_EXPIRATION=86400000
```

📖 **Guía detallada:** [ENVIRONMENT_SETUP.md](ENVIRONMENT_SETUP.md)

### 2. Base de Datos

```sql
-- Crear base de datos
CREATE DATABASE "foro-hub";
```

Las tablas se crean automáticamente con Flyway en el primer arranque.

### 3. Ejecutar Aplicación

```bash
# Compilar proyecto
mvn clean compile

# Ejecutar aplicación
mvn spring-boot:run
```

La aplicación estará disponible en: http://localhost:8081

## 📚 Documentación API

### Swagger UI
- **URL**: http://localhost:8081/swagger-ui.html
- **API Docs**: http://localhost:8081/v3/api-docs

📖 **Guía completa de Swagger:** [SWAGGER_GUIDE.md](SWAGGER_GUIDE.md)

## 🔐 Autenticación

### Obtener Token JWT

```bash
POST /auth/login
Content-Type: application/json

{
  "correoElectronico": "usuario@ejemplo.com",
  "contrasena": "password123"
}
```

### Usar Token en Requests

```bash
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

## 📊 Endpoints Principales

### 🔑 Autenticación
| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| POST | `/auth/login` | Iniciar sesión | No |
| POST | `/auth/registro` | Registrar usuario | No |

### 📝 Tópicos
| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| GET | `/topicos` | Listar tópicos | No |
| GET | `/topicos/{id}` | Obtener tópico | No |
| POST | `/topicos` | Crear tópico | JWT |
| PUT | `/topicos/{id}` | Actualizar tópico | JWT |
| DELETE | `/topicos/{id}` | Eliminar tópico | ADMIN |

### 📚 Cursos
| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| GET | `/cursos` | Listar cursos | No |
| GET | `/cursos/{id}` | Obtener curso | No |
| POST | `/cursos` | Crear curso | ADMIN/MODERADOR |

### 👥 Usuarios
| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| GET | `/usuarios/{id}` | Obtener usuario | No |

## 🎭 Sistema de Roles

| Rol | ID | Permisos |
|-----|----|---------| 
| **ADMIN** | 1 | Acceso completo (CRUD en todos los recursos) |
| **MODERADOR** | 2 | Crear cursos, gestionar tópicos |
| **USER** | 3 | Crear y editar sus propios tópicos |

## 🗃️ Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/rai69/Foro_Hub/
│   │   ├── config/          # Configuración (Security, JWT, Swagger)
│   │   ├── controller/      # Controladores REST
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── exception/      # Manejo de excepciones
│   │   ├── model/          # Entidades JPA
│   │   ├── repository/     # Repositorios Spring Data
│   │   ├── service/        # Lógica de negocio
│   │   └── util/           # Utilidades
│   └── resources/
│       ├── db/migration/   # Scripts Flyway
│       └── application.properties
└── test/                   # Tests unitarios
```

## 🔄 Base de Datos

### Esquema Principal

**Usuarios (usuarios)**
- id, nombre, correo_electronico, contrasena
- perfil_id (FK a perfiles)

**Perfiles (perfiles)**
- id, nombre (ADMIN, MODERADOR, USER)

**Cursos (cursos)**
- id, nombre, categoria

**Tópicos (topicos)**
- id, titulo, mensaje, fecha_creacion, estado
- usuario_id (FK), curso_id (FK)

## 🧪 Testing

### Ejemplos con curl

```bash
# Login
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{"correoElectronico":"admin@test.com","contrasena":"password"}'

# Crear tópico (con JWT)
curl -X POST http://localhost:8081/topicos \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{"titulo":"Mi Tópico","mensaje":"Contenido","cursoId":1,"usuarioId":1}'
```

## 🐛 Troubleshooting

### Problemas Comunes

**❌ Error de conexión a BD**
```
Solution: Verificar PostgreSQL ejecutándose y credenciales en .env
```

**❌ JWT Secret error**
```
Solution: Verificar JWT_SECRET tiene al menos 32 caracteres
```

**❌ 403 Forbidden**
```
Solution: Verificar token JWT y permisos de rol
```

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -m 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver [LICENSE](LICENSE) para detalles.

## 👨‍💻 Autor

**Rai69** - [GitHub](https://github.com/Rai5559)

---

⭐ **¡Dale una estrella si te ha sido útil!**
