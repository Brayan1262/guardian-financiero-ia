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
- `GET /api/health` : Endpoint de salud, verifica el estado del backend.

## Swagger / OpenAPI
La documentación interactiva de la API está disponible en:
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Base de Datos (PostgreSQL)
Actualmente el proyecto utiliza el perfil `dev` por defecto. Este perfil **desactiva temporalmente** la autoconfiguración de la base de datos para permitir que el backend inicie correctamente de forma limpia y sin errores de conexión. La configuración de PostgreSQL está completamente preparada en el perfil `prod` y **se configurará y habilitará al 100% en el Módulo 3**, cuando comencemos a trabajar con las entidades y repositorios de datos.

## Estado actual
**Módulo 2:** backend base Spring Boot creado con estructura por capas, manejo global de errores, Swagger y endpoints básicos listos para ejecutar.
