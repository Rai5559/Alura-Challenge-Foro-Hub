# 📋 Documentación Completa de API - Foro Hub

## 🚀 Introducción

Esta guía proporciona documentación detallada de todos los endpoints de la API del **Foro Hub**, diseñada para que los desarrolladores de cliente puedan integrar fácilmente con nuestro backend.

## 🔗 Acceso a la Documentación Interactiva

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## 🔐 Autenticación JWT

### Esquema de Seguridad
La API utiliza **JWT Bearer Token** para autenticación. Todos los endpoints protegidos requieren el header:

```bash
Authorization: Bearer <tu_jwt_token>
```

### Obtener Token JWT
**POST** `/auth/login`

```json
{
    "email": "usuario@example.com",
    "password": "contraseña123"
}
```

**Respuesta exitosa (200):**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvQGV4YW1wbGUuY29tIiwiaWF0IjoxNjM5NTUwNDAwLCJleHAiOjE2Mzk2MzY4MDB9.signature",
    "tipo": "Bearer",
    "usuario": {
        "id": 1,
        "nombre": "Usuario Test",
        "email": "usuario@example.com",
        "perfil": {
            "id": 3,
            "nombre": "USER"
        }
    }
}
```

---

## 📝 ENDPOINTS DETALLADOS

### 🔑 **1. AUTENTICACIÓN** (`/auth`)

#### **Registro de Usuario**
**POST** `/auth/registro`

**Descripción**: Registra un nuevo usuario en el sistema con perfil USER por defecto.

**Request Body**:
```json
{
    "nombre": "Carlos Rodriguez",
    "email": "carlos@example.com",
    "password": "password123"
}
```

**Respuestas**:
- **201 Created**: Usuario registrado exitosamente
- **400 Bad Request**: Datos inválidos
- **409 Conflict**: Email ya registrado

#### **Iniciar Sesión**
**POST** `/auth/login`

**Descripción**: Autentica usuario y retorna token JWT.

**Request Body**:
```json
{
    "email": "carlos@example.com",
    "password": "password123"
}
```

---

### 💬 **2. TÓPICOS** (`/topicos`)

#### **Crear Tópico**
**POST** `/topicos`
🔒 **Requiere autenticación**

**Descripción**: Crea un nuevo tópico en el foro.

**Request Body**:
```json
{
    "titulo": "¿Cómo implementar JWT en Spring Boot?",
    "mensaje": "Necesito ayuda para implementar autenticación JWT...",
    "autor": 1,
    "curso": 1
}
```

**Respuestas**:
- **201 Created**: Tópico creado exitosamente
- **400 Bad Request**: Datos inválidos
- **409 Conflict**: Tópico duplicado (mismo título y autor)

#### **Obtener Tópico por ID**
**GET** `/topicos/{id}`
🔒 **Requiere autenticación**

**Parámetros**:
- `id` (path): ID del tópico (ejemplo: 1)

**Respuesta 200**:
```json
{
    "id": 1,
    "titulo": "¿Cómo implementar JWT en Spring Boot?",
    "mensaje": "Necesito ayuda para implementar autenticación JWT en mi proyecto Spring Boot...",
    "fechaCreacion": "2024-12-23T10:30:00Z",
    "status": "ABIERTO",
    "usuario": {
        "id": 1,
        "nombre": "Carlos Rodriguez",
        "email": "carlos@example.com"
    },
    "curso": {
        "id": 1,
        "nombre": "Spring Boot Avanzado",
        "categoria": "Backend Development"
    }
}
```

#### **Listar Tópicos**
**GET** `/topicos`
🔒 **Requiere autenticación**

**Parámetros de Query**:
- `page`: Número de página (default: 0)
- `size`: Elementos por página (default: 10)
- `sort`: Campo de ordenamiento (default: fechaCreacion,desc)

**Ejemplo**: `/topicos?page=0&size=5&sort=titulo,asc`

#### **Actualizar Tópico**
**PUT** `/topicos/{id}`
🔒 **Requiere autenticación** (solo autor o ADMIN/MODERADOR)

**Request Body**:
```json
{
    "titulo": "¿Cómo implementar JWT y OAuth2 en Spring Boot?",
    "mensaje": "Mensaje actualizado...",
    "status": "CERRADO"
}
```

#### **Eliminar Tópico**
**DELETE** `/topicos/{id}`
🔒 **Requiere rol ADMIN**

**Respuesta**: 204 No Content

---

### 📚 **3. CURSOS** (`/cursos`)

#### **Crear Curso**
**POST** `/cursos`
🔒 **Requiere rol ADMIN o MODERADOR**

**Request Body**:
```json
{
    "nombre": "Spring Boot Avanzado",
    "categoria": "Backend Development"
}
```

#### **Obtener Curso por Nombre**
**GET** `/cursos/{nombre}`
🌐 **Endpoint público**

**Parámetro**:
- `nombre` (path): Nombre del curso (ejemplo: "Spring Boot Avanzado")

#### **Listar Cursos**
**GET** `/cursos`
🌐 **Endpoint público**

**Parámetros**: Soporta paginación estándar

#### **Actualizar Curso**
**PUT** `/cursos/{nombre}`
🔒 **Requiere rol ADMIN o MODERADOR**

---

### 👥 **4. USUARIOS** (`/usuarios`)

#### **Obtener Usuario por ID**
**GET** `/usuarios/{id}`
🔒 **Requiere autenticación**

**Respuesta 200**:
```json
{
    "id": 1,
    "nombre": "Carlos Rodriguez",
    "email": "carlos@example.com",
    "perfil": {
        "id": 3,
        "nombre": "USER"
    }
}
```

#### **Listar Usuarios**
**GET** `/usuarios`
🔒 **Requiere autenticación**

**Ordenamiento**: Por defecto ordenado por nombre (ASC)

---

### 🎭 **5. PERFILES** (`/perfiles`)

#### **Listar Perfiles**
**GET** `/perfiles`
🔒 **Requiere autenticación**

**Descripción**: Retorna los roles disponibles en el sistema.

**Respuesta 200**:
```json
[
    {"id": 1, "nombre": "ADMIN"},
    {"id": 2, "nombre": "MODERADOR"},
    {"id": 3, "nombre": "USER"}
]
```

---

## 🔒 **ROLES Y PERMISOS**

| Endpoint | ADMIN | MODERADOR | USER | Público |
|----------|-------|-----------|------|---------|
| POST /auth/registro | ✅ | ✅ | ✅ | ✅ |
| POST /auth/login | ✅ | ✅ | ✅ | ✅ |
| POST /topicos | ✅ | ✅ | ✅ | ❌ |
| GET /topicos/* | ✅ | ✅ | ✅ | ❌ |
| PUT /topicos/* | ✅ | ✅ | 🔸* | ❌ |
| DELETE /topicos/* | ✅ | ❌ | ❌ | ❌ |
| POST /cursos | ✅ | ✅ | ❌ | ❌ |
| GET /cursos/* | ✅ | ✅ | ✅ | ✅ |
| PUT /cursos/* | ✅ | ✅ | ❌ | ❌ |
| GET /usuarios/* | ✅ | ✅ | ✅ | ❌ |
| GET /perfiles | ✅ | ✅ | ✅ | ❌ |

**🔸*** Usuario solo puede editar sus propios tópicos

---

## 📊 **CÓDIGOS DE RESPUESTA**

| Código | Descripción | Uso |
|--------|-------------|-----|
| **200** | OK | Operación exitosa |
| **201** | Created | Recurso creado exitosamente |
| **204** | No Content | Eliminación exitosa |
| **400** | Bad Request | Datos de entrada inválidos |
| **401** | Unauthorized | Token JWT requerido o inválido |
| **403** | Forbidden | Permisos insuficientes |
| **404** | Not Found | Recurso no encontrado |
| **409** | Conflict | Conflicto (ej: email duplicado) |
| **500** | Internal Server Error | Error interno del servidor |

---

## 🛠️ **EJEMPLOS DE INTEGRACIÓN**

### JavaScript/Fetch
```javascript
// Obtener token
const loginResponse = await fetch('/auth/login', {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify({
        email: 'usuario@example.com',
        password: 'password123'
    })
});
const { token } = await loginResponse.json();

