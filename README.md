# Guardián Financiero IA

**Subtítulo:** Plataforma inteligente antifraude para análisis de transacciones financieras.

## Descripción general
Guardián Financiero IA es una plataforma full-stack empresarial diseñada para registrar clientes y transacciones financieras, analizando el riesgo de fraude en tiempo real. Permite generar y gestionar alertas antifraude mediante un equipo de analistas, ofreciendo visualizaciones de métricas en un dashboard integral y soportado por un microservicio avanzado de Inteligencia Artificial.

## Problema que resuelve
El aumento de transacciones digitales ha incrementado proporcionalmente los intentos de fraude financiero. Muchas instituciones carecen de sistemas centralizados, eficientes y apoyados en IA que permitan detectar patrones anómalos en tiempo real, lo que resulta en pérdidas económicas y disminución de la confianza del cliente.

## Objetivo del sistema
Crear una plataforma robusta y escalable que permita registrar operaciones, evaluar su riesgo instantáneamente usando reglas predefinidas y modelos de IA, y proporcionar a los analistas una herramienta eficaz para auditar y gestionar las alertas generadas.

## Tecnologías planificadas
- Java 21
- Spring Boot
- Spring Security
- JWT
- PostgreSQL
- Angular
- TypeScript
- Angular Material
- Python
- FastAPI
- Scikit-learn
- Docker
- Docker Compose
- Swagger / OpenAPI
- JUnit
- Mockito
- Git y GitHub

## Módulos principales
1. **Backend Base:** API REST en Java Spring Boot.
2. **Frontend:** Aplicación web SPA en Angular.
3. **Servicio IA:** Microservicio en Python/FastAPI.
4. **Base de Datos:** PostgreSQL con esquema relacional.
5. **Dashboard:** Panel de control y métricas para analistas.

## Estructura del proyecto
```text
guardian-financiero-ia/
├── backend-java/
├── frontend-angular/
├── ai-service/
├── database/
│   ├── init.sql
│   └── seed.sql
├── docs/
│   ├── architecture.md
│   ├── api-examples.md
│   └── development-notes.md
├── README.md
├── .gitignore
└── docker-compose.yml
```

## Planificación y Módulos
* **Módulo 1:** estructura base del monorepo. (Completado)
* **Módulo 2:** backend Java Spring Boot base. (Completado)
* **Módulo 3:** PostgreSQL y modelo de datos inicial. (Completado)
* **Módulo 4:** seguridad con Spring Security, JWT y roles. (Completado)
* **Módulo 5:** gestión de clientes. (Completado)
* **Módulo 6:** frontend Angular base y UI dashboard.
* **Módulo 7:** microservicio IA integrado.

## Flujo general del sistema
1. El cliente o sistema externo envía una transacción al backend (Spring Boot).
2. El backend guarda la transacción en PostgreSQL y solicita un análisis rápido mediante reglas predefinidas.
3. El backend envía la transacción al microservicio IA para un análisis avanzado (opcional/asíncrono).
4. El sistema evalúa el puntaje final; si supera el límite, crea una `FraudAlert`.
5. Si es muy alto el riesgo, la transacción se marca como "UNDER_REVIEW" o se bloquea automáticamente.
6. Los usuarios (ANALYST o AUDITOR) inician sesión en el frontend (Angular) para revisar el dashboard.
7. Los analistas gestionan (aprueban, rechazan, investigan) las alertas desde la interfaz web.

## Base de Datos (PostgreSQL)
La base de datos está completamente configurada. Al ejecutarse la aplicación, las entidades de JPA crearán y actualizarán el esquema automáticamente.

## Estado actual
**Módulo 5:** Implementada gestión completa de clientes (CRUD, búsqueda, filtros y cambio de estado) usando DTOs, validaciones, mapeos manuales y seguridad por roles.

---
**Autor:** Brayan Jair Chavez Oscor
