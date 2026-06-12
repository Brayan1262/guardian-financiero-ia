# Ejemplos de API (Planificación)

*Nota: Estos son solo ejemplos documentados para la futura implementación de endpoints.*

## Auth
- `POST /api/v1/auth/login` - Autenticar usuario y obtener JWT.
- `POST /api/v1/auth/refresh` - Refrescar token de acceso.

## Users
- `POST /api/v1/users` - Crear un nuevo usuario (ADMIN).
- `GET /api/v1/users` - Listar usuarios del sistema.
- `GET /api/v1/users/{id}` - Obtener detalles de un usuario.

## Customers
- `POST /api/v1/customers` - Registrar un nuevo cliente.
- `GET /api/v1/customers` - Listar clientes.
- `GET /api/v1/customers/{id}` - Ver perfil de cliente y su historial.

## Transactions
- `POST /api/v1/transactions` - Registrar una nueva transacción (evalúa riesgo automáticamente).
- `GET /api/v1/transactions` - Listar historial de transacciones con filtros.
- `GET /api/v1/transactions/{id}` - Detalles de la transacción.

## Fraud Alerts
- `GET /api/v1/alerts` - Listar alertas generadas.
- `GET /api/v1/alerts/{id}` - Detalle de una alerta.
- `PUT /api/v1/alerts/{id}/status` - Actualizar estado de la alerta (ej. INVESTIGANDO, CERRADO, CONFIRMADO).

## Risk Engine
- `POST /api/v1/risk/rules` - Crear nueva regla estática de riesgo.
- `GET /api/v1/risk/rules` - Listar reglas de riesgo activas.

## Dashboard
- `GET /api/v1/dashboard/metrics` - Obtener KPIs (total transacciones, alertas abiertas, montos en riesgo, etc.).
- `GET /api/v1/dashboard/charts` - Datos para gráficos temporales.

## AI Analysis
- `POST /analyze-risk` - Endpoint interno (FastAPI) para predecir riesgo de fraude.

## Audit Logs
- `GET /api/v1/audit/logs` - Consultar acciones realizadas por los usuarios (cambios de estado, accesos, etc.).
