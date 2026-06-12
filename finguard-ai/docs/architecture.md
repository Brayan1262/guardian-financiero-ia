# Arquitectura del Sistema: Guardián Financiero IA

Este documento describe la arquitectura planeada para la plataforma antifraude.

## Componentes Principales

### 1. Backend Principal (Java Spring Boot)
El núcleo del sistema estará desarrollado en Java 21 utilizando el framework Spring Boot. Este componente actuará como el orquestador principal, exponiendo la API REST central. Se encargará de la lógica de negocio, reglas de validación inicial, autenticación, autorización y comunicación directa con la base de datos.

### 2. Frontend (Angular)
La interfaz de usuario será una Single Page Application (SPA) construida con Angular, TypeScript y Angular Material. Consumirá la API del backend para ofrecer interfaces de registro, gestión de clientes, visualización de transacciones, dashboard de métricas y gestión de alertas.

### 3. Microservicio de IA (FastAPI)
Un servicio independiente desarrollado en Python utilizando FastAPI. Estará dedicado exclusivamente al análisis de riesgo usando modelos de Machine Learning (como Scikit-learn). Recibirá datos de transacciones desde el backend Java y devolverá un score de riesgo.

### 4. Base de Datos (PostgreSQL)
El almacenamiento persistente y relacional estará gestionado por PostgreSQL. Guardará toda la información estructurada, incluyendo usuarios, transacciones, alertas y logs de auditoría.

## Comunicación entre Componentes
- **Frontend (Angular) <-> Backend (Spring Boot):** Peticiones HTTP RESTful aseguradas mediante tokens JWT.
- **Backend (Spring Boot) <-> PostgreSQL:** Conexión directa mediante JDBC/JPA (Hibernate).
- **Backend (Spring Boot) <-> Microservicio IA (FastAPI):** Llamadas HTTP REST internas dentro de la red del contenedor Docker.

## Infraestructura y Despliegue
Todo el sistema estará orquestado utilizando **Docker Compose** para levantar los servicios de manera local o en entornos de prueba, asegurando que el backend, frontend, microservicio de IA y base de datos funcionen en contenedores aislados pero interconectados en una red virtual.

## Separación por Módulos
El desarrollo se dividirá de forma modular, permitiendo escalar el equipo y los componentes independientemente (monorepo).

## Roles del Sistema
El sistema contará con control de acceso basado en roles (RBAC) con los siguientes niveles:
- **ADMIN:** Control total del sistema, gestión de usuarios, roles y configuraciones.
- **ANALYST:** Capacidad para visualizar transacciones, gestionar alertas de fraude, cambiar su estado e ingresar notas de investigación.
- **AUDITOR:** Acceso de solo lectura para revisar las acciones de los analistas, visualizar logs de auditoría y reportes del dashboard.
