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

---

## Motor Antifraude y Análisis de Riesgo (Módulo 7)

### Analizar transacción específica
**POST /api/risk-analysis/transactions/1**
*Requiere Header: `Authorization: Bearer <TOKEN>` (ADMIN o ANALYST)*

**Respuesta Esperada:**
```json
{
  "transactionId": 1,
  "customerId": 1,
  "customerFullName": "Juan Pérez",
  "amount": 8500.00,
  "transactionType": "TRANSFER",
  "channel": "WEB",
  "riskScore": 65,
  "riskLevel": "HIGH",
  "riskExplanation": "Monto elevado respecto a una operación regular. Operación realizada por canal digital. Origen y destino de la operación son diferentes. Las transferencias requieren mayor monitoreo.",
  "triggeredRules": [
    "HIGH_AMOUNT",
    "DIGITAL_CHANNEL",
    "DIFFERENT_LOCATION",
    "TRANSFER_OPERATION"
  ],
  "recommendedAction": "Enviar operación a revisión manual por analista.",
  "transactionStatus": "UNDER_REVIEW",
  "analyzedAt": "2026-06-12T15:30:00.000000"
}
```

### Analizar todas las pendientes
**POST /api/risk-analysis/pending**

### Listar reglas activas
**GET /api/risk-analysis/rules**

---

## Gestión de Alertas Antifraude (Módulo 8)

### Listar alertas
**GET /api/alerts**

### Tomar alerta (In Review)
**PATCH /api/alerts/1/take**
*Requiere Header: `Authorization: Bearer <TOKEN>` (ADMIN o ANALYST)*

### Marcar como Fraude Confirmado
**PATCH /api/alerts/1/status**
```json
{
  "status": "CONFIRMED_FRAUD",
  "analystComment": "Se confirma operación sospechosa por monto elevado y canal digital."
}
```

### Marcar como Falso Positivo
**PATCH /api/alerts/1/status**
```json
{
  "status": "FALSE_POSITIVE",
  "analystComment": "Cliente confirmó la operación por llamada."
}
```

### Agregar o actualizar comentario
**PATCH /api/alerts/1/comment**
```json
{
  "analystComment": "Se contactó al cliente para validación inicial. Esperando respuesta."
}
```

### Resumen estadístico
**GET /api/alerts/summary**

---

## Dashboard y Métricas (Módulo 9)

### Resumen General (Summary)
**GET /api/dashboard/summary**
*Requiere Header: `Authorization: Bearer <TOKEN>`*
```json
{
  "totalCustomers": 1,
  "totalTransactions": 2,
  "totalTransactionAmount": 12500.00,
  "averageTransactionAmount": 6250.00,
  "totalAlerts": 1,
  "highRiskTransactions": 2,
  "criticalRiskTransactions": 0,
  "generatedAt": "2026-06-12T17:00:00"
}
```

### Datos para Gráficos (Charts)
**GET /api/dashboard/charts**
```json
{
  "transactionsByStatus": [
    { "label": "UNDER_REVIEW", "value": 2 },
    { "label": "APPROVED", "value": 0 }
  ],
  "transactionsByRiskLevel": [
    { "label": "HIGH", "value": 2 },
    { "label": "LOW", "value": 0 }
  ]
}
```

### Overview Completo
**GET /api/dashboard/overview**
Devuelve todo el modelo del dashboard agrupado.
