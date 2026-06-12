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
finguard-ai/
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

## Flujo general del sistema
1. El cliente o sistema externo envía una transacción al backend (Spring Boot).
2. El backend guarda la transacción en PostgreSQL y solicita un análisis rápido mediante reglas predefinidas.
3. El backend envía la transacción al microservicio de IA (FastAPI) para un análisis profundo basado en modelos.
4. El servicio de IA devuelve un score de riesgo.
5. Si el riesgo supera un umbral, el backend genera una alerta de fraude en la base de datos.
6. Los usuarios (ANALYST o AUDITOR) inician sesión en el frontend (Angular) para revisar el dashboard.
7. Los analistas gestionan (aprueban, rechazan, investigan) las alertas desde la interfaz web.

## Estado actual
**Módulo 1:** estructura inicial base del monorepo y documentación.

---
**Autor:** Brayan Jair Chavez Oscor
