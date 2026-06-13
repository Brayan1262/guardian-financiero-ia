import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserProfile } from '../models/profile.model';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private profileSubject = new BehaviorSubject<UserProfile | null>(this.getProfileFromStorage());
  public profile$ = this.profileSubject.asObservable();

  constructor() {}

  getProfile(): UserProfile | null {
    return this.profileSubject.value;
  }

  updateProfile(profile: UserProfile): void {
    const current = this.getProfile() || { fullName: '', email: '' };
    const updated = { ...current, ...profile };
    this.saveProfileToStorage(updated);
  }

  updateAvatar(base64: string): void {
    const current = this.getProfile();
    if (current) {
      current.avatarBase64 = base64;
      this.saveProfileToStorage(current);
    }
  }

  resetProfile(): void {
    localStorage.removeItem('userProfile');
    this.profileSubject.next(null);
  }

  private saveProfileToStorage(profile: UserProfile): void {
    localStorage.setItem('userProfile', JSON.stringify(profile));
    this.profileSubject.next(profile);
  }

  private getProfileFromStorage(): UserProfile | null {
    const data = localStorage.getItem('userProfile');
    if (data) {
      try {
        return JSON.parse(data);
      } catch (e) {
        return null;
      }
    }
    // Si no hay perfil extendido, inicializar con lo básico del auth (si existe)
    const userAuth = localStorage.getItem('user');
    if (userAuth) {
        try {
            const parsed = JSON.parse(userAuth);
            return { fullName: parsed.fullName, email: parsed.email };
        } catch (e) { return null; }
    }
    return null;
  }
}
