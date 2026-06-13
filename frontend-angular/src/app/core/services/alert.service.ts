import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { FraudAlertResponse } from '../models/alert.model';

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  private apiUrl = `${environment.apiUrl}/alerts`;

  constructor(private http: HttpClient) {}

  getAlerts(): Observable<FraudAlertResponse[]> {
    return this.http.get<FraudAlertResponse[]>(this.apiUrl);
  }

  getAlertById(id: number): Observable<FraudAlertResponse> {
    return this.http.get<FraudAlertResponse>(`${this.apiUrl}/${id}`);
  }

  takeAlert(id: number): Observable<FraudAlertResponse> {
    return this.http.patch<FraudAlertResponse>(`${this.apiUrl}/${id}/take`, {});
  }

  updateStatus(id: number, status: string): Observable<FraudAlertResponse> {
    return this.http.patch<FraudAlertResponse>(`${this.apiUrl}/${id}/status?status=${status}`, {});
  }

  addComment(id: number, comment: string): Observable<FraudAlertResponse> {
    // Se envía el texto en el body tal como se solicitó
    return this.http.patch<FraudAlertResponse>(`${this.apiUrl}/${id}/comment`, comment);
  }
}
