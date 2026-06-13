import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CustomerService } from '../../core/services/customer.service';
import { CustomerResponse } from '../../core/models/customer.model';

@Component({
  selector: 'app-customers',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './customers.component.html',
  styleUrl: './customers.component.css'
})
export class CustomersComponent implements OnInit {
  customers: CustomerResponse[] = [];
  filteredCustomers: CustomerResponse[] = [];
  isLoading = true;
  
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
    this.customerService.getAllCustomers().subscribe({
      next: (data) => {
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

  applyFilters(): void {
    this.filteredCustomers = this.customers.filter(c => {
      const matchSearch = c.fullName.toLowerCase().includes(this.searchTerm.toLowerCase()) || 
                          c.documentNumber.includes(this.searchTerm) ||
                          c.email.toLowerCase().includes(this.searchTerm.toLowerCase());
      const matchStatus = this.statusFilter === 'ALL' || c.status === this.statusFilter;
      return matchSearch && matchStatus;
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
          this.loadCustomers();
          this.closeModal('customerModal');
          this.isSaving = false;
        },
        error: (err) => { console.error(err); this.isSaving = false; }
      });
    } else {
      this.customerService.createCustomer(req).subscribe({
        next: (res) => {
          this.loadCustomers();
          this.closeModal('customerModal');
          this.isSaving = false;
        },
        error: (err) => { console.error(err); this.isSaving = false; }
      });
    }
  }

  prepareDelete(customer: CustomerResponse): void {
    this.customerToDelete = customer;
  }

  confirmDelete(): void {
    if (!this.customerToDelete) return;
    this.isSaving = true;
    this.customerService.deleteCustomer(this.customerToDelete.id).subscribe({
      next: () => {
        this.loadCustomers();
        this.closeModal('deleteModal');
        this.isSaving = false;
        this.customerToDelete = null;
      },
      error: (err) => { console.error(err); this.isSaving = false; }
    });
  }

  closeModal(id: string): void {
    const btn = document.getElementById(`${id}Close`);
    if (btn) btn.click();
  }

  getStatusBadgeClass(status: string): string {
    switch (status) {
      case 'ACTIVE': return 'badge-approved';
      case 'INACTIVE': return 'badge-false_positive';
      case 'BLOCKED': return 'badge-rejected';
      case 'UNDER_REVIEW': return 'badge-under_review';
      default: return 'bg-secondary';
    }
  }
}