// Usar token en requests
const topicsResponse = await fetch('/topicos', {
    headers: { 'Authorization': `Bearer ${token}` }
});
```

### cURL
```bash
# Login
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"usuario@example.com","password":"password123"}'

# Usar token
curl -X GET http://localhost:8080/topicos \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### Python/Requests
```python
import requests

# Login
login_response = requests.post('/auth/login', json={
    'email': 'usuario@example.com',
    'password': 'password123'
})
token = login_response.json()['token']

# Headers con token
headers = {'Authorization': f'Bearer {token}'}
topics = requests.get('/topicos', headers=headers)
```

---

## ⚠️ **CONSIDERACIONES IMPORTANTES**

1. **Tokens JWT**: Expiran en 24 horas, requieren renovación
2. **Rate Limiting**: Implementar en el cliente para evitar spam
3. **Validación**: Todos los campos son validados en el servidor
4. **Paginación**: Usar parámetros `page` y `size` para listas grandes
5. **CORS**: Configurado para desarrollo en localhost
6. **HTTPS**: Usar en producción para seguridad

---

## 🐛 **DEPURACIÓN Y LOGS**

- Consulta los logs del servidor para errores 500
- Valida tokens JWT en [jwt.io](https://jwt.io)
- Usa herramientas como Postman o Insomnia para pruebas
- Revisa la consola de Swagger UI para ejemplos en vivo

---

**📞 Soporte**: Si encuentras problemas, consulta la documentación interactiva en Swagger UI o contacta al equipo de desarrollo.
