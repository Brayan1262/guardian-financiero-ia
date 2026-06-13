import { Component, OnInit, OnDestroy } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit, OnDestroy {
  loginForm: FormGroup;
  isLoading = false;
  errorMessage = '';

  phrases = [
    'Protege tu sistema',
    'Analítica en tiempo real',
    'Seguridad financiera'
  ];
  currentPhrase = this.phrases[0];
  phraseInterval: any;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      email: ['admin@guardian.com', [Validators.required, Validators.email]],
      password: ['admin123', Validators.required]
    });
  }

  ngOnInit(): void {
    let index = 0;
    this.phraseInterval = setInterval(() => {
      index = (index + 1) % this.phrases.length;
      this.currentPhrase = this.phrases[index];
    }, 4000);
  }

  ngOnDestroy(): void {
    if (this.phraseInterval) {
      clearInterval(this.phraseInterval);
    }
  }

  onSubmit(): void {
    if (this.loginForm.invalid) {
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.authService.login(this.loginForm.value).subscribe({
      next: () => {
        this.isLoading = false;
        this.router.navigate(['/dashboard']);
      },
      error: (err) => {
        this.isLoading = false;
        if (err.status === 401 || err.status === 403) {
          this.errorMessage = 'Credenciales incorrectas';
        } else {
          this.errorMessage = err.error?.message || 'Error de conexión con el servidor.';
        }
      }
    });
  }
}
