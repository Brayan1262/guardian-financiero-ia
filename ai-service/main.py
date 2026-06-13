from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from schemas import TransactionFeatures, PredictionResponse
from ml_engine import predict_fraud

app = FastAPI(
    title="Guardián Financiero IA - AI Service",
    description="Microservicio de Machine Learning para detección de fraudes",
    version="1.0.0"
)

# Configuración CORS
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],  # Permitir solicitudes de Spring Boot o Angular
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/health")
def health_check():
    return {"status": "AI Service running", "version": "1.0.0"}

@app.post("/api/ai/predict", response_model=PredictionResponse)
def predict(features: TransactionFeatures):
    """
    Recibe las características de una transacción y devuelve el análisis de riesgo 
    evaluado por el motor de IA.
    """
    prediction = predict_fraud(features)
    return prediction
