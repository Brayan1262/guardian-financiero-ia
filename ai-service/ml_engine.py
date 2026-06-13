import random
from schemas import TransactionFeatures, PredictionResponse

def predict_fraud(features: TransactionFeatures) -> PredictionResponse:
    # Heurística base simulada (Fase 1)
    base_score = 10
    explanation_parts = []
    
    # Evaluar monto
    if features.amount > 10000:
        base_score += 40
        explanation_parts.append(f"Monto excepcionalmente alto (${features.amount}).")
    elif features.amount > 5000:
        base_score += 20
        explanation_parts.append(f"Monto elevado (${features.amount}).")
        
    # Evaluar ubicación
    if features.is_different_location:
        base_score += 35
        explanation_parts.append("La transacción proviene de una ubicación inusual.")
        
    # Evaluar dispositivo
    if not features.device_id_trusted:
        base_score += 25
        explanation_parts.append("Dispositivo no reconocido o no confiable.")
    else:
        # Beneficio por dispositivo confiable
        base_score -= 15
        explanation_parts.append("Dispositivo de confianza utilizado.")
        
    # Evaluar canal
    if features.channel.upper() == "WEB" and features.amount > 3000:
        base_score += 15
        explanation_parts.append("Transacción web de monto considerable.")
        
    # Añadir factor de aleatoriedad para simular un modelo probabilístico (ruido)
    noise = random.randint(-5, 5)
    final_score = max(0, min(100, base_score + noise))
    
    # Determinar nivel de riesgo
    if final_score >= 85:
        risk_level = "CRITICAL"
    elif final_score >= 65:
        risk_level = "HIGH"
    elif final_score >= 40:
        risk_level = "MEDIUM"
    else:
        risk_level = "LOW"
        
    # Ensamblar explicación
    if risk_level == "LOW" and not explanation_parts:
        ai_explanation = "Transacción estándar sin anomalías detectadas."
    else:
        ai_explanation = " ".join(explanation_parts) if explanation_parts else "Transacción evaluada dentro de los umbrales normales."
        
    return PredictionResponse(
        risk_score=final_score,
        risk_level=risk_level,
        ai_explanation=ai_explanation
    )
