import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TransactionService } from '../../core/services/transaction.service';
import { RiskAnalysisService } from '../../core/services/risk-analysis.service';
import { TransactionResponse } from '../../core/models/transaction.model';

@Component({
  selector: 'app-transactions',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './transactions.component.html',
  styleUrl: './transactions.component.css'
})
export class TransactionsComponent implements OnInit {
  transactions: TransactionResponse[] = [];
  filteredTransactions: TransactionResponse[] = [];
  isLoading = true;
  
  // Detalle Modal
  selectedTransaction: TransactionResponse | null = null;
  analyzingId: number | null = null;
  analyzedExplanation = '';
  
  // Creacion
  createForm: FormGroup;
  isCreating = false;
  successMessage = '';
  
  // Filtros frontend
  searchTerm: string = '';
  statusFilter: string = 'ALL';
  riskFilter: string = 'ALL';

  constructor(
    private txService: TransactionService,
    private riskService: RiskAnalysisService,
    private fb: FormBuilder
  ) {
    this.createForm = this.fb.group({
      customerId: [null, [Validators.required, Validators.min(1)]],
      amount: [null, [Validators.required, Validators.min(0.01)]],
      type: ['TRANSFER', Validators.required],
      channel: ['WEB', Validators.required],
      originLocation: ['', Validators.required],
      destinationAccount: ['', Validators.required],
      currency: ['USD', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadTransactions();
  }

  loadTransactions(): void {
    this.isLoading = true;
    this.txService.getAllTransactions().subscribe({
      next: (data) => {
        console.log('API Response (Transactions):', data);
        this.transactions = data;
        this.applyFilters();
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching transactions', err);
        this.isLoading = false;
      }
    });
  }

  applyFilters(): void {
    this.filteredTransactions = this.transactions.filter(tx => {
      const searchStr = this.searchTerm.toLowerCase();
      const matchSearch = tx.id.toString().includes(searchStr) || 
                          (tx.customerFullName?.toLowerCase() || '').includes(searchStr) ||
                          tx.channel.toLowerCase().includes(searchStr);
      const matchStatus = this.statusFilter === 'ALL' || tx.status === this.statusFilter;
      const matchRisk = this.riskFilter === 'ALL' || tx.riskLevel === this.riskFilter;
      
      return matchSearch && matchStatus && matchRisk;
    });
  }

  openDetail(tx: TransactionResponse): void {
    this.selectedTransaction = tx;
  }

  analyzeRisk(txId: number): void {
    this.analyzingId = txId;
    this.analyzedExplanation = '';
    this.riskService.analyzeTransaction(txId).subscribe({
      next: (data) => {
        console.log('API Response (Analyze Risk):', data);
        const index = this.transactions.findIndex(t => t.id === data.id);
        if (index !== -1) {
          this.transactions[index] = data;
        }
        if (this.selectedTransaction && this.selectedTransaction.id === data.id) {
          this.selectedTransaction = data;
        }
        this.applyFilters();
        this.analyzingId = null;
        
        this.analyzedExplanation = data.riskExplanation || 'Análisis completado sin observaciones críticas.';
        // Disparar modal de explicación
        const modalBtn = document.getElementById('btnShowExplanation');
        if (modalBtn) modalBtn.click();
      },
      error: (err) => {
        console.error('Error al analizar riesgo', err);
        this.analyzingId = null;
      }
    });
  }

  approveTransaction(txId: number): void {
    this.txService.approveTransaction(txId).subscribe({
      next: (data) => {
        console.log('API Response (Approve):', data);
        const index = this.transactions.findIndex(t => t.id === data.id);
        if (index !== -1) {
          this.transactions[index] = data;
        }
        this.applyFilters();
        this.showSuccess('Transacción aprobada exitosamente.');
      },
      error: (err) => {
        console.error('Error al aprobar', err);
      }
    });
  }

  onSubmitCreate(): void {
    if (this.createForm.invalid) return;
    
    this.isCreating = true;
    this.txService.createTransaction(this.createForm.value).subscribe({
      next: (data) => {
        console.log('API Response (Create Transaction):', data);
        this.isCreating = false;
        this.createForm.reset({ type: 'TRANSFER', channel: 'WEB', currency: 'USD' });
        
        const closeBtn = document.getElementById('closeCreateModal');
        if (closeBtn) closeBtn.click();
        
        this.loadTransactions();
        this.showSuccess('Transacción creada exitosamente.');
      },
      error: (err) => {
        console.error('Error creando transacción', err);
        this.isCreating = false;
      }
    });
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
      case 'APPROVED': return 'badge-approved';
      case 'PENDING': return 'badge-pending';
      case 'UNDER_REVIEW': return 'badge-under_review';
      case 'REJECTED': return 'badge-rejected';
      case 'IN_REVIEW': return 'badge-in_review';
      case 'CONFIRMED_FRAUD': return 'badge-confirmed_fraud';
      case 'FALSE_POSITIVE': return 'badge-false_positive';
      case 'RESOLVED': return 'badge-resolved';
      default: return 'bg-secondary';
    }
  }
}
