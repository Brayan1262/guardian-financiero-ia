-- seed.sql
-- ==========================================================
-- IMPORTANTE: Ejecutar este script SOLO después de que Spring Boot 
-- haya levantado por primera vez y creado las tablas automáticamente.
-- ==========================================================

-- ==========================================================
-- IMPORTANTE: Para el Módulo 4 de Seguridad en adelante, los passwords 
-- en la base de datos deben ser un hash generado por BCrypt.
-- Es obligatorio crear a los usuarios usando el endpoint de registro de la API:
-- POST /api/auth/register
-- 
-- Ejemplo de Body:
-- { "fullName": "Admin", "email": "admin@guardian.com", "password": "admin123", "role": "ADMIN" }
-- ==========================================================

-- Clientes demo
INSERT INTO customers (document_number, full_name, email, phone, status, risk_score, created_at, updated_at)
VALUES ('74859612', 'Juan Pérez', 'juan.perez@email.com', '987654321', 'ACTIVE', 0, NOW(), NOW())
ON CONFLICT (document_number) DO NOTHING;

INSERT INTO customers (document_number, full_name, email, phone, status, risk_score, created_at, updated_at)
VALUES ('70854123', 'María López', 'maria.lopez@email.com', '999888777', 'UNDER_REVIEW', 50, NOW(), NOW())
ON CONFLICT (document_number) DO NOTHING;

INSERT INTO customers (document_number, full_name, email, phone, status, risk_score, created_at, updated_at)
VALUES ('45987631', 'Carlos Ramírez', 'carlos.ramirez@email.com', '111222333', 'BLOCKED', 95, NOW(), NOW())
ON CONFLICT (document_number) DO NOTHING;

-- Transacciones demo (Módulo 6)
-- Transferencia web de S/ 150.00 para cliente activo (Juan Pérez)
INSERT INTO transactions (customer_id, amount, transaction_type, channel, status, origin_location, destination_location, device_id, transaction_date_time, risk_score, risk_level, created_at, updated_at)
SELECT id, 150.00, 'TRANSFER', 'WEB', 'PENDING', 'Lima', 'Arequipa', 'WEB-DEVICE-001', NOW(), 0, 'LOW', NOW(), NOW()
FROM customers WHERE document_number = '74859612'
LIMIT 1;

-- Compra mobile de S/ 320.50
INSERT INTO transactions (customer_id, amount, transaction_type, channel, status, origin_location, destination_location, device_id, transaction_date_time, risk_score, risk_level, created_at, updated_at)
SELECT id, 320.50, 'PURCHASE', 'MOBILE', 'PENDING', 'Lima', 'Lima', 'MOB-DEVICE-002', NOW(), 0, 'LOW', NOW(), NOW()
FROM customers WHERE document_number = '74859612'
LIMIT 1;

-- Retiro ATM de S/ 700.00 (María López - Under Review)
INSERT INTO transactions (customer_id, amount, transaction_type, channel, status, origin_location, destination_location, device_id, transaction_date_time, risk_score, risk_level, created_at, updated_at)
SELECT id, 700.00, 'WITHDRAWAL', 'ATM', 'UNDER_REVIEW', 'Cusco', 'Cusco', 'ATM-CUSCO-005', NOW(), 0, 'LOW', NOW(), NOW()
FROM customers WHERE document_number = '70854123'
LIMIT 1;

-- Pago POS de S/ 85.90
INSERT INTO transactions (customer_id, amount, transaction_type, channel, status, origin_location, destination_location, device_id, transaction_date_time, risk_score, risk_level, created_at, updated_at)
SELECT id, 85.90, 'PAYMENT', 'POS', 'APPROVED', 'Lima', 'Lima', 'POS-STORE-089', NOW(), 0, 'LOW', NOW(), NOW()
FROM customers WHERE document_number = '74859612'
LIMIT 1;

-- Transferencia web alta de S/ 8500.00, para usar después en motor antifraude
INSERT INTO transactions (customer_id, amount, transaction_type, channel, status, origin_location, destination_location, device_id, transaction_date_time, risk_score, risk_level, created_at, updated_at)
SELECT id, 8500.00, 'TRANSFER', 'WEB', 'PENDING', 'Piura', 'Lima', 'WEB-DEVICE-003', NOW(), 0, 'LOW', NOW(), NOW()
FROM customers WHERE document_number = '74859612'
LIMIT 1;

-- Reglas de riesgo demo
INSERT INTO risk_rules (rule_code, name, description, score, active, created_at, updated_at)
VALUES ('HIGH_AMOUNT', 'Monto inusual', 'Transacción supera el límite seguro', 30, true, NOW(), NOW());

-- Nota (Módulo 8):
-- Las alertas de fraude (tabla fraud_alerts) NO se inicializan masivamente por scripts SQL. 
-- El sistema está diseñado para que FraudAnalysisService las genere de manera automática en tiempo 
-- real cuando una transacción es analizada y resulta tener RiskLevel HIGH o CRITICAL.
-- Puedes generar alertas probando el endpoint POST /api/risk-analysis/pending.

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
