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
    "correoElectronico": "usuario@example.com",
    "contrasena": "contraseña123"
}
```

**Respuesta exitosa (200):**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3VhcmlvQGV4YW1wbGUuY29tIiwiaWF0IjoxNjM5NTUwNDAwLCJleHAiOjE2Mzk2MzY4MDB9.signature",
    "correoElectronico": "usuario@example.com",
    "nombre": "Usuario Test"
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
    "correoElectronico": "carlos@example.com",
    "contrasena": "password123",
    "perfilId": 3
}
```

**Respuestas**:
- **201 Created**: Usuario registrado exitosamente
- **400 Bad Request**: Datos inválidos o falta información requerida
- **409 Conflict**: Email ya registrado

**Ejemplo 409 - Email duplicado:**
```json
{
    "error": "Conflict",
    "message": "Ya existe un usuario registrado con este email",
    "timestamp": "2024-12-23T10:30:00Z",
    "status": 409,
    "path": "/auth/registro"
}
```

#### **Iniciar Sesión**
**POST** `/auth/login`

**Descripción**: Autentica usuario y retorna token JWT.

**Request Body**:
```json
{
    "correoElectronico": "carlos@example.com",
    "contrasena": "password123"
}
```

**Respuestas**:
- **200 OK**: Login exitoso con token JWT
- **401 Unauthorized**: Credenciales incorrectas
- **500 Internal Server Error**: Error interno

**Ejemplo 401 - Credenciales incorrectas:**
```json
{
    "error": "Unauthorized",
    "message": "Credenciales incorrectas. Verifica tu email y contraseña",
    "timestamp": "2024-12-23T10:30:00Z",
    "status": 401,
    "path": "/auth/login"
}
```

---

## 🔧 **MANEJO CENTRALIZADO DE ERRORES**

La API utiliza un sistema de manejo de errores centralizado que garantiza respuestas consistentes:

### **Estructura de Error Estándar**
```json
{
    "error": "Tipo de Error HTTP",
    "message": "Descripción detallada del error",
    "timestamp": "2024-12-23T10:30:00Z",
    "status": 400,
    "path": "/endpoint/que/genero/error"
}
```

### **Tipos de Error Comunes**

#### **400 - Bad Request**
```json
{
    "error": "Bad Request",
    "message": "Datos de entrada inválidos: {nombre=El nombre es requerido}",
    "timestamp": "2024-12-23T10:30:00Z",
    "status": 400,
    "path": "/topicos"
}
```

#### **401 - Unauthorized**
```json
{
    "error": "Unauthorized",
    "message": "Token JWT requerido o inválido",
    "timestamp": "2024-12-23T10:30:00Z",
    "status": 401,
    "path": "/topicos"
}
```

#### **403 - Forbidden**
```json
{
    "error": "Forbidden",
    "message": "No tienes permisos para realizar esta operación",
    "timestamp": "2024-12-23T10:30:00Z",
    "status": 403,
    "path": "/topicos/1"
}
```

#### **404 - Not Found**
```json
{
    "error": "Not Found",
    "message": "No se encontró un tópico con ID: 999",
    "timestamp": "2024-12-23T10:30:00Z",
    "status": 404,
    "path": "/topicos/999"
}
```

#### **409 - Conflict**
```json
{
    "error": "Conflict",
    "message": "Ya existe un tópico con el mismo título y autor",
    "timestamp": "2024-12-23T10:30:00Z",
    "status": 409,
    "path": "/topicos"
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
- **401 Unauthorized**: Token JWT requerido
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
        correoElectronico: 'usuario@example.com',
        contrasena: 'password123'
    })
});

if (!loginResponse.ok) {
    const error = await loginResponse.json();
    console.error('Error login:', error.message);
    return;
}

const { token } = await loginResponse.json();

// Usar token en requests
const topicsResponse = await fetch('/topicos', {
    headers: { 'Authorization': `Bearer ${token}` }
});

if (!topicsResponse.ok) {
    const error = await topicsResponse.json();
    console.error('Error:', error.message);
}
```

### Python/Requests con manejo de errores
```python
import requests

def login(email, password):
    response = requests.post('/auth/login', json={
        'correoElectronico': email,
        'contrasena': password
    })
    
    if response.status_code == 401:
        error = response.json()
        print(f"Error de login: {error['message']}")
        return None
    elif response.status_code != 200:
        error = response.json()
        print(f"Error inesperado: {error['message']}")
        return None
    
    return response.json()['token']

def create_topic(token, title, message, author_id, course_id):
    headers = {'Authorization': f'Bearer {token}'}
    data = {
        'titulo': title,
        'mensaje': message,
        'autor': author_id,
        'curso': course_id
    }
    
    response = requests.post('/topicos', json=data, headers=headers)
    
    if response.status_code == 409:
        error = response.json()
        print(f"Tópico duplicado: {error['message']}")
    elif response.status_code == 401:
        error = response.json()
        print(f"Token inválido: {error['message']}")
    elif response.status_code == 201:
        print("Tópico creado exitosamente!")
        return response.json()
    else:
        error = response.json()
        print(f"Error: {error['message']}")
    
    return None
```

---

## ⚠️ **CONSIDERACIONES IMPORTANTES**

1. **Manejo de Errores**: Todos los errores siguen el formato estándar JSON
2. **Tokens JWT**: Expiran en 24 horas, requieren renovación
3. **Validación**: Los errores de validación incluyen detalles específicos
4. **Códigos HTTP**: Usar códigos de estado para lógica de control
5. **Mensajes**: Los mensajes de error son descriptivos y orientados al usuario
6. **CORS**: Configurado para desarrollo en localhost
7. **HTTPS**: Usar en producción para seguridad

---

## 🐛 **DEPURACIÓN Y LOGS**

- **Errores 500**: Consulta los logs del servidor
- **Errores de validación**: Revisa el campo "message" en la respuesta
- **Tokens JWT**: Valida en [jwt.io](https://jwt.io)
- **Swagger UI**: Usa para pruebas interactivas
- **Códigos de estado**: Implementa lógica de manejo basada en HTTP status

---

**📞 Soporte**: Si encuentras problemas, todos los errores ahora incluyen información detallada. Consulta la documentación interactiva en Swagger UI o contacta al equipo de desarrollo.
