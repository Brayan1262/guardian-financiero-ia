import { Component, OnInit, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService } from '../../core/services/dashboard.service';
import { DashboardOverview } from '../../core/models/dashboard.model';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent implements OnInit, OnDestroy {
  overview: DashboardOverview | null = null;
  isLoading = true;
  hasError = false;
  errorMessage = '';

  // Datos para barras HTML
  txStatusBars: { label: string, value: number, percent: number, colorClass: string }[] = [];
  txRiskBars: { label: string, value: number, percent: number, colorClass: string }[] = [];
  alertsRiskBars: { label: string, value: number, percent: number, colorClass: string }[] = [];

  currentTime: Date = new Date();
  private timeInterval: any;

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.timeInterval = setInterval(() => {
      this.currentTime = new Date();
    }, 1000);

    forkJoin({
      overview: this.dashboardService.getOverview(),
      recentTx: this.dashboardService.getRecentTransactions(),
      recentAlerts: this.dashboardService.getRecentAlerts()
    }).subscribe({
      next: (data) => {
        this.overview = data.overview;
        // Sobreescribir las listas con los endpoints directos como se pidió
        if (data.recentTx) this.overview.recentTransactions = data.recentTx;
        if (data.recentAlerts) this.overview.recentAlerts = data.recentAlerts;
        
        this.setupBars();
        this.isLoading = false;
        this.hasError = false;
      },
      error: (err) => {
        console.error('Error fetching dashboard data', err);
        this.isLoading = false;
        this.hasError = true;
        this.errorMessage = 'Error al cargar datos. Verifica la conexión con el servidor.';
      }
    });
  }

  ngOnDestroy(): void {
    if (this.timeInterval) {
      clearInterval(this.timeInterval);
    }
  }

  private setupBars(): void {
    if (!this.overview) return;

    // Calcular máximo para porcentajes de transacciones por estado
    const totalTxStatus = this.overview.charts.transactionsByStatus.reduce((acc, curr) => acc + curr.value, 0);
    this.txStatusBars = this.overview.charts.transactionsByStatus.map(c => ({
      label: c.label,
      value: c.value,
      percent: totalTxStatus > 0 ? (c.value / totalTxStatus) * 100 : 0,
      colorClass: this.getColorForStatus(c.label)
    }));

    // Calcular máximo para transacciones por riesgo
    const totalTxRisk = this.overview.charts.transactionsByRiskLevel.reduce((acc, curr) => acc + curr.value, 0);
    this.txRiskBars = this.overview.charts.transactionsByRiskLevel.map(c => ({
      label: c.label,
      value: c.value,
      percent: totalTxRisk > 0 ? (c.value / totalTxRisk) * 100 : 0,
      colorClass: this.getColorForRisk(c.label)
    }));

    // Calcular máximo para alertas por riesgo
    const totalAlertsRisk = this.overview.charts.alertsByRiskLevel.reduce((acc, curr) => acc + curr.value, 0);
    this.alertsRiskBars = this.overview.charts.alertsByRiskLevel.map(c => ({
      label: c.label,
      value: c.value,
      percent: totalAlertsRisk > 0 ? (c.value / totalAlertsRisk) * 100 : 0,
      colorClass: this.getColorForRisk(c.label)
    }));
  }

  private getColorForStatus(status: string): string {
    const s = status.toUpperCase();
    if (s.includes('APPROVED')) return 'bg-success';
    if (s.includes('REJECTED')) return 'bg-danger';
    if (s.includes('UNDER_REVIEW')) return 'bg-warning';
    return 'bg-secondary';
  }

  private getColorForRisk(risk: string): string {
    const r = risk.toUpperCase();
    if (r.includes('LOW')) return 'bg-success';
    if (r.includes('MEDIUM')) return 'bg-warning';
    if (r.includes('HIGH')) return 'bg-orange';
    if (r.includes('CRITICAL')) return 'bg-danger';
    return 'bg-secondary';
  }

  getRiskBadgeClass(riskLevel: string): string {
    switch (riskLevel) {
      case 'LOW': return 'bg-success';
      case 'MEDIUM': return 'bg-warning text-dark';
      case 'HIGH': return 'bg-orange';
      case 'CRITICAL': return 'bg-danger';
      default: return 'bg-secondary';
    }
  }

  getStatusBadgeClass(status: string): string {
    switch (status) {
      case 'APPROVED': return 'bg-success';
      case 'PENDING': return 'bg-warning text-dark';
      case 'UNDER_REVIEW': return 'bg-orange';
      case 'REJECTED': return 'bg-danger';
      case 'IN_REVIEW': return 'bg-primary';
      case 'CONFIRMED_FRAUD': return 'bg-danger';
      case 'FALSE_POSITIVE': return 'bg-secondary';
      case 'RESOLVED': return 'bg-success';
      default: return 'bg-secondary';
    }
  }
}
