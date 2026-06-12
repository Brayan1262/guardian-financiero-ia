export interface DashboardSummary {
  totalCustomers: number;
  activeCustomers: number;
  blockedCustomers: number;
  underReviewCustomers: number;
  totalTransactions: number;
  totalTransactionAmount: number;
  averageTransactionAmount: number;
  pendingTransactions: number;
  approvedTransactions: number;
  rejectedTransactions: number;
  underReviewTransactions: number;
  totalAlerts: number;
  pendingAlerts: number;
  inReviewAlerts: number;
  confirmedFraudAlerts: number;
  falsePositiveAlerts: number;
  resolvedAlerts: number;
  highRiskTransactions: number;
  criticalRiskTransactions: number;
  highRiskAlerts: number;
  criticalRiskAlerts: number;
  generatedAt: string;
}

export interface MetricCard {
  title: string;
  value: string;
  description: string;
  type: string;
}

export interface ChartData {
  label: string;
  value: number;
}

export interface DashboardCharts {
  transactionsByStatus: ChartData[];
  transactionsByRiskLevel: ChartData[];
  transactionsByChannel: ChartData[];
  alertsByStatus: ChartData[];
  alertsByRiskLevel: ChartData[];
  customersByStatus: ChartData[];
}

export interface RecentTransaction {
  id: number;
  customerFullName: string;
  amount: number;
  transactionType: string;
  channel: string;
  status: string;
  riskLevel: string;
  riskScore: number;
  transactionDateTime: string;
}

export interface RecentAlert {
  id: number;
  transactionId: number;
  customerFullName: string;
  riskLevel: string;
  riskScore: number;
  status: string;
  createdAt: string;
}

export interface DashboardOverview {
  summary: DashboardSummary;
  metricCards: MetricCard[];
  charts: DashboardCharts;
  recentTransactions: RecentTransaction[];
  recentAlerts: RecentAlert[];
}
