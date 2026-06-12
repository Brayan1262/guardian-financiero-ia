# Ejemplos de API

## Autenticación (Módulo 4)

### Registrar Usuario
**POST /api/auth/register**
```json
{
  "fullName": "Administrador Demo",
  "email": "admin@guardian.com",
  "password": "admin123",
  "role": "ADMIN"
}
```

### Iniciar Sesión
**POST /api/auth/login**
```json
{
  "email": "admin@guardian.com",
  "password": "admin123"
}
```

### Obtener Usuario Actual
**GET /api/auth/me**
*Requiere Header: `Authorization: Bearer <TOKEN>`*

---

## Prueba de Roles (Módulo 4)

### Probar Rol ADMIN
**GET /api/admin/test**
*Requiere Header: `Authorization: Bearer <TOKEN>` (de un ADMIN)*

### Probar Rol ANALYST
**GET /api/analyst/test**
*Requiere Header: `Authorization: Bearer <TOKEN>` (de un ADMIN o ANALYST)*

### Probar Rol AUDITOR
**GET /api/audit/test**
*Requiere Header: `Authorization: Bearer <TOKEN>` (de un ADMIN o AUDITOR)*

---

## Clientes (Módulo 5)

### Crear cliente
**POST /api/customers**
*Requiere Header: `Authorization: Bearer <TOKEN>` (ADMIN o ANALYST)*
```json
{
  "documentNumber": "74859612",
  "fullName": "Juan Pérez",
  "email": "juan.perez@email.com",
  "phone": "987654321"
}
```

### Actualizar cliente
**PUT /api/customers/{id}**
*Requiere Header: `Authorization: Bearer <TOKEN>` (ADMIN o ANALYST)*
```json
{
  "fullName": "Juan Pérez Actualizado",
  "email": "juan.actualizado@email.com",
  "phone": "999888777"
}
```

### Cambiar estado
**PATCH /api/customers/{id}/status**
*Requiere Header: `Authorization: Bearer <TOKEN>` (ADMIN o ANALYST)*
```json
{
  "status": "UNDER_REVIEW"
}
```

---

## Transacciones Financieras (Módulo 6)

### Crear transacción
**POST /api/transactions**
*Requiere Header: `Authorization: Bearer <TOKEN>` (ADMIN o ANALYST)*
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

### Actualizar estado
**PATCH /api/transactions/{id}/status**
*Requiere Header: `Authorization: Bearer <TOKEN>` (ADMIN o ANALYST)*
```json
{
  "status": "UNDER_REVIEW"
}
```

### Búsquedas Comunes
* **Buscar por estado:** `GET /api/transactions/status/PENDING`
* **Buscar por tipo:** `GET /api/transactions/type/TRANSFER`
* **Buscar por canal:** `GET /api/transactions/channel/WEB`
* **Resumen estadístico:** `GET /api/transactions/summary`
