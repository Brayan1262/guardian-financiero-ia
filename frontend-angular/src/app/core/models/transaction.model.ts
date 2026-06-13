export interface TransactionResponse {
  id: number;
  customerId: number;
  customerFullName?: string; // Podría no venir siempre, pero lo preparamos
  amount: number;
  currency: string;
  type: string;
  channel: string;
  destinationAccount: string;
  status: string;
  transactionDateTime: string;
  riskScore: number;
  riskLevel: string;
  riskExplanation: string;
}
