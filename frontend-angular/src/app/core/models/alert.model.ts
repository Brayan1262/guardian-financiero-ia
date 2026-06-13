export interface FraudAlertResponse {
  id: number;
  transactionId: number;
  customerId: number;
  riskLevel: string;
  riskScore: number;
  ruleTriggered: string;
  status: string;
  analystComment: string | null;
  reviewedBy: string | null;
  reviewedAt: string | null;
  createdAt: string;
}
