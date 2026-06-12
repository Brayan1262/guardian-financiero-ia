-- seed.sql
-- ==========================================================
-- IMPORTANTE: Ejecutar este script SOLO después de que Spring Boot 
-- haya levantado por primera vez y creado las tablas automáticamente.
-- ==========================================================

-- Usuarios demo (Contraseñas temporales en texto plano, cambiar por BCrypt después)
INSERT INTO users (full_name, email, password, role, enabled, created_at, updated_at) 
VALUES ('Admin Sistema', 'admin@finguard.com', 'admin123', 'ADMIN', true, NOW(), NOW());

INSERT INTO users (full_name, email, password, role, enabled, created_at, updated_at) 
VALUES ('Analista Prueba', 'analista@finguard.com', 'analista123', 'ANALYST', true, NOW(), NOW());

INSERT INTO users (full_name, email, password, role, enabled, created_at, updated_at) 
VALUES ('Auditor General', 'auditor@finguard.com', 'auditor123', 'AUDITOR', true, NOW(), NOW());

-- Clientes demo
INSERT INTO customers (document_number, full_name, email, phone, status, risk_score, created_at, updated_at)
VALUES ('10000001', 'Juan Perez', 'juan@test.com', '+12345678', 'ACTIVE', 0, NOW(), NOW());

INSERT INTO customers (document_number, full_name, email, phone, status, risk_score, created_at, updated_at)
VALUES ('10000002', 'Maria Lopez', 'maria@test.com', '+87654321', 'UNDER_REVIEW', 50, NOW(), NOW());

INSERT INTO customers (document_number, full_name, email, phone, status, risk_score, created_at, updated_at)
VALUES ('10000003', 'Carlos Fraud', 'carlos@test.com', '+00000000', 'BLOCKED', 100, NOW(), NOW());

-- Reglas de riesgo demo
INSERT INTO risk_rules (rule_code, name, description, score, active, created_at, updated_at)
VALUES ('HIGH_AMOUNT', 'Monto inusual', 'Transacción supera el límite seguro', 30, true, NOW(), NOW());

INSERT INTO risk_rules (rule_code, name, description, score, active, created_at, updated_at)
VALUES ('UNUSUAL_HOUR', 'Hora inusual', 'Transacción fuera del horario laboral', 20, true, NOW(), NOW());

INSERT INTO risk_rules (rule_code, name, description, score, active, created_at, updated_at)
VALUES ('NEW_CUSTOMER', 'Cliente nuevo', 'Primeras transacciones del cliente', 10, true, NOW(), NOW());

INSERT INTO risk_rules (rule_code, name, description, score, active, created_at, updated_at)
VALUES ('DIFFERENT_LOCATION', 'Ubicación diferente', 'Origen y destino no coinciden o son sospechosos', 40, true, NOW(), NOW());

INSERT INTO risk_rules (rule_code, name, description, score, active, created_at, updated_at)
VALUES ('DIGITAL_CHANNEL', 'Canal digital', 'Operaciones no presenciales por montos altos', 15, true, NOW(), NOW());

INSERT INTO risk_rules (rule_code, name, description, score, active, created_at, updated_at)
VALUES ('MULTIPLE_TRANSACTIONS', 'Transacciones múltiples', 'Varias transacciones seguidas en corto tiempo', 50, true, NOW(), NOW());
