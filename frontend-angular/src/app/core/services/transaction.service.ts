import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { TransactionResponse } from '../models/transaction.model';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private apiUrl = `${environment.apiUrl}/transactions`;

  constructor(private http: HttpClient) {}

  getAllTransactions(): Observable<TransactionResponse[]> {
    return this.http.get<TransactionResponse[]>(this.apiUrl);
  }

  getTransactionById(id: number): Observable<TransactionResponse> {
    return this.http.get<TransactionResponse>(`${this.apiUrl}/${id}`);
  }

  createTransaction(data: any): Observable<TransactionResponse> {
    return this.http.post<TransactionResponse>(this.apiUrl, data);
  }

  approveTransaction(id: number): Observable<TransactionResponse> {
    return this.http.patch<TransactionResponse>(`${this.apiUrl}/${id}/status?status=APPROVED`, {});
  }

  getTransactionsByStatus(status: string): Observable<TransactionResponse[]> {
    return this.http.get<TransactionResponse[]>(`${this.apiUrl}/status/${status}`);
  }

  getTransactionsByRiskLevel(riskLevel: string): Observable<TransactionResponse[]> {
    return this.http.get<TransactionResponse[]>(`${this.apiUrl}/risk/${riskLevel}`);
  }

  getTransactionsByCustomer(customerId: number): Observable<TransactionResponse[]> {
    return this.http.get<TransactionResponse[]>(`${this.apiUrl}/customer/${customerId}`);
  }
}
