import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { DashboardOverview, DashboardSummary, MetricCard, DashboardCharts, RecentTransaction, RecentAlert } from '../models/dashboard.model';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  private apiUrl = `${environment.apiUrl}/dashboard`;

  constructor(private http: HttpClient) {}

  getSummary(): Observable<DashboardSummary> {
    return this.http.get<DashboardSummary>(`${this.apiUrl}/summary`);
  }

  getCards(): Observable<MetricCard[]> {
    return this.http.get<MetricCard[]>(`${this.apiUrl}/cards`);
  }

  getCharts(): Observable<DashboardCharts> {
    return this.http.get<DashboardCharts>(`${this.apiUrl}/charts`);
  }

  getRecentTransactions(): Observable<RecentTransaction[]> {
    return this.http.get<RecentTransaction[]>(`${this.apiUrl}/recent-transactions`);
  }

  getRecentAlerts(): Observable<RecentAlert[]> {
    return this.http.get<RecentAlert[]>(`${this.apiUrl}/recent-alerts`);
  }

  getOverview(): Observable<DashboardOverview> {
    return this.http.get<DashboardOverview>(`${this.apiUrl}/overview`);
  }
}
