import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { UserResponse } from '../../core/models/auth.model';
import { CommonModule } from '@angular/common';
import { filter } from 'rxjs/operators';
import { ProfileService } from '../../core/services/profile.service';

@Component({
  selector: 'app-topbar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './topbar.component.html',
  styleUrl: './topbar.component.css'
})
export class TopbarComponent implements OnInit {
  currentUser: UserResponse | null = null;
  roleBase = '';
  pageTitle = 'Dashboard';
  avatarBase64: string | null = null;
  isDarkMode = false;

  constructor(
    private authService: AuthService,
    private profileService: ProfileService,
    private router: Router
  ) {}

  ngOnInit(): void {
    // Escuchar cambios de autenticación
    this.authService.currentUser$.subscribe((user: UserResponse | null) => {
      this.currentUser = user;
      if (user?.role) {
        this.roleBase = user.role.replace('ROLE_', '');
      }
    });

    // Theme setup
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
      this.isDarkMode = true;
      document.documentElement.setAttribute('data-theme', 'dark');
    }

    // Escuchar perfil extendido (por si cambia nombre o foto)
    this.profileService.profile$.subscribe(profile => {
      if (profile) {
        if (this.currentUser) {
          this.currentUser.fullName = profile.fullName;
        }
        this.avatarBase64 = profile.avatarBase64 || null;
      }
    });

    // Título dinámico
    this.updateTitle(this.router.url);
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: any) => {
      this.updateTitle(event.urlAfterRedirects);
    });
  }

  updateTitle(url: string) {
    if (url.includes('/dashboard')) this.pageTitle = 'Dashboard Analítico';
    else if (url.includes('/transactions')) this.pageTitle = 'Gestión de Transacciones';
    else if (url.includes('/alerts')) this.pageTitle = 'Centro de Alertas';
    else if (url.includes('/customers')) this.pageTitle = 'Directorio de Clientes';
    else if (url.includes('/profile')) this.pageTitle = 'Mi Perfil';
    else this.pageTitle = 'Panel Antifraude';
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  goToProfile(): void {
    this.router.navigate(['/profile']);
  }

  toggleTheme(): void {
    this.isDarkMode = !this.isDarkMode;
    if (this.isDarkMode) {
      document.documentElement.setAttribute('data-theme', 'dark');
      localStorage.setItem('theme', 'dark');
    } else {
      document.documentElement.removeAttribute('data-theme');
      localStorage.setItem('theme', 'light');
    }
  }
}
