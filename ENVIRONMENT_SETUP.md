# 🔧 Configuración del Entorno - Foro Hub

## 📋 Variables de Entorno Requeridas

Este proyecto utiliza variables de entorno para mantener la seguridad de datos sensibles como credenciales de base de datos y claves JWT.

### 🚀 Configuración Rápida

1. **Copiar el template:**
   ```bash
   cp .env.template .env
   ```

2. **Editar el archivo `.env`:**
   - Abre el archivo `.env` en tu editor
   - Reemplaza los valores de ejemplo con tus valores reales

3. **Verificar configuración:**
   - El archivo `.env` debe estar en la raíz del proyecto
   - **NUNCA** subas el archivo `.env` a GitHub

### 📊 Variables Detalladas

#### Base de Datos PostgreSQL
```bash
# Conexión a PostgreSQL
DB_URL=jdbc:postgresql://localhost:5432/foro-hub
DB_USER=postgres
DB_PASSWORD=tu_password_aqui
```

**Configuración de PostgreSQL:**
- Puerto por defecto: `5432`
- Base de datos: `foro-hub`
- Usuario: configura según tu instalación

#### JWT (Autenticación)
```bash
# Configuración JWT
JWT_SECRET=mi_clave_super_secreta_de_al_menos_32_caracteres
JWT_EXPIRATION=86400000
```

**Configuración JWT:**
- `JWT_SECRET`: Clave de 256 bits mínimo (32+ caracteres)
- `JWT_EXPIRATION`: Tiempo en milisegundos (86400000 = 24 horas)

### 🔒 Seguridad

#### Generar JWT_SECRET Seguro

**Opción 1 - OpenSSL:**
```bash
openssl rand -hex 32
```

**Opción 2 - Online (solo desarrollo):**
- Visita: https://jwt.io/
- Usa el generador de claves

**Opción 3 - Java:**
```java
// Generar clave de 256 bits
KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
SecretKey secretKey = keyGen.generateKey();
String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
```

#### Buenas Prácticas

✅ **Hacer:**
- Usar claves JWT únicas por ambiente
- Rotar claves periódicamente en producción
- Usar contraseñas fuertes para BD
- Verificar que `.env` esté en `.gitignore`

❌ **NO Hacer:**
- Subir archivos `.env` a GitHub
- Usar la misma clave JWT en desarrollo y producción
- Hardcodear valores sensibles en el código
- Compartir claves en canales inseguros

### 🏗️ Configuración por Ambiente

#### Desarrollo Local
```bash
DB_URL=jdbc:postgresql://localhost:5432/foro-hub-dev
JWT_EXPIRATION=86400000  # 24 horas
```

#### Producción
```bash
DB_URL=jdbc:postgresql://prod-server:5432/foro-hub-prod
JWT_EXPIRATION=3600000   # 1 hora (más seguro)
```

### 🛠️ Troubleshooting

#### Error: "Could not resolve placeholder"
- ✅ Verifica que existe el archivo `.env`
- ✅ Verifica que las variables estén definidas
- ✅ Reinicia la aplicación Spring Boot

#### Error de conexión a BD
- ✅ Verifica que PostgreSQL esté ejecutándose
- ✅ Verifica credenciales en `.env`
- ✅ Verifica que la base de datos existe

#### Error JWT
- ✅ Verifica que `JWT_SECRET` tenga al menos 32 caracteres
- ✅ Verifica formato de `JWT_EXPIRATION` (números, sin comillas)

### 📝 Ejemplo Completo

Archivo `.env` funcional:
```bash
# Base de datos
DB_URL=jdbc:postgresql://localhost:5432/foro-hub
DB_USER=postgres
DB_PASSWORD=miPasswordSeguro123

# JWT
JWT_SECRET=esta_es_una_clave_jwt_muy_segura_de_32_caracteres_minimo
JWT_EXPIRATION=86400000
```

---

💡 **Tip:** Mantén una copia local de tus configuraciones en un gestor de contraseñas seguro.
