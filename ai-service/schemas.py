from pydantic import BaseModel

class TransactionFeatures(BaseModel):
    amount: float
    transaction_type: str
    channel: str
    device_id_trusted: bool
    is_different_location: bool

class PredictionResponse(BaseModel):
    risk_score: int
    risk_level: str
    ai_explanation: str
