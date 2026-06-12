# Guardián Financiero IA - Frontend Angular

Módulo 10: Aplicación cliente (Frontend) del sistema inteligente antifraude.

## Requisitos Previos
* Node.js v18+
* Angular CLI v17+
* Tener ejecutándose el backend Java en `http://localhost:8080`.

## Instalación y Configuración

1. Instalar las dependencias de npm:
   ```bash
   npm install
   ```

2. Ejecutar el servidor de desarrollo:
   ```bash
   npm start
   ```

3. Navegar a `http://localhost:4200/`.

## Credenciales de Acceso (Demo)
* **Correo:** admin@guardian.com
* **Contraseña:** admin123

## Tecnologías Utilizadas
* Angular 17+ (Standalone Components, Functional Guards/Interceptors).
* Bootstrap 5 + Bootstrap Icons.
* Chart.js + ng2-charts para gráficas dinámicas.
* JWT almacenado en `localStorage`.

## Estructura Principal
* `core/`: Guards, Models, Interceptors, Services.
* `features/`: Vistas de las pantallas (`login`, `dashboard`, `alerts`, etc.).
* `layout/`: Estructura general de navegación (`sidebar`, `topbar`).
* `environments/`: Configuración a apuntar al servidor backend.

## Posibles Errores (CORS)
Si encuentras un error de CORS al intentar hacer Login o recuperar el Dashboard, asegúrate de que el Backend Java (`SecurityConfig.java`) tenga configurado `http://localhost:4200` en sus orígenes permitidos.
