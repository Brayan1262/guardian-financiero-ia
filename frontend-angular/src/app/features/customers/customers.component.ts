import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { CustomerService } from '../../core/services/customer.service';
import { CustomerResponse } from '../../core/models/customer.model';

@Component({
  selector: 'app-customers',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule, HttpClientModule],
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit {
  customers: CustomerResponse[] = [];
  filteredCustomers: CustomerResponse[] = [];
  isLoading = true;
  successMessage = '';
  
  searchTerm = '';
  statusFilter = 'ALL';

  customerForm: FormGroup;
  isEditMode = false;
  selectedCustomerId: number | null = null;
  isSaving = false;
  
  customerToDelete: CustomerResponse | null = null;

  constructor(
    private customerService: CustomerService,
    private fb: FormBuilder
  ) {
    this.customerForm = this.fb.group({
      documentType: ['DNI', Validators.required],
      documentNumber: ['', [Validators.required, Validators.minLength(5)]],
      fullName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phoneNumber: [''],
      status: ['ACTIVE']
    });
  }

  ngOnInit(): void {
    this.loadCustomers();
  }

  loadCustomers(): void {
    this.isLoading = true;
    this.customerService.getCustomers().subscribe({
      next: (data) => {
        console.log('API Response (getCustomers):', data);
        this.customers = data;
        this.applyFilters();
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error fetching customers', err);
        this.isLoading = false;
      }
    });
  }

  search(): void {
    if (!this.searchTerm || this.searchTerm.trim() === '') {
      this.loadCustomers();
      return;
    }

    this.isLoading = true;
    this.customerService.searchCustomers(this.searchTerm).subscribe({
      next: (data) => {
        console.log('API Response (searchCustomers):', data);
        this.customers = data;
        this.applyFilters();
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Error searching customers', err);
        this.isLoading = false;
      }
    });
  }

  applyFilters(): void {
    this.filteredCustomers = this.customers.filter(c => {
      const matchStatus = this.statusFilter === 'ALL' || c.status === this.statusFilter;
      return matchStatus;
    });
  }

  openCreateModal(): void {
    this.isEditMode = false;
    this.selectedCustomerId = null;
    this.customerForm.reset({ documentType: 'DNI', status: 'ACTIVE' });
  }

  openEditModal(customer: CustomerResponse): void {
    this.isEditMode = true;
    this.selectedCustomerId = customer.id;
    this.customerForm.patchValue({
      documentType: customer.documentType,
      documentNumber: customer.documentNumber,
      fullName: customer.fullName,
      email: customer.email,
      phoneNumber: customer.phoneNumber,
      status: customer.status
    });
  }

  saveCustomer(): void {
    if (this.customerForm.invalid) return;
    this.isSaving = true;

    const req = this.customerForm.value;

    if (this.isEditMode && this.selectedCustomerId) {
      this.customerService.updateCustomer(this.selectedCustomerId, req).subscribe({
        next: (res) => {
          console.log('API Response (updateCustomer):', res);
          this.loadCustomers();
          this.closeModal('customerModal');
          this.showSuccess('Cliente actualizado exitosamente.');
          this.isSaving = false;
        },
        error: (err) => { 
          console.error('Error updating customer', err); 
          this.isSaving = false; 
        }
      });
    } else {
      this.customerService.createCustomer(req).subscribe({
        next: (res) => {
          console.log('API Response (createCustomer):', res);
          this.loadCustomers();
          this.closeModal('customerModal');
          this.showSuccess('Cliente creado exitosamente.');
          this.isSaving = false;
        },
        error: (err) => { 
          console.error('Error creating customer', err); 
          this.isSaving = false; 
        }
      });
    }
  }

  changeStatus(customer: CustomerResponse, newStatus: string): void {
    this.customerService.changeStatus(customer.id, newStatus).subscribe({
      next: (res) => {
        console.log('API Response (changeStatus):', res);
        this.loadCustomers();
        this.showSuccess(`Estado del cliente cambiado a ${newStatus}.`);
      },
      error: (err) => console.error('Error changing status', err)
    });
  }

  prepareDelete(customer: CustomerResponse): void {
    this.customerToDelete = customer;
  }

  confirmDelete(): void {
    if (!this.customerToDelete) return;
    this.isSaving = true;
    this.customerService.deleteCustomer(this.customerToDelete.id).subscribe({
      next: () => {
        console.log('API Response (deleteCustomer): SUCCESS');
        this.loadCustomers();
        this.closeModal('deleteModal');
        this.showSuccess('Cliente eliminado exitosamente.');
        this.isSaving = false;
        this.customerToDelete = null;
      },
      error: (err) => { 
        console.error('Error deleting customer', err); 
        this.isSaving = false; 
      }
    });
  }

  closeModal(id: string): void {
    const btn = document.getElementById(`${id}Close`);
    if (btn) btn.click();
  }

  showSuccess(msg: string): void {
    this.successMessage = msg;
    setTimeout(() => this.successMessage = '', 4000);
  }

  getStatusBadgeClass(status: string): string {
    if (!status) return 'bg-secondary';
    switch (status.toUpperCase()) {
      case 'ACTIVE': return 'badge-approved';
      case 'INACTIVE': return 'badge-false_positive';
      case 'BLOCKED': return 'badge-rejected';
      case 'UNDER_REVIEW': return 'badge-under_review';
      default: return 'bg-secondary';
    }
  }
}
