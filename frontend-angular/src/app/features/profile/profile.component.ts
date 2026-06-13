import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';
import { ProfileService } from '../../core/services/profile.service';
import { UserProfile } from '../../core/models/profile.model';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit {
  profile: UserProfile = { fullName: '', email: '' };
  originalRole: string = '';
  
  successMessage = '';
  
  constructor(
    private authService: AuthService,
    private profileService: ProfileService
  ) {}

  ngOnInit(): void {
    // Inicializar datos base desde auth
    const authUser = this.authService.getCurrentUser();
    if (authUser) {
      this.originalRole = authUser.role;
    }

    // Suscribirse a los cambios del perfil
    this.profileService.profile$.subscribe(prof => {
      if (prof) {
        this.profile = { ...prof };
      }
    });
  }

  onFileSelected(event: any): void {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.profile.avatarBase64 = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  triggerFileInput(): void {
    document.getElementById('avatarUpload')?.click();
  }

  saveProfile(): void {
    this.profileService.updateProfile(this.profile);
    this.successMessage = 'Perfil actualizado exitosamente.';
    setTimeout(() => this.successMessage = '', 3000);
  }

  resetProfile(): void {
    this.profileService.resetProfile();
    this.successMessage = 'Perfil restablecido a los valores por defecto.';
    setTimeout(() => this.successMessage = '', 3000);
  }
}
