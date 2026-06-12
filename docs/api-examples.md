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
