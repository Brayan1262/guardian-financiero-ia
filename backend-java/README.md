# Guardián Financiero IA - Backend

Este es el backend principal para la plataforma Guardián Financiero IA, encargado de gestionar la lógica de negocio, base de datos, seguridad, y la comunicación con el microservicio de Inteligencia Artificial.

## Tecnologías usadas
- Java 21
- Spring Boot 3.2.x
- Maven
- Spring Web
- Spring Data JPA
- PostgreSQL
- Spring Validation
- Lombok
- Springdoc OpenAPI (Swagger)

## Requisitos previos
- Java 21 instalado
- Maven instalado
- Base de datos PostgreSQL disponible

## Variables de entorno
Para ejecutar el proyecto, puedes configurar las siguientes variables de entorno. Hay un archivo `.env.example` como referencia.

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=guardian_financiero_db
DB_USER=postgres
DB_PASSWORD=admin
SERVER_PORT=8080
```

## Cómo ejecutar
1. Clona el repositorio y navega a la carpeta `backend-java`.
2. Asegúrate de tener una base de datos PostgreSQL corriendo con las credenciales indicadas en las variables de entorno (o usando los valores por defecto).
3. Ejecuta el proyecto con Maven Wrapper:
   ```bash
   .\mvnw.cmd spring-boot:run
   ```
   *Alternativamente, en Windows puedes ejecutar simplemente el archivo `run-backend.bat`.*

### Solución a error JAVA_HOME en Windows
Si al ejecutar te aparece el error `JAVA_HOME not found in your environment`, por favor lee el archivo [SETUP_WINDOWS.md](SETUP_WINDOWS.md) o ejecuta el script `run-backend.bat` para recibir instrucciones precisas sobre cómo configurarlo en tu sistema.

## Endpoints disponibles (Base)
- `GET /` : Endpoint principal, devuelve información de la aplicación.
- `GET /api/health` : Health check del microservicio.
- `GET /api/database/status` : Verificar estado de conexión con PostgreSQL.

## Swagger / OpenAPI
La documentación interactiva de la API está disponible en:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## Módulo 3 - PostgreSQL y modelo de datos inicial

### 1. Base de datos manual
Para ejecutar este módulo con éxito, primero asegúrate de crear la base de datos en tu PostgreSQL usando pgAdmin o psql:
```sql
CREATE DATABASE guardian_financiero_db;
```

### 2. Variables de entorno
Renombra o copia el archivo `.env.example` a `.env` en la raíz de `backend-java` y ajusta tus credenciales si no usas las de por defecto:
```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=guardian_financiero_db
DB_USER=postgres
DB_PASSWORD=admin
SERVER_PORT=8080
```

### 3. Ejecutar y probar
Ejecuta el backend en tu terminal Windows usando el script preparado:
```cmd
.\run-backend.bat
```
*(O tradicionalmente: `.\mvnw.cmd spring-boot:run`)*

Una vez iniciado, Spring Boot creará las tablas automáticamente en tu base de datos gracias a JPA/Hibernate. 
Luego, puedes verificar la conexión entrando a:
- [http://localhost:8080/api/database/status](http://localhost:8080/api/database/status)

---

## Módulo 4 - Seguridad JWT y roles

El sistema incluye autenticación JWT usando Spring Security y BCrypt.

### Endpoints Públicos
- `GET /`
- `GET /api/health`
- `GET /api/database/status`
- `POST /api/auth/register`
- `POST /api/auth/login`
- Documentación Swagger (`/swagger-ui/index.html`)

### Endpoints Protegidos por Rol
- `/api/admin/**`: Requiere rol `ADMIN`
- `/api/analyst/**`: Requiere rol `ADMIN` o `ANALYST`
- `/api/audit/**`: Requiere rol `ADMIN` o `AUDITOR`

### Probar la seguridad

1. **Registrar un usuario:** 
   Ve al endpoint `POST /api/auth/register` y crea tu usuario (ej. rol `ADMIN`).
2. **Iniciar sesión:** 
   Usa `POST /api/auth/login` para obtener tu `token` JWT.
3. **Autorizar Swagger:** 
   Copia el `token` devuelto (sin la palabra Bearer), haz clic en el botón verde **Authorize** en la parte superior derecha de Swagger y pégalo allí.
4. **Probar endpoints:** 
   Ahora puedes ejecutar libremente los endpoints como `GET /api/auth/me` o `GET /api/admin/test` desde Swagger, y la autorización se enviará automáticamente.

---

## Módulo 5 - Gestión de clientes

Este módulo permite administrar los clientes que realizarán transacciones.

### Endpoints Protegidos por Rol
- `POST /api/customers`: Crear cliente. (ADMIN, ANALYST)
- `GET /api/customers`: Listar todos. (ADMIN, ANALYST, AUDITOR)
- `GET /api/customers/{id}`: Buscar por ID. (ADMIN, ANALYST, AUDITOR)
- `GET /api/customers/document/{documentNumber}`: Buscar por DNI. (ADMIN, ANALYST, AUDITOR)
- `GET /api/customers/status/{status}`: Buscar por estado. (ADMIN, ANALYST, AUDITOR)
- `GET /api/customers/search?name={texto}`: Búsqueda por nombre. (ADMIN, ANALYST, AUDITOR)
- `PUT /api/customers/{id}`: Actualizar cliente. (ADMIN, ANALYST)
- `PATCH /api/customers/{id}/status`: Cambiar estado. (ADMIN, ANALYST)
- `DELETE /api/customers/{id}`: Eliminar cliente. (ADMIN)

### Cómo probar en Swagger
1. Genera y autoriza tu token JWT como se explica en el Módulo 4.
2. Ve a la sección `customer-controller`.
3. Para crear, usa `POST /api/customers` con el siguiente body:
```json
{
  "documentNumber": "74859612",
  "fullName": "Juan Pérez",
  "email": "juan.perez@email.com",
  "phone": "987654321"
}
```

---

## Módulo 6 - Gestión de transacciones financieras

Prepara las transacciones antes de que pasen por el motor antifraude. Aplica reglas de validación en base al estado del cliente (por ejemplo, rechaza transacciones si el cliente está `BLOCKED`).

### Endpoints Protegidos por Rol
- `POST /api/transactions`: Crear transacción. (ADMIN, ANALYST)
- `GET /api/transactions`: Listar todas. (ADMIN, ANALYST, AUDITOR)
- `GET /api/transactions/{id}`: Buscar por ID. (ADMIN, ANALYST, AUDITOR)
- `GET /api/transactions/customer/{customerId}`: Transacciones de un cliente. (ADMIN, ANALYST, AUDITOR)
- `GET /api/transactions/status/{status}`: Filtro por estado. (ADMIN, ANALYST, AUDITOR)
- `GET /api/transactions/risk/{riskLevel}`: Filtro por riesgo. (ADMIN, ANALYST, AUDITOR)
- `GET /api/transactions/type/{type}`: Filtro por tipo. (ADMIN, ANALYST, AUDITOR)
- `GET /api/transactions/channel/{channel}`: Filtro por canal. (ADMIN, ANALYST, AUDITOR)
- `GET /api/transactions/date-range`: Filtro por fechas. (ADMIN, ANALYST, AUDITOR)
- `GET /api/transactions/summary`: Resumen estadístico. (ADMIN, ANALYST, AUDITOR)
- `PATCH /api/transactions/{id}/status`: Cambiar estado manualmente. (ADMIN, ANALYST)

### Cómo probar en Swagger
1. Login en `/api/auth/login` y autoriza Swagger.
2. Crea un cliente en `customer-controller` y anota su `id`.
3. Crea la transacción enviando en `POST /api/transactions`:
```json
{
  "customerId": 1,
  "amount": 8500.00,
  "transactionType": "TRANSFER",
  "channel": "WEB",
  "originLocation": "Lima",
  "destinationLocation": "Cusco",
  "deviceId": "WEB-DEVICE-001",
  "transactionDateTime": "2026-06-12T14:30:00"
}
```

## Base de Datos (PostgreSQL)
Actualmente el proyecto utiliza el perfil `dev` por defecto. Este perfil **desactiva temporalmente** la autoconfiguración de la base de datos para permitir que el backend inicie correctamente de forma limpia y sin errores de conexión. La configuración de PostgreSQL está completamente preparada en el perfil `prod` y **se configurará y habilitará al 100% en el Módulo 3**, cuando comencemos a trabajar con las entidades y repositorios de datos.

## Estado actual
**Módulo 2:** backend base Spring Boot creado con estructura por capas, manejo global de errores, Swagger y endpoints básicos listos para ejecutar.
