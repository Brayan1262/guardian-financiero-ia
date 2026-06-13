import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { TransactionResponse } from '../models/transaction.model';

@Injectable({
  providedIn: 'root'
})
export class RiskAnalysisService {
  constructor(private http: HttpClient) {}

  analyzeTransaction(transactionId: number): Observable<TransactionResponse> {
    return this.http.post<TransactionResponse>(`http://localhost:8080/api/risk-analysis/transactions/${transactionId}`, {});
  }
}
