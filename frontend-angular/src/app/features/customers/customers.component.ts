import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-customers',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <div class="card border-0 shadow-sm rounded-4 text-center py-5">
      <div class="card-body">
        <div class="mb-4">
          <div class="d-inline-flex align-items-center justify-content-center bg-success-subtle text-success rounded-circle" style="width: 80px; height: 80px;">
            <i class="bi bi-tools fs-1"></i>
          </div>
        </div>
        <h3 class="fw-bold text-dark">Módulo de Clientes</h3>
        <p class="text-muted mb-4">Este módulo visual se encuentra actualmente en construcción.</p>
        <a routerLink="/dashboard" class="btn btn-primary px-4 rounded-pill">Volver al Dashboard</a>
      </div>
    </div>
  `
})
export class CustomersComponent {
}
