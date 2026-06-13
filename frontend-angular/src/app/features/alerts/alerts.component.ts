import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { AlertService } from '../../core/services/alert.service';
import { FraudAlertResponse } from '../../core/models/alert.model';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-alerts',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './alerts.component.html',
  styleUrl: './alerts.component.css'
})
export class AlertsComponent implements OnInit {
  alerts: FraudAlertResponse[] = [];
  filteredAlerts: FraudAlertResponse[] = [];
  isLoading = true;
  successMessage = '';

  // Filtros
  statusFilter: string = 'ALL';
  riskFilter: string = 'ALL';
  
  // Modal State
  selectedAlert: FraudAlertResponse | null = null;
  actionComment: string = '';
  isProcessing = false;

  constructor(private alertService: AlertService) {}

  ngOnInit(): void {
    this.loadAlerts();
  }

  loadAlerts(): void {
    this.isLoading = true;
    this.alertService.getAlerts().subscribe({
      next: (data) => {
        console.log('API Response (getAlerts):', data);
        this.alerts = data;
        this.applyFilters();
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching alerts', err);
        this.isLoading = false;
      }
    });
  }

  applyFilters(): void {
    this.filteredAlerts = this.alerts.filter(alert => {
      const matchStatus = this.statusFilter === 'ALL' || alert.status === this.statusFilter;
      const matchRisk = this.riskFilter === 'ALL' || alert.riskLevel === this.riskFilter;
      return matchStatus && matchRisk;
    });
  }

  takeAlert(alert: FraudAlertResponse): void {
    this.alertService.takeAlert(alert.id).subscribe({
      next: (data) => {
        console.log('API Response (takeAlert):', data);
        this.updateAlertInList(data);
        this.showSuccess('Alerta asignada exitosamente.');
      },
      error: (err) => console.error('Error taking alert', err)
    });
  }

  openEvalModal(alert: FraudAlertResponse): void {
    this.selectedAlert = alert;
    this.actionComment = '';
  }

  submitDecision(newStatus: string): void {
    if (!this.selectedAlert || !this.actionComment) return;

    this.isProcessing = true;
    const alertId = this.selectedAlert.id;

    // Llamada secuencial: primero addComment, luego updateStatus
    this.alertService.addComment(alertId, this.actionComment).pipe(
      switchMap((commentData) => {
        console.log('API Response (addComment):', commentData);
        return this.alertService.updateStatus(alertId, newStatus);
      })
    ).subscribe({
      next: (statusData) => {
        console.log('API Response (updateStatus):', statusData);
        this.updateAlertInList(statusData);
        this.isProcessing = false;
        
        this.selectedAlert = null;
        document.getElementById('closeEvalModalBtn')?.click();
        this.showSuccess(`Alerta evaluada y marcada como ${newStatus}.`);
      },
      error: (err) => {
        console.error('Error procesando la evaluación de la alerta', err);
        this.isProcessing = false;
      }
    });
  }

  private updateAlertInList(updatedAlert: FraudAlertResponse): void {
    const idx = this.alerts.findIndex(a => a.id === updatedAlert.id);
    if (idx !== -1) {
      this.alerts[idx] = updatedAlert;
      this.applyFilters();
    }
  }

  showSuccess(msg: string): void {
    this.successMessage = msg;
    setTimeout(() => this.successMessage = '', 4000);
  }

  getRiskBadgeClass(riskLevel: string): string {
    if (!riskLevel) return 'bg-secondary';
    switch (riskLevel.toUpperCase()) {
      case 'LOW': return 'badge-low';
      case 'MEDIUM': return 'badge-medium';
      case 'HIGH': return 'badge-high';
      case 'CRITICAL': return 'badge-critical';
      default: return 'bg-secondary';
    }
  }

  getStatusBadgeClass(status: string): string {
    if (!status) return 'bg-secondary';
    switch (status.toUpperCase()) {
      case 'PENDING': return 'badge-pending';
      case 'IN_REVIEW': return 'badge-in_review';
      case 'CONFIRMED_FRAUD': return 'badge-confirmed_fraud';
      case 'FALSE_POSITIVE': return 'badge-false_positive';
      case 'RESOLVED': return 'badge-resolved';
      default: return 'bg-secondary';
    }
  }
}
